package it.wetaxi.test.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.wetaxi.test.message.data.Message
import it.wetaxi.test.message.data.MessageRepository
import it.wetaxi.test.message.data.Result
import it.wetaxi.test.message.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading: Flow<Boolean> = _dataLoading

    private val _errorEvent = MutableStateFlow<Event<Unit>?>(null)
    val errorEvent: Flow<Event<Unit>?> = _errorEvent

    private val _successEvent = MutableStateFlow<Event<Unit>?>(null)
    val successEvent: Flow<Event<Unit>?> = _successEvent

    private val _onlyNotRead = MutableStateFlow(false)
    val onlyNotRead: Flow<Boolean> = _onlyNotRead

    val messages: Flow<List<Message>> =
        repository.getAllMessages().combine(_onlyNotRead) { messages, onlyNotRead ->
            if (onlyNotRead) {
                messages.filter { message ->
                    !message.read
                }
            } else {
                messages
            }
        }

    fun getMessage() {
        if (_dataLoading.value) {
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
        if (_dataLoading.value) {
            return
        }

        _dataLoading.value = true
        viewModelScope.launch {
            repository.markAllMessagesRead()
            _dataLoading.value = false
        }
    }

    fun filterOnlyNotRead(filter: Boolean) {
        if (_onlyNotRead.value == filter) {
            return
        }
        _onlyNotRead.value = filter
    }
}