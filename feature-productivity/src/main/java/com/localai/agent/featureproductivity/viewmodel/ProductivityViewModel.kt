package com.localai.agent.featureproductivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.coredatabase.dao.TaskDao
import com.localai.agent.coredatabase.entity.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductivityViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {
    val tasks: StateFlow<List<TaskEntity>> = taskDao.observeActive()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String) {
        viewModelScope.launch { taskDao.insert(TaskEntity(title = title)) }
    }

    fun complete(id: Long) {
        viewModelScope.launch { taskDao.complete(id) }
    }
}
