package com.localai.agent.featurememory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.core.model.MemoryType
import com.localai.agent.corememory.repository.MemoryRepository
import com.localai.agent.coredatabase.entity.MemoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val memoryRepository: MemoryRepository
) : ViewModel() {

    val memories: StateFlow<List<MemoryEntity>> = memoryRepository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNote(title: String, content: String) {
        viewModelScope.launch { memoryRepository.rememberNote(title, content) }
    }

    fun addPreference(key: String, value: String) {
        viewModelScope.launch { memoryRepository.rememberPreference(key, value) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { memoryRepository.delete(id) }
    }

    fun memoriesByType(type: MemoryType) = memoryRepository.observeByType(type)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
