package com.pwhs.quickmem.presentation.app.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

class SearchScreenViewModel : ViewModel() {

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val searchResults = listOf(
        "Android Java Tutorial",
        "Android Kotlin Tutorial",
        "Android Flutter Tutorial",
        "Android React Native Tutorial",
        "Android Kotlin/Jetpack Compose Tutorial"
    )

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }

    val filteredResults: List<String>
        get() = searchResults.filter {
            it.contains(_searchText.value, ignoreCase = true)
        }
}