package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.StoredNotificationEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NotificationDao_Impl(
  __db: RoomDatabase,
) : NotificationDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStoredNotificationEntity:
      EntityInsertAdapter<StoredNotificationEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfStoredNotificationEntity = object :
        EntityInsertAdapter<StoredNotificationEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `stored_notifications` (`key`,`packageName`,`title`,`text`,`category`,`priority`,`isRead`,`postedAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StoredNotificationEntity) {
        statement.bindText(1, entity.key)
        statement.bindText(2, entity.packageName)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.text)
        statement.bindText(5, entity.category)
        statement.bindLong(6, entity.priority.toLong())
        val _tmp: Int = if (entity.isRead) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        statement.bindLong(8, entity.postedAt)
      }
    }
  }

  public override suspend fun upsert(notification: StoredNotificationEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfStoredNotificationEntity.insert(_connection, notification)
  }

  public override fun observeRecent(limit: Int): Flow<List<StoredNotificationEntity>> {
    val _sql: String =
        "SELECT * FROM stored_notifications ORDER BY priority DESC, postedAt DESC LIMIT ?"
    return createFlow(__db, false, arrayOf("stored_notifications")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfIsRead: Int = getColumnIndexOrThrow(_stmt, "isRead")
        val _columnIndexOfPostedAt: Int = getColumnIndexOrThrow(_stmt, "postedAt")
        val _result: MutableList<StoredNotificationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StoredNotificationEntity
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpIsRead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRead).toInt()
          _tmpIsRead = _tmp != 0
          val _tmpPostedAt: Long
          _tmpPostedAt = _stmt.getLong(_columnIndexOfPostedAt)
          _item =
              StoredNotificationEntity(_tmpKey,_tmpPackageName,_tmpTitle,_tmpText,_tmpCategory,_tmpPriority,_tmpIsRead,_tmpPostedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeUnread(): Flow<List<StoredNotificationEntity>> {
    val _sql: String =
        "SELECT * FROM stored_notifications WHERE isRead = 0 ORDER BY priority DESC, postedAt DESC"
    return createFlow(__db, false, arrayOf("stored_notifications")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfIsRead: Int = getColumnIndexOrThrow(_stmt, "isRead")
        val _columnIndexOfPostedAt: Int = getColumnIndexOrThrow(_stmt, "postedAt")
        val _result: MutableList<StoredNotificationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StoredNotificationEntity
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpIsRead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRead).toInt()
          _tmpIsRead = _tmp != 0
          val _tmpPostedAt: Long
          _tmpPostedAt = _stmt.getLong(_columnIndexOfPostedAt)
          _item =
              StoredNotificationEntity(_tmpKey,_tmpPackageName,_tmpTitle,_tmpText,_tmpCategory,_tmpPriority,_tmpIsRead,_tmpPostedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun unreadCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM stored_notifications WHERE isRead = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markRead(key: String) {
    val _sql: String = "UPDATE stored_notifications SET isRead = 1 WHERE key = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOlderThan(before: Long) {
    val _sql: String = "DELETE FROM stored_notifications WHERE postedAt < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, before)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
