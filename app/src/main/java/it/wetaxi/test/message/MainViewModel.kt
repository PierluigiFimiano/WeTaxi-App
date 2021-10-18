package it.wetaxi.test.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.wetaxi.test.message.data.Message
import it.wetaxi.test.message.data.MessageRepository
import it.wetaxi.test.message.data.Result
import it.wetaxi.test.message.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _errorEvent = MutableLiveData<Event<Unit>>()
    val errorEvent: LiveData<Event<Unit>> = _errorEvent

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> = _successEvent

    val messages: Flow<List<Message>> = repository.getAllMessages()

    fun getMessage() {
        if (_dataLoading.value == true) {
            return
        }

        _dataLoading.value = true
        viewModelScope.launch {
            val result: Result<Message> = repository.getMassage()
            if (result is Result.Success) {
                _successEvent.value = Event(Unit)
            } else {
                _errorEvent.value = Event(Unit)
            }

            _dataLoading.value = false
        }
    }

    fun markAllMessagesRead() {
        if (_dataLoading.value == true) {
            return
        }

        _dataLoading.value = true
        viewModelScope.launch {
            repository.markAllMessagesRead()
            _dataLoading.value = false
        }
    }
}