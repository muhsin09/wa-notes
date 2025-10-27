package mhsn.wa_notes.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mhsn.wa_notes.data.local.entity.NoteEntity
import mhsn.wa_notes.data.local.entity.ThreadEntity
import mhsn.wa_notes.data.repository.NoteRepositoryImpl
import mhsn.wa_notes.data.repository.ThreadRepositoryImpl
import mhsn.wa_notes.data.local.AppDatabase
import android.content.Context

data class SearchUiState(
    val query: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchScope: SearchScope = SearchScope.ALL
)

enum class SearchScope {
    ALL, CURRENT_THREAD
}

data class SearchResult(
    val note: NoteEntity,
    val thread: ThreadEntity?,
    val matchedContent: String
)

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class ScopeChanged(val scope: SearchScope) : SearchEvent()
    object ClearSearch : SearchEvent()
}

class SearchViewModel(
    context: Context,
    private val currentThreadId: Long? = null
) : ViewModel() {

    private val threadRepository = ThreadRepositoryImpl(
        AppDatabase.getInstance(context).threadDao()
    )
    private val noteRepository = NoteRepositoryImpl(
        AppDatabase.getInstance(context).noteDao()
    )

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")
    private val searchScope = MutableStateFlow(SearchScope.ALL)

    val searchResults: StateFlow<List<SearchResult>> = combine(
        searchQuery.debounce(300).distinctUntilChanged(),
        searchScope
    ) { query, scope ->
        if (query.isBlank()) {
            emptyList()
        } else {
            performSearch(query, scope)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        if (currentThreadId != null) {
            searchScope.value = SearchScope.CURRENT_THREAD
            _uiState.value = _uiState.value.copy(searchScope = SearchScope.CURRENT_THREAD)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> {
                searchQuery.value = event.query
                _uiState.value = _uiState.value.copy(query = event.query)
            }
            is SearchEvent.ScopeChanged -> {
                searchScope.value = event.scope
                _uiState.value = _uiState.value.copy(searchScope = event.scope)
            }
            is SearchEvent.ClearSearch -> {
                searchQuery.value = ""
                _uiState.value = _uiState.value.copy(query = "", searchResults = emptyList())
            }
        }
    }

    private suspend fun performSearch(query: String, scope: SearchScope): List<SearchResult> {
        return try {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val notes = when (scope) {
                SearchScope.ALL -> noteRepository.searchNotes(query)
                SearchScope.CURRENT_THREAD -> {
                    currentThreadId?.let { threadId ->
                        noteRepository.getNotesByThread(threadId).filter { note ->
                            note.content?.contains(query, ignoreCase = true) == true
                        }
                    } ?: emptyList()
                }
            }
            
            val results = notes.mapNotNull { note ->
                val thread = if (scope == SearchScope.ALL) {
                    threadRepository.getThreadById(note.threadId)
                } else null
                
                val matchedContent = when (note.type) {
                    "text" -> note.content ?: ""
                    else -> note.filePath?.substringAfterLast("/") ?: ""
                }
                
                if (matchedContent.contains(query, ignoreCase = true)) {
                    SearchResult(
                        note = note,
                        thread = thread,
                        matchedContent = matchedContent
                    )
                } else null
            }
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                searchResults = results
            )
            
            results
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Arama sırasında hata oluştu: ${e.message}"
            )
            emptyList()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
