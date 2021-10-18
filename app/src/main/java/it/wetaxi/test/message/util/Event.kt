package it.wetaxi.test.message.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

data class Event<out T : Any>(
    val content: T
) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (!hasBeenHandled) {
            hasBeenHandled = true
            content
        } else {
            null
        }
    }

    fun peekContent(): T = content
}

fun <T : Any> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner) { event ->
        event.getContentIfNotHandled()?.also {
            onChanged(it)
        }
    }
}