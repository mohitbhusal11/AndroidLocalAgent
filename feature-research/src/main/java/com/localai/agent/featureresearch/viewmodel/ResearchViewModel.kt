package com.localai.agent.featureresearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.core.model.AiStreamEvent
import com.localai.agent.coreai.research.ResearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResearchViewModel @Inject constructor(
    private val researchRepository: ResearchRepository
) : ViewModel() {
    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result

    fun searchLocal(query: String) {
        viewModelScope.launch { _result.value = researchRepository.searchLocal(query) }
    }

    fun summarize(text: String) {
        viewModelScope.launch {
            _result.value = "Summarizing…"
            researchRepository.summarizeText(text).collect { event ->
                when (event) {
                    is AiStreamEvent.Token -> _result.value = (_result.value ?: "") + event.text
                    is AiStreamEvent.Complete -> _result.value = event.fullText
                    is AiStreamEvent.Error -> _result.value = event.message
                    else -> {}
                }
            }
        }
    }
}
