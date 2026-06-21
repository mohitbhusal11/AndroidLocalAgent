package com.localai.agent.coredatabase.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.TaskEntity
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
public class TaskDao_Impl(
  __db: RoomDatabase,
) : TaskDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTaskEntity: EntityInsertAdapter<TaskEntity>

  private val __updateAdapterOfTaskEntity: EntityDeleteOrUpdateAdapter<TaskEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTaskEntity = object : EntityInsertAdapter<TaskEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tasks` (`id`,`title`,`description`,`dueAt`,`isDone`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TaskEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmpDueAt: Long? = entity.dueAt
        if (_tmpDueAt == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpDueAt)
        }
        val _tmp: Int = if (entity.isDone) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.createdAt)
      }
    }
    this.__updateAdapterOfTaskEntity = object : EntityDeleteOrUpdateAdapter<TaskEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`dueAt` = ?,`isDone` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TaskEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmpDueAt: Long? = entity.dueAt
        if (_tmpDueAt == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpDueAt)
        }
        val _tmp: Int = if (entity.isDone) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.id)
      }
    }
  }

  public override suspend fun insert(task: TaskEntity): Long = performSuspending(__db, false, true)
      { _connection ->
    val _result: Long = __insertAdapterOfTaskEntity.insertAndReturnId(_connection, task)
    _result
  }

  public override suspend fun update(task: TaskEntity): Unit = performSuspending(__db, false, true)
      { _connection ->
    __updateAdapterOfTaskEntity.handle(_connection, task)
  }

  public override fun observeActive(): Flow<List<TaskEntity>> {
    val _sql: String = "SELECT * FROM tasks WHERE isDone = 0 ORDER BY dueAt ASC, createdAt DESC"
    return createFlow(__db, false, arrayOf("tasks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDueAt: Int = getColumnIndexOrThrow(_stmt, "dueAt")
        val _columnIndexOfIsDone: Int = getColumnIndexOrThrow(_stmt, "isDone")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<TaskEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TaskEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDueAt: Long?
          if (_stmt.isNull(_columnIndexOfDueAt)) {
            _tmpDueAt = null
          } else {
            _tmpDueAt = _stmt.getLong(_columnIndexOfDueAt)
          }
          val _tmpIsDone: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDone).toInt()
          _tmpIsDone = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDueAt,_tmpIsDone,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun complete(id: Long) {
    val _sql: String = "UPDATE tasks SET isDone = 1 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
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
