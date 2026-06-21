package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.UserPreferenceEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UserPreferenceDao_Impl(
  __db: RoomDatabase,
) : UserPreferenceDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserPreferenceEntity: EntityInsertAdapter<UserPreferenceEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUserPreferenceEntity = object :
        EntityInsertAdapter<UserPreferenceEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `user_preferences` (`key`,`value`,`updatedAt`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserPreferenceEntity) {
        statement.bindText(1, entity.key)
        statement.bindText(2, entity.value)
        statement.bindLong(3, entity.updatedAt)
      }
    }
  }

  public override suspend fun upsert(preference: UserPreferenceEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfUserPreferenceEntity.insert(_connection, preference)
  }

  public override suspend fun `get`(key: String): UserPreferenceEntity? {
    val _sql: String = "SELECT * FROM user_preferences WHERE `key` = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: UserPreferenceEntity?
        if (_stmt.step()) {
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpValue: String
          _tmpValue = _stmt.getText(_columnIndexOfValue)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = UserPreferenceEntity(_tmpKey,_tmpValue,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
