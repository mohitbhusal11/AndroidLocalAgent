package com.localai.agent.coredatabase.repository

import com.localai.agent.coredatabase.dao.NotificationDao
import com.localai.agent.coredatabase.entity.StoredNotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {
    fun observeRecent(limit: Int = 50): Flow<List<StoredNotificationEntity>> =
        notificationDao.observeRecent(limit)

    fun observeUnread(): Flow<List<StoredNotificationEntity>> = notificationDao.observeUnread()

    suspend fun store(
        key: String,
        packageName: String,
        title: String,
        text: String,
        priority: Int
    ) {
        notificationDao.upsert(
            StoredNotificationEntity(
                key = key,
                packageName = packageName,
                title = title,
                text = text,
                priority = priority
            )
        )
    }

    suspend fun markRead(key: String) = notificationDao.markRead(key)
    suspend fun unreadCount(): Int = notificationDao.unreadCount()

    fun smartReply(notification: StoredNotificationEntity): String = when {
        notification.text.contains("?", ignoreCase = true) -> "Yes, I'll get back to you soon."
        notification.packageName.contains("whatsapp") -> "On my way!"
        notification.title.contains("meeting", ignoreCase = true) -> "I'll be there."
        else -> "Thanks for letting me know!"
    }

    fun detectPriority(title: String, text: String): Int {
        val combined = "$title $text".lowercase()
        return when {
            combined.contains("urgent") || combined.contains("emergency") -> 3
            combined.contains("otp") || combined.contains("code") -> 3
            combined.contains("payment") || combined.contains("bank") -> 2
            combined.contains("meeting") || combined.contains("call") -> 2
            else -> 1
        }
    }
}
