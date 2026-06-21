package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.MemoryEntity
import javax.`annotation`.processing.Generated
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
public class MemoryDao_Impl(
  __db: RoomDatabase,
) : MemoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMemoryEntity: EntityInsertAdapter<MemoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfMemoryEntity = object : EntityInsertAdapter<MemoryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `memories` (`id`,`type`,`key`,`value`,`metadata`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MemoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.type)
        statement.bindText(3, entity.key)
        statement.bindText(4, entity.value)
        statement.bindText(5, entity.metadata)
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.updatedAt)
      }
    }
  }

  public override suspend fun upsert(memory: MemoryEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfMemoryEntity.insert(_connection, memory)
  }

  public override fun observeAll(): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM memories ORDER BY updatedAt DESC"
    return createFlow(__db, false, arrayOf("memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfMetadata: Int = getColumnIndexOrThrow(_stmt, "metadata")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpValue: String
          _tmpValue = _stmt.getText(_columnIndexOfValue)
          val _tmpMetadata: String
          _tmpMetadata = _stmt.getText(_columnIndexOfMetadata)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              MemoryEntity(_tmpId,_tmpType,_tmpKey,_tmpValue,_tmpMetadata,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeByType(type: String): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM memories WHERE type = ? ORDER BY updatedAt DESC"
    return createFlow(__db, false, arrayOf("memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, type)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfMetadata: Int = getColumnIndexOrThrow(_stmt, "metadata")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpValue: String
          _tmpValue = _stmt.getText(_columnIndexOfValue)
          val _tmpMetadata: String
          _tmpMetadata = _stmt.getText(_columnIndexOfMetadata)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              MemoryEntity(_tmpId,_tmpType,_tmpKey,_tmpValue,_tmpMetadata,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByKey(key: String): MemoryEntity? {
    val _sql: String = "SELECT * FROM memories WHERE key = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfMetadata: Int = getColumnIndexOrThrow(_stmt, "metadata")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MemoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpValue: String
          _tmpValue = _stmt.getText(_columnIndexOfValue)
          val _tmpMetadata: String
          _tmpMetadata = _stmt.getText(_columnIndexOfMetadata)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              MemoryEntity(_tmpId,_tmpType,_tmpKey,_tmpValue,_tmpMetadata,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(id: Long) {
    val _sql: String = "DELETE FROM memories WHERE id = ?"
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
