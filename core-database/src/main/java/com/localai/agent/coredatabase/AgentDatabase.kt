package com.localai.agent.coredatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.localai.agent.coredatabase.dao.AppUsageDao
import com.localai.agent.coredatabase.dao.ConversationDao
import com.localai.agent.coredatabase.dao.FocusLimitDao
import com.localai.agent.coredatabase.dao.MemoryDao
import com.localai.agent.coredatabase.dao.MessageDao
import com.localai.agent.coredatabase.dao.NotificationDao
import com.localai.agent.coredatabase.dao.RoutineDao
import com.localai.agent.coredatabase.dao.SuggestionDao
import com.localai.agent.coredatabase.dao.TaskDao
import com.localai.agent.coredatabase.dao.UserPreferenceDao
import com.localai.agent.coredatabase.entity.AppUsageEntity
import com.localai.agent.coredatabase.entity.ConversationEntity
import com.localai.agent.coredatabase.entity.FocusLimitEntity
import com.localai.agent.coredatabase.entity.MemoryEntity
import com.localai.agent.coredatabase.entity.MessageEntity
import com.localai.agent.coredatabase.entity.RoutineEntity
import com.localai.agent.coredatabase.entity.StoredNotificationEntity
import com.localai.agent.coredatabase.entity.SuggestionEntity
import com.localai.agent.coredatabase.entity.TaskEntity
import com.localai.agent.coredatabase.entity.UserPreferenceEntity

@Database(
    entities = [
        ConversationEntity::class,
        MessageEntity::class,
        MemoryEntity::class,
        UserPreferenceEntity::class,
        StoredNotificationEntity::class,
        AppUsageEntity::class,
        FocusLimitEntity::class,
        TaskEntity::class,
        SuggestionEntity::class,
        RoutineEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AgentDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun memoryDao(): MemoryDao
    abstract fun userPreferenceDao(): UserPreferenceDao
    abstract fun notificationDao(): NotificationDao
    abstract fun appUsageDao(): AppUsageDao
    abstract fun focusLimitDao(): FocusLimitDao
    abstract fun taskDao(): TaskDao
    abstract fun suggestionDao(): SuggestionDao
    abstract fun routineDao(): RoutineDao
}
