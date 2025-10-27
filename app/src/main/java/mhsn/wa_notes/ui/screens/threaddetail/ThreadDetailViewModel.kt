package mhsn.wa_notes.ui.screens.threaddetail

import androidx.lifecycle.SavedStateHandle
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
import mhsn.wa_notes.data.local.entity.NoteEntity
import mhsn.wa_notes.data.local.entity.ThreadEntity
import mhsn.wa_notes.data.repository.NoteRepositoryImpl
import mhsn.wa_notes.data.repository.ThreadRepositoryImpl
import mhsn.wa_notes.data.local.AppDatabase
import android.content.Context

data class ThreadDetailUiState(
    val thread: ThreadEntity? = null,
    val notes: List<NoteEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val messageText: String = ""
)

sealed class ThreadDetailEvent {
    data class SendTextNote(val content: String) : ThreadDetailEvent()
    data class SendMediaNote(val filePath: String, val type: String) : ThreadDetailEvent()
    data class UpdateNote(val note: NoteEntity) : ThreadDetailEvent()
    data class DeleteNote(val note: NoteEntity) : ThreadDetailEvent()
    data class MessageTextChanged(val text: String) : ThreadDetailEvent()
}

class ThreadDetailViewModel(
    context: Context,
    threadId: Long
) : ViewModel() {

    private val threadRepository = ThreadRepositoryImpl(
        AppDatabase.getInstance(context).threadDao()
    )
    private val noteRepository = NoteRepositoryImpl(
        AppDatabase.getInstance(context).noteDao()
    )

    private val _uiState = MutableStateFlow(ThreadDetailUiState())
    val uiState: StateFlow<ThreadDetailUiState> = _uiState.asStateFlow()

    val notes: StateFlow<List<NoteEntity>> = noteRepository
        .getNotesByThreadFlow(threadId)
        .catch { exception ->
            _uiState.update { it.copy(error = exception.message, isLoading = false) }
        }
        .map { noteList ->
            _uiState.update { it.copy(isLoading = false) }
            noteList
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadThread(threadId)
    }

    fun onEvent(event: ThreadDetailEvent) {
        when (event) {
            is ThreadDetailEvent.SendTextNote -> sendTextNote(event.content)
            is ThreadDetailEvent.SendMediaNote -> sendMediaNote(event.filePath, event.type)
            is ThreadDetailEvent.UpdateNote -> updateNote(event.note)
            is ThreadDetailEvent.DeleteNote -> deleteNote(event.note)
            is ThreadDetailEvent.MessageTextChanged -> updateMessageText(event.text)
        }
    }

    private fun loadThread(threadId: Long) {
        viewModelScope.launch {
            try {
                val thread = threadRepository.getThreadById(threadId)
                _uiState.update { it.copy(thread = thread, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Thread yüklenemedi: ${e.message}", isLoading = false) }
            }
        }
    }

    private fun sendTextNote(content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            try {
                val note = NoteEntity(
                    threadId = _uiState.value.thread?.id ?: 0L,
                    content = content.trim(),
                    type = "text",
                    filePath = null,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                noteRepository.insertNote(note)
                
                // Thread'in updatedAt'ini güncelle
                _uiState.value.thread?.let { thread ->
                    threadRepository.updateThread(thread.copy(updatedAt = System.currentTimeMillis()))
                }
                
                _uiState.update { it.copy(messageText = "") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Not gönderilemedi: ${e.message}") }
            }
        }
    }

    private fun sendMediaNote(filePath: String, type: String) {
        viewModelScope.launch {
            try {
                val note = NoteEntity(
                    threadId = _uiState.value.thread?.id ?: 0L,
                    content = null,
                    type = type,
                    filePath = filePath,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                noteRepository.insertNote(note)
                
                // Thread'in updatedAt'ini güncelle
                _uiState.value.thread?.let { thread ->
                    threadRepository.updateThread(thread.copy(updatedAt = System.currentTimeMillis()))
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Medya gönderilemedi: ${e.message}") }
            }
        }
    }

    private fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                val updatedNote = note.copy(updatedAt = System.currentTimeMillis())
                noteRepository.updateNote(updatedNote)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Not güncellenemedi: ${e.message}") }
            }
        }
    }

    private fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(note)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Not silinemedi: ${e.message}") }
            }
        }
    }

    private fun updateMessageText(text: String) {
        _uiState.update { it.copy(messageText = text) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
