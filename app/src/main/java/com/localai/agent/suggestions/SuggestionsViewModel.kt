package com.localai.agent.suggestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.coredatabase.dao.SuggestionDao
import com.localai.agent.coredatabase.entity.SuggestionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionsViewModel @Inject constructor(
    private val suggestionDao: SuggestionDao,
    private val suggestionEngine: SuggestionEngine
) : ViewModel() {
    val suggestions: StateFlow<List<SuggestionEntity>> = suggestionDao.observeActive()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            suggestionEngine.generateProactiveSuggestions()
        }
    }

    fun dismiss(id: Long) {
        viewModelScope.launch { suggestionDao.dismiss(id) }
    }
}
