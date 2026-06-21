package com.localai.agent.featurenotifications.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.localai.agent.coredatabase.repository.NotificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AgentNotificationListenerService : NotificationListenerService() {

    @Inject lateinit var notificationRepository: NotificationRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn ?: return
        val extras = sbn.notification.extras
        val title = extras.getCharSequence("android.title")?.toString().orEmpty()
        val text = extras.getCharSequence("android.text")?.toString().orEmpty()
        if (title.isBlank() && text.isBlank()) return

        val priority = notificationRepository.detectPriority(title, text)
        scope.launch {
            notificationRepository.store(
                key = sbn.key,
                packageName = sbn.packageName,
                title = title,
                text = text,
                priority = priority
            )
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {}
}
