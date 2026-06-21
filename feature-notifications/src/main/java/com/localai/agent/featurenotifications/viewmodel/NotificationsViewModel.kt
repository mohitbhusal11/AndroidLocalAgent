package com.localai.agent.featurenotifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.coredatabase.entity.StoredNotificationEntity
import com.localai.agent.coredatabase.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val notifications: StateFlow<List<StoredNotificationEntity>> =
        notificationRepository.observeRecent(30)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _summary = kotlinx.coroutines.flow.MutableStateFlow<String?>(null)
    val summary: StateFlow<String?> = _summary

    init {
        refreshSummary()
    }

    fun refreshSummary() {
        viewModelScope.launch {
            val recent = notificationRepository.observeRecent(5).first()
            _summary.value = if (recent.isEmpty()) {
                "Enable notification access in Settings to capture notifications."
            } else {
                recent.joinToString("\n") { "• [P${it.priority}] ${it.title}: ${it.text.take(50)}" }
            }
        }
    }

    fun smartReply(n: StoredNotificationEntity) = notificationRepository.smartReply(n)
}
