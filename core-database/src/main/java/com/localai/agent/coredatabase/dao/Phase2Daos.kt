package com.localai.agent.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.localai.agent.coredatabase.entity.AppUsageEntity
import com.localai.agent.coredatabase.entity.FocusLimitEntity
import com.localai.agent.coredatabase.entity.RoutineEntity
import com.localai.agent.coredatabase.entity.StoredNotificationEntity
import com.localai.agent.coredatabase.entity.SuggestionEntity
import com.localai.agent.coredatabase.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM stored_notifications ORDER BY priority DESC, postedAt DESC LIMIT :limit")
    fun observeRecent(limit: Int = 50): Flow<List<StoredNotificationEntity>>

    @Query("SELECT * FROM stored_notifications WHERE isRead = 0 ORDER BY priority DESC, postedAt DESC")
    fun observeUnread(): Flow<List<StoredNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(notification: StoredNotificationEntity)

    @Query("UPDATE stored_notifications SET isRead = 1 WHERE key = :key")
    suspend fun markRead(key: String)

    @Query("DELETE FROM stored_notifications WHERE postedAt < :before")
    suspend fun deleteOlderThan(before: Long)

    @Query("SELECT COUNT(*) FROM stored_notifications WHERE isRead = 0")
    suspend fun unreadCount(): Int
}

@Dao
interface AppUsageDao {
    @Query("SELECT * FROM app_usage WHERE dateKey = :dateKey ORDER BY durationMs DESC")
    fun observeByDate(dateKey: String): Flow<List<AppUsageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(usage: AppUsageEntity)

    @Query("SELECT SUM(durationMs) FROM app_usage WHERE packageName = :pkg AND dateKey = :dateKey")
    suspend fun totalForPackageToday(pkg: String, dateKey: String): Long?

    @Query("SELECT * FROM app_usage WHERE dateKey = :dateKey ORDER BY durationMs DESC LIMIT 10")
    suspend fun topApps(dateKey: String): List<AppUsageEntity>
}

@Dao
interface FocusLimitDao {
    @Query("SELECT * FROM focus_limits")
    fun observeAll(): Flow<List<FocusLimitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(limit: FocusLimitEntity)

    @Query("SELECT * FROM focus_limits WHERE packageName = :pkg LIMIT 1")
    suspend fun get(pkg: String): FocusLimitEntity?
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE isDone = 0 ORDER BY dueAt ASC, createdAt DESC")
    fun observeActive(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity)

    @Query("UPDATE tasks SET isDone = 1 WHERE id = :id")
    suspend fun complete(id: Long)
}

@Dao
interface SuggestionDao {
    @Query("SELECT * FROM suggestions WHERE dismissed = 0 ORDER BY createdAt DESC LIMIT 10")
    fun observeActive(): Flow<List<SuggestionEntity>>

    @Insert
    suspend fun insert(suggestion: SuggestionEntity): Long

    @Query("UPDATE suggestions SET dismissed = 1 WHERE id = :id")
    suspend fun dismiss(id: Long)
}

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(routine: RoutineEntity)

    @Query("SELECT * FROM routines WHERE hourOfDay = :hour ORDER BY count DESC LIMIT 5")
    suspend fun topForHour(hour: Int): List<RoutineEntity>

    @Query("SELECT * FROM routines ORDER BY count DESC LIMIT 10")
    suspend fun topRoutines(): List<RoutineEntity>
}
