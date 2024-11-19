package com.pwhs.quickmem.presentation.app.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.repository.SearchQueryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository,
) : ViewModel() {

    init {
        loadSearchHistory()
    }

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchUiAction) {
        when (event) {
            is SearchUiAction.Search -> search()
            is SearchUiAction.OnQueryChanged -> {
                _uiState.update { it.copy(query = event.query, error = "") }
            }
            is SearchUiAction.DeleteAllSearch -> {
                deleteAllSearchHistory()
            }
            is SearchUiAction.DeleteSearch -> {
                deleteSearch(event.query)
                loadSearchHistory()
            }
            is SearchUiAction.SearchWithQueryResent -> {
                searchWithQueryResent(event.query)
            }
            SearchUiAction.OnRefresh -> {
                loadSearchHistory()
            }
        }
    }

    private fun searchWithQueryResent(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(query = query) }
            _uiEvent.send(SearchUiEvent.NavigateToResult(query))
        }
    }

    private fun search() {
        viewModelScope.launch {
            val query = uiState.value.query
            if (query.isNotBlank()) {
                searchQueryRepository.addSearchQuery(query)
            }
            _uiEvent.trySend(SearchUiEvent.NavigateToResult(query))
        }
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            try {
                val searches = searchQueryRepository.getRecentSearches()
                _uiState.update { it.copy(listResult = searches) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.send(SearchUiEvent.ShowError("Failed to load search history"))
            }
        }
    }

    private fun deleteSearch(query: String) {
        viewModelScope.launch {
            val searchQueryModel = uiState.value.listResult.find { it.query == query }
            if (searchQueryModel != null) {
                searchQueryRepository.deleteSearchQuery(searchQueryModel)
            } else {
                _uiEvent.send(SearchUiEvent.ShowError("Search query not found."))
            }
        }
    }

    private fun deleteAllSearchHistory() {
        viewModelScope.launch {
            try {
                searchQueryRepository.clearSearchHistory()
                loadSearchHistory()
                _uiEvent.trySend(SearchUiEvent.ClearAllSearchResent)
            } catch (e: Exception) {
                _uiEvent.send(SearchUiEvent.ShowError("Failed to clear search history"))
            }
        }
    }
}
