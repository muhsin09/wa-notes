package mhsn.wa_notes.ui.screens.threadlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mhsn.wa_notes.data.local.entity.ThreadEntity
import mhsn.wa_notes.data.repository.ThreadRepositoryImpl
import mhsn.wa_notes.data.local.AppDatabase
import android.content.Context
import mhsn.wa_notes.util.Constants

data class ThreadListUiState(
    val threads: List<ThreadEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = ""
)

sealed class ThreadListEvent {
    data class CreateThread(val title: String) : ThreadListEvent()
    data class UpdateThread(val thread: ThreadEntity) : ThreadListEvent()
    data class DeleteThread(val thread: ThreadEntity) : ThreadListEvent()
    data class SearchQueryChanged(val query: String) : ThreadListEvent()
}

class ThreadListViewModel(
    context: Context
) : ViewModel() {

    private val threadRepository = ThreadRepositoryImpl(
        AppDatabase.getInstance(context).threadDao()
    )

    private val _uiState = MutableStateFlow(ThreadListUiState())
    val uiState: StateFlow<ThreadListUiState> = _uiState.asStateFlow()

    val threads: StateFlow<List<ThreadEntity>> = threadRepository
        .getAllThreadsFlow()
        .catch { exception ->
            _uiState.update { it.copy(error = exception.message, isLoading = false) }
        }
        .map { threadList ->
            _uiState.update { it.copy(isLoading = false) }
            threadList
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        ensureDefaultThread()
    }

    fun onEvent(event: ThreadListEvent) {
        when (event) {
            is ThreadListEvent.CreateThread -> createThread(event.title)
            is ThreadListEvent.UpdateThread -> updateThread(event.thread)
            is ThreadListEvent.DeleteThread -> deleteThread(event.thread)
            is ThreadListEvent.SearchQueryChanged -> updateSearchQuery(event.query)
        }
    }

    private fun createThread(title: String) {
        viewModelScope.launch {
            try {
                val newThread = ThreadEntity(
                    title = title.trim(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                threadRepository.insertThread(newThread)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "${Constants.ERROR_THREAD_CREATE_FAILED}: ${e.message}") }
            }
        }
    }

    private fun updateThread(thread: ThreadEntity) {
        viewModelScope.launch {
            try {
                val updatedThread = thread.copy(updatedAt = System.currentTimeMillis())
                threadRepository.updateThread(updatedThread)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "${Constants.ERROR_THREAD_UPDATE_FAILED}: ${e.message}") }
            }
        }
    }

    private fun deleteThread(thread: ThreadEntity) {
        viewModelScope.launch {
            try {
                threadRepository.deleteThread(thread)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "${Constants.ERROR_THREAD_DELETE_FAILED}: ${e.message}") }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    private fun ensureDefaultThread() {
        viewModelScope.launch {
            try {
                val existingThreads = threadRepository.getAllThreads()
                if (existingThreads.isEmpty()) {
                    val defaultThread = ThreadEntity(
                        title = Constants.DEFAULT_THREAD_TITLE,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    threadRepository.insertThread(defaultThread)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "${Constants.ERROR_DEFAULT_THREAD_CREATE_FAILED}: ${e.message}") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
