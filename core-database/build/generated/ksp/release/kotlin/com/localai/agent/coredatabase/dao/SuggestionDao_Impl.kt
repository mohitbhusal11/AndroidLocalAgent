package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.SuggestionEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SuggestionDao_Impl(
  __db: RoomDatabase,
) : SuggestionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSuggestionEntity: EntityInsertAdapter<SuggestionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSuggestionEntity = object : EntityInsertAdapter<SuggestionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `suggestions` (`id`,`type`,`title`,`message`,`actionHint`,`dismissed`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SuggestionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.type)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.message)
        statement.bindText(5, entity.actionHint)
        val _tmp: Int = if (entity.dismissed) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.createdAt)
      }
    }
  }

  public override suspend fun insert(suggestion: SuggestionEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfSuggestionEntity.insertAndReturnId(_connection, suggestion)
    _result
  }

  public override fun observeActive(): Flow<List<SuggestionEntity>> {
    val _sql: String =
        "SELECT * FROM suggestions WHERE dismissed = 0 ORDER BY createdAt DESC LIMIT 10"
    return createFlow(__db, false, arrayOf("suggestions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMessage: Int = getColumnIndexOrThrow(_stmt, "message")
        val _columnIndexOfActionHint: Int = getColumnIndexOrThrow(_stmt, "actionHint")
        val _columnIndexOfDismissed: Int = getColumnIndexOrThrow(_stmt, "dismissed")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<SuggestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SuggestionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMessage: String
          _tmpMessage = _stmt.getText(_columnIndexOfMessage)
          val _tmpActionHint: String
          _tmpActionHint = _stmt.getText(_columnIndexOfActionHint)
          val _tmpDismissed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfDismissed).toInt()
          _tmpDismissed = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              SuggestionEntity(_tmpId,_tmpType,_tmpTitle,_tmpMessage,_tmpActionHint,_tmpDismissed,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun dismiss(id: Long) {
    val _sql: String = "UPDATE suggestions SET dismissed = 1 WHERE id = ?"
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
