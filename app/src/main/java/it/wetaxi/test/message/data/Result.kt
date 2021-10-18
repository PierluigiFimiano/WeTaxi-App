package it.wetaxi.test.message.data

sealed class Result<out T : Any> {
    data class Success<T : Any>(val data: T): Result<T>()
    data class Error(val exception: Exception? = null): Result<Nothing>()
}
