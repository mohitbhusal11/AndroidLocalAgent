package com.localai.agent.coredatabase.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stored_notifications", indices = [Index("postedAt"), Index("priority")])
data class StoredNotificationEntity(
    @PrimaryKey val key: String,
    val packageName: String,
    val title: String,
    val text: String,
    val category: String = "",
    val priority: Int = 0,
    val isRead: Boolean = false,
    val postedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_usage", indices = [Index("packageName"), Index("dateKey")])
data class AppUsageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val appName: String,
    val durationMs: Long,
    val launchCount: Int = 1,
    val dateKey: String,
    val recordedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "focus_limits")
data class FocusLimitEntity(
    @PrimaryKey val packageName: String,
    val dailyLimitMinutes: Int,
    val isLocked: Boolean = false,
    val warningShown: Boolean = false
)

@Entity(tableName = "tasks", indices = [Index("isDone"), Index("dueAt")])
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val dueAt: Long? = null,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "suggestions", indices = [Index("createdAt"), Index("dismissed")])
data class SuggestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val title: String,
    val message: String,
    val actionHint: String = "",
    val dismissed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "routines", indices = [Index("hourOfDay")])
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val action: String,
    val hourOfDay: Int,
    val dayMask: Int = 127,
    val count: Int = 1,
    val lastSeen: Long = System.currentTimeMillis()
)
