package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.FocusLimitEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
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
public class FocusLimitDao_Impl(
  __db: RoomDatabase,
) : FocusLimitDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfFocusLimitEntity: EntityInsertAdapter<FocusLimitEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfFocusLimitEntity = object : EntityInsertAdapter<FocusLimitEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `focus_limits` (`packageName`,`dailyLimitMinutes`,`isLocked`,`warningShown`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: FocusLimitEntity) {
        statement.bindText(1, entity.packageName)
        statement.bindLong(2, entity.dailyLimitMinutes.toLong())
        val _tmp: Int = if (entity.isLocked) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmp_1: Int = if (entity.warningShown) 1 else 0
        statement.bindLong(4, _tmp_1.toLong())
      }
    }
  }

  public override suspend fun upsert(limit: FocusLimitEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfFocusLimitEntity.insert(_connection, limit)
  }

  public override fun observeAll(): Flow<List<FocusLimitEntity>> {
    val _sql: String = "SELECT * FROM focus_limits"
    return createFlow(__db, false, arrayOf("focus_limits")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfDailyLimitMinutes: Int = getColumnIndexOrThrow(_stmt, "dailyLimitMinutes")
        val _columnIndexOfIsLocked: Int = getColumnIndexOrThrow(_stmt, "isLocked")
        val _columnIndexOfWarningShown: Int = getColumnIndexOrThrow(_stmt, "warningShown")
        val _result: MutableList<FocusLimitEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: FocusLimitEntity
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpDailyLimitMinutes: Int
          _tmpDailyLimitMinutes = _stmt.getLong(_columnIndexOfDailyLimitMinutes).toInt()
          val _tmpIsLocked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsLocked).toInt()
          _tmpIsLocked = _tmp != 0
          val _tmpWarningShown: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfWarningShown).toInt()
          _tmpWarningShown = _tmp_1 != 0
          _item =
              FocusLimitEntity(_tmpPackageName,_tmpDailyLimitMinutes,_tmpIsLocked,_tmpWarningShown)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun `get`(pkg: String): FocusLimitEntity? {
    val _sql: String = "SELECT * FROM focus_limits WHERE packageName = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, pkg)
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfDailyLimitMinutes: Int = getColumnIndexOrThrow(_stmt, "dailyLimitMinutes")
        val _columnIndexOfIsLocked: Int = getColumnIndexOrThrow(_stmt, "isLocked")
        val _columnIndexOfWarningShown: Int = getColumnIndexOrThrow(_stmt, "warningShown")
        val _result: FocusLimitEntity?
        if (_stmt.step()) {
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpDailyLimitMinutes: Int
          _tmpDailyLimitMinutes = _stmt.getLong(_columnIndexOfDailyLimitMinutes).toInt()
          val _tmpIsLocked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsLocked).toInt()
          _tmpIsLocked = _tmp != 0
          val _tmpWarningShown: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfWarningShown).toInt()
          _tmpWarningShown = _tmp_1 != 0
          _result =
              FocusLimitEntity(_tmpPackageName,_tmpDailyLimitMinutes,_tmpIsLocked,_tmpWarningShown)
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
