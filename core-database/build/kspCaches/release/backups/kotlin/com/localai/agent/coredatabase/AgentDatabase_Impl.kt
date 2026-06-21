package com.localai.agent.coredatabase

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.localai.agent.coredatabase.dao.AppUsageDao
import com.localai.agent.coredatabase.dao.AppUsageDao_Impl
import com.localai.agent.coredatabase.dao.ConversationDao
import com.localai.agent.coredatabase.dao.ConversationDao_Impl
import com.localai.agent.coredatabase.dao.FocusLimitDao
import com.localai.agent.coredatabase.dao.FocusLimitDao_Impl
import com.localai.agent.coredatabase.dao.MemoryDao
import com.localai.agent.coredatabase.dao.MemoryDao_Impl
import com.localai.agent.coredatabase.dao.MessageDao
import com.localai.agent.coredatabase.dao.MessageDao_Impl
import com.localai.agent.coredatabase.dao.NotificationDao
import com.localai.agent.coredatabase.dao.NotificationDao_Impl
import com.localai.agent.coredatabase.dao.RoutineDao
import com.localai.agent.coredatabase.dao.RoutineDao_Impl
import com.localai.agent.coredatabase.dao.SuggestionDao
import com.localai.agent.coredatabase.dao.SuggestionDao_Impl
import com.localai.agent.coredatabase.dao.TaskDao
import com.localai.agent.coredatabase.dao.TaskDao_Impl
import com.localai.agent.coredatabase.dao.UserPreferenceDao
import com.localai.agent.coredatabase.dao.UserPreferenceDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AgentDatabase_Impl : AgentDatabase() {
  private val _conversationDao: Lazy<ConversationDao> = lazy {
    ConversationDao_Impl(this)
  }

  private val _messageDao: Lazy<MessageDao> = lazy {
    MessageDao_Impl(this)
  }

  private val _memoryDao: Lazy<MemoryDao> = lazy {
    MemoryDao_Impl(this)
  }

  private val _userPreferenceDao: Lazy<UserPreferenceDao> = lazy {
    UserPreferenceDao_Impl(this)
  }

  private val _notificationDao: Lazy<NotificationDao> = lazy {
    NotificationDao_Impl(this)
  }

  private val _appUsageDao: Lazy<AppUsageDao> = lazy {
    AppUsageDao_Impl(this)
  }

  private val _focusLimitDao: Lazy<FocusLimitDao> = lazy {
    FocusLimitDao_Impl(this)
  }

  private val _taskDao: Lazy<TaskDao> = lazy {
    TaskDao_Impl(this)
  }

  private val _suggestionDao: Lazy<SuggestionDao> = lazy {
    SuggestionDao_Impl(this)
  }

  private val _routineDao: Lazy<RoutineDao> = lazy {
    RoutineDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "ec0ad636afb63098a6956777e98f1841", "d8b6e7f6e0700c487fd4e0e305b5b1db") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `conversations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `isPinned` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_conversations_updatedAt` ON `conversations` (`updatedAt`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `conversationId` INTEGER NOT NULL, `role` TEXT NOT NULL, `content` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, FOREIGN KEY(`conversationId`) REFERENCES `conversations`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_messages_conversationId` ON `messages` (`conversationId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_messages_timestamp` ON `messages` (`timestamp`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `memories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `key` TEXT NOT NULL, `value` TEXT NOT NULL, `metadata` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_memories_type` ON `memories` (`type`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_memories_key` ON `memories` (`key`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user_preferences` (`key` TEXT NOT NULL, `value` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`key`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `stored_notifications` (`key` TEXT NOT NULL, `packageName` TEXT NOT NULL, `title` TEXT NOT NULL, `text` TEXT NOT NULL, `category` TEXT NOT NULL, `priority` INTEGER NOT NULL, `isRead` INTEGER NOT NULL, `postedAt` INTEGER NOT NULL, PRIMARY KEY(`key`))")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_stored_notifications_postedAt` ON `stored_notifications` (`postedAt`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_stored_notifications_priority` ON `stored_notifications` (`priority`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `app_usage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `durationMs` INTEGER NOT NULL, `launchCount` INTEGER NOT NULL, `dateKey` TEXT NOT NULL, `recordedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_app_usage_packageName` ON `app_usage` (`packageName`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_app_usage_dateKey` ON `app_usage` (`dateKey`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `focus_limits` (`packageName` TEXT NOT NULL, `dailyLimitMinutes` INTEGER NOT NULL, `isLocked` INTEGER NOT NULL, `warningShown` INTEGER NOT NULL, PRIMARY KEY(`packageName`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `dueAt` INTEGER, `isDone` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_isDone` ON `tasks` (`isDone`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_dueAt` ON `tasks` (`dueAt`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `suggestions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `title` TEXT NOT NULL, `message` TEXT NOT NULL, `actionHint` TEXT NOT NULL, `dismissed` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_suggestions_createdAt` ON `suggestions` (`createdAt`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_suggestions_dismissed` ON `suggestions` (`dismissed`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `routines` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `action` TEXT NOT NULL, `hourOfDay` INTEGER NOT NULL, `dayMask` INTEGER NOT NULL, `count` INTEGER NOT NULL, `lastSeen` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_routines_hourOfDay` ON `routines` (`hourOfDay`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ec0ad636afb63098a6956777e98f1841')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `conversations`")
        connection.execSQL("DROP TABLE IF EXISTS `messages`")
        connection.execSQL("DROP TABLE IF EXISTS `memories`")
        connection.execSQL("DROP TABLE IF EXISTS `user_preferences`")
        connection.execSQL("DROP TABLE IF EXISTS `stored_notifications`")
        connection.execSQL("DROP TABLE IF EXISTS `app_usage`")
        connection.execSQL("DROP TABLE IF EXISTS `focus_limits`")
        connection.execSQL("DROP TABLE IF EXISTS `tasks`")
        connection.execSQL("DROP TABLE IF EXISTS `suggestions`")
        connection.execSQL("DROP TABLE IF EXISTS `routines`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsConversations: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsConversations.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsConversations.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsConversations.put("isPinned", TableInfo.Column("isPinned", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsConversations.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsConversations.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysConversations: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesConversations: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesConversations.add(TableInfo.Index("index_conversations_updatedAt", false,
            listOf("updatedAt"), listOf("ASC")))
        val _infoConversations: TableInfo = TableInfo("conversations", _columnsConversations,
            _foreignKeysConversations, _indicesConversations)
        val _existingConversations: TableInfo = read(connection, "conversations")
        if (!_infoConversations.equals(_existingConversations)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |conversations(com.localai.agent.coredatabase.entity.ConversationEntity).
              | Expected:
              |""".trimMargin() + _infoConversations + """
              |
              | Found:
              |""".trimMargin() + _existingConversations)
        }
        val _columnsMessages: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMessages.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("conversationId", TableInfo.Column("conversationId", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("role", TableInfo.Column("role", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("content", TableInfo.Column("content", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMessages: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysMessages.add(TableInfo.ForeignKey("conversations", "CASCADE", "NO ACTION",
            listOf("conversationId"), listOf("id")))
        val _indicesMessages: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesMessages.add(TableInfo.Index("index_messages_conversationId", false,
            listOf("conversationId"), listOf("ASC")))
        _indicesMessages.add(TableInfo.Index("index_messages_timestamp", false, listOf("timestamp"),
            listOf("ASC")))
        val _infoMessages: TableInfo = TableInfo("messages", _columnsMessages, _foreignKeysMessages,
            _indicesMessages)
        val _existingMessages: TableInfo = read(connection, "messages")
        if (!_infoMessages.equals(_existingMessages)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |messages(com.localai.agent.coredatabase.entity.MessageEntity).
              | Expected:
              |""".trimMargin() + _infoMessages + """
              |
              | Found:
              |""".trimMargin() + _existingMessages)
        }
        val _columnsMemories: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMemories.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("key", TableInfo.Column("key", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("value", TableInfo.Column("value", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("metadata", TableInfo.Column("metadata", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMemories.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMemories: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMemories: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesMemories.add(TableInfo.Index("index_memories_type", false, listOf("type"),
            listOf("ASC")))
        _indicesMemories.add(TableInfo.Index("index_memories_key", false, listOf("key"),
            listOf("ASC")))
        val _infoMemories: TableInfo = TableInfo("memories", _columnsMemories, _foreignKeysMemories,
            _indicesMemories)
        val _existingMemories: TableInfo = read(connection, "memories")
        if (!_infoMemories.equals(_existingMemories)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |memories(com.localai.agent.coredatabase.entity.MemoryEntity).
              | Expected:
              |""".trimMargin() + _infoMemories + """
              |
              | Found:
              |""".trimMargin() + _existingMemories)
        }
        val _columnsUserPreferences: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserPreferences.put("key", TableInfo.Column("key", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserPreferences.put("value", TableInfo.Column("value", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserPreferences.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserPreferences: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUserPreferences: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUserPreferences: TableInfo = TableInfo("user_preferences", _columnsUserPreferences,
            _foreignKeysUserPreferences, _indicesUserPreferences)
        val _existingUserPreferences: TableInfo = read(connection, "user_preferences")
        if (!_infoUserPreferences.equals(_existingUserPreferences)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user_preferences(com.localai.agent.coredatabase.entity.UserPreferenceEntity).
              | Expected:
              |""".trimMargin() + _infoUserPreferences + """
              |
              | Found:
              |""".trimMargin() + _existingUserPreferences)
        }
        val _columnsStoredNotifications: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStoredNotifications.put("key", TableInfo.Column("key", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("packageName", TableInfo.Column("packageName", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("text", TableInfo.Column("text", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("category", TableInfo.Column("category", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("priority", TableInfo.Column("priority", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("isRead", TableInfo.Column("isRead", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStoredNotifications.put("postedAt", TableInfo.Column("postedAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStoredNotifications: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStoredNotifications: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesStoredNotifications.add(TableInfo.Index("index_stored_notifications_postedAt",
            false, listOf("postedAt"), listOf("ASC")))
        _indicesStoredNotifications.add(TableInfo.Index("index_stored_notifications_priority",
            false, listOf("priority"), listOf("ASC")))
        val _infoStoredNotifications: TableInfo = TableInfo("stored_notifications",
            _columnsStoredNotifications, _foreignKeysStoredNotifications,
            _indicesStoredNotifications)
        val _existingStoredNotifications: TableInfo = read(connection, "stored_notifications")
        if (!_infoStoredNotifications.equals(_existingStoredNotifications)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |stored_notifications(com.localai.agent.coredatabase.entity.StoredNotificationEntity).
              | Expected:
              |""".trimMargin() + _infoStoredNotifications + """
              |
              | Found:
              |""".trimMargin() + _existingStoredNotifications)
        }
        val _columnsAppUsage: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAppUsage.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("packageName", TableInfo.Column("packageName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("appName", TableInfo.Column("appName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("durationMs", TableInfo.Column("durationMs", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("launchCount", TableInfo.Column("launchCount", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("dateKey", TableInfo.Column("dateKey", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppUsage.put("recordedAt", TableInfo.Column("recordedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAppUsage: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAppUsage: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAppUsage.add(TableInfo.Index("index_app_usage_packageName", false,
            listOf("packageName"), listOf("ASC")))
        _indicesAppUsage.add(TableInfo.Index("index_app_usage_dateKey", false, listOf("dateKey"),
            listOf("ASC")))
        val _infoAppUsage: TableInfo = TableInfo("app_usage", _columnsAppUsage,
            _foreignKeysAppUsage, _indicesAppUsage)
        val _existingAppUsage: TableInfo = read(connection, "app_usage")
        if (!_infoAppUsage.equals(_existingAppUsage)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |app_usage(com.localai.agent.coredatabase.entity.AppUsageEntity).
              | Expected:
              |""".trimMargin() + _infoAppUsage + """
              |
              | Found:
              |""".trimMargin() + _existingAppUsage)
        }
        val _columnsFocusLimits: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsFocusLimits.put("packageName", TableInfo.Column("packageName", "TEXT", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFocusLimits.put("dailyLimitMinutes", TableInfo.Column("dailyLimitMinutes",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFocusLimits.put("isLocked", TableInfo.Column("isLocked", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsFocusLimits.put("warningShown", TableInfo.Column("warningShown", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysFocusLimits: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesFocusLimits: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoFocusLimits: TableInfo = TableInfo("focus_limits", _columnsFocusLimits,
            _foreignKeysFocusLimits, _indicesFocusLimits)
        val _existingFocusLimits: TableInfo = read(connection, "focus_limits")
        if (!_infoFocusLimits.equals(_existingFocusLimits)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |focus_limits(com.localai.agent.coredatabase.entity.FocusLimitEntity).
              | Expected:
              |""".trimMargin() + _infoFocusLimits + """
              |
              | Found:
              |""".trimMargin() + _existingFocusLimits)
        }
        val _columnsTasks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTasks.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTasks.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTasks.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTasks.put("dueAt", TableInfo.Column("dueAt", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTasks.put("isDone", TableInfo.Column("isDone", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTasks.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTasks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTasks: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTasks.add(TableInfo.Index("index_tasks_isDone", false, listOf("isDone"),
            listOf("ASC")))
        _indicesTasks.add(TableInfo.Index("index_tasks_dueAt", false, listOf("dueAt"),
            listOf("ASC")))
        val _infoTasks: TableInfo = TableInfo("tasks", _columnsTasks, _foreignKeysTasks,
            _indicesTasks)
        val _existingTasks: TableInfo = read(connection, "tasks")
        if (!_infoTasks.equals(_existingTasks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tasks(com.localai.agent.coredatabase.entity.TaskEntity).
              | Expected:
              |""".trimMargin() + _infoTasks + """
              |
              | Found:
              |""".trimMargin() + _existingTasks)
        }
        val _columnsSuggestions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSuggestions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("message", TableInfo.Column("message", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("actionHint", TableInfo.Column("actionHint", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("dismissed", TableInfo.Column("dismissed", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSuggestions.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSuggestions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSuggestions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSuggestions.add(TableInfo.Index("index_suggestions_createdAt", false,
            listOf("createdAt"), listOf("ASC")))
        _indicesSuggestions.add(TableInfo.Index("index_suggestions_dismissed", false,
            listOf("dismissed"), listOf("ASC")))
        val _infoSuggestions: TableInfo = TableInfo("suggestions", _columnsSuggestions,
            _foreignKeysSuggestions, _indicesSuggestions)
        val _existingSuggestions: TableInfo = read(connection, "suggestions")
        if (!_infoSuggestions.equals(_existingSuggestions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |suggestions(com.localai.agent.coredatabase.entity.SuggestionEntity).
              | Expected:
              |""".trimMargin() + _infoSuggestions + """
              |
              | Found:
              |""".trimMargin() + _existingSuggestions)
        }
        val _columnsRoutines: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRoutines.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRoutines.put("action", TableInfo.Column("action", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRoutines.put("hourOfDay", TableInfo.Column("hourOfDay", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRoutines.put("dayMask", TableInfo.Column("dayMask", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRoutines.put("count", TableInfo.Column("count", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRoutines.put("lastSeen", TableInfo.Column("lastSeen", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRoutines: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesRoutines: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesRoutines.add(TableInfo.Index("index_routines_hourOfDay", false, listOf("hourOfDay"),
            listOf("ASC")))
        val _infoRoutines: TableInfo = TableInfo("routines", _columnsRoutines, _foreignKeysRoutines,
            _indicesRoutines)
        val _existingRoutines: TableInfo = read(connection, "routines")
        if (!_infoRoutines.equals(_existingRoutines)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |routines(com.localai.agent.coredatabase.entity.RoutineEntity).
              | Expected:
              |""".trimMargin() + _infoRoutines + """
              |
              | Found:
              |""".trimMargin() + _existingRoutines)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "conversations", "messages",
        "memories", "user_preferences", "stored_notifications", "app_usage", "focus_limits",
        "tasks", "suggestions", "routines")
  }

  public override fun clearAllTables() {
    super.performClear(true, "conversations", "messages", "memories", "user_preferences",
        "stored_notifications", "app_usage", "focus_limits", "tasks", "suggestions", "routines")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ConversationDao::class, ConversationDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(MessageDao::class, MessageDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(MemoryDao::class, MemoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserPreferenceDao::class, UserPreferenceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(NotificationDao::class, NotificationDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AppUsageDao::class, AppUsageDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(FocusLimitDao::class, FocusLimitDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TaskDao::class, TaskDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SuggestionDao::class, SuggestionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(RoutineDao::class, RoutineDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun conversationDao(): ConversationDao = _conversationDao.value

  public override fun messageDao(): MessageDao = _messageDao.value

  public override fun memoryDao(): MemoryDao = _memoryDao.value

  public override fun userPreferenceDao(): UserPreferenceDao = _userPreferenceDao.value

  public override fun notificationDao(): NotificationDao = _notificationDao.value

  public override fun appUsageDao(): AppUsageDao = _appUsageDao.value

  public override fun focusLimitDao(): FocusLimitDao = _focusLimitDao.value

  public override fun taskDao(): TaskDao = _taskDao.value

  public override fun suggestionDao(): SuggestionDao = _suggestionDao.value

  public override fun routineDao(): RoutineDao = _routineDao.value
}
