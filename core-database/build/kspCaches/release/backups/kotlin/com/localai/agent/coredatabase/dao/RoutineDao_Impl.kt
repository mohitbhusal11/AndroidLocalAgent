package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.RoutineEntity
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

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class RoutineDao_Impl(
  __db: RoomDatabase,
) : RoutineDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfRoutineEntity: EntityInsertAdapter<RoutineEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfRoutineEntity = object : EntityInsertAdapter<RoutineEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `routines` (`id`,`action`,`hourOfDay`,`dayMask`,`count`,`lastSeen`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RoutineEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.action)
        statement.bindLong(3, entity.hourOfDay.toLong())
        statement.bindLong(4, entity.dayMask.toLong())
        statement.bindLong(5, entity.count.toLong())
        statement.bindLong(6, entity.lastSeen)
      }
    }
  }

  public override suspend fun upsert(routine: RoutineEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfRoutineEntity.insert(_connection, routine)
  }

  public override suspend fun topForHour(hour: Int): List<RoutineEntity> {
    val _sql: String = "SELECT * FROM routines WHERE hourOfDay = ? ORDER BY count DESC LIMIT 5"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, hour.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAction: Int = getColumnIndexOrThrow(_stmt, "action")
        val _columnIndexOfHourOfDay: Int = getColumnIndexOrThrow(_stmt, "hourOfDay")
        val _columnIndexOfDayMask: Int = getColumnIndexOrThrow(_stmt, "dayMask")
        val _columnIndexOfCount: Int = getColumnIndexOrThrow(_stmt, "count")
        val _columnIndexOfLastSeen: Int = getColumnIndexOrThrow(_stmt, "lastSeen")
        val _result: MutableList<RoutineEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RoutineEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAction: String
          _tmpAction = _stmt.getText(_columnIndexOfAction)
          val _tmpHourOfDay: Int
          _tmpHourOfDay = _stmt.getLong(_columnIndexOfHourOfDay).toInt()
          val _tmpDayMask: Int
          _tmpDayMask = _stmt.getLong(_columnIndexOfDayMask).toInt()
          val _tmpCount: Int
          _tmpCount = _stmt.getLong(_columnIndexOfCount).toInt()
          val _tmpLastSeen: Long
          _tmpLastSeen = _stmt.getLong(_columnIndexOfLastSeen)
          _item = RoutineEntity(_tmpId,_tmpAction,_tmpHourOfDay,_tmpDayMask,_tmpCount,_tmpLastSeen)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun topRoutines(): List<RoutineEntity> {
    val _sql: String = "SELECT * FROM routines ORDER BY count DESC LIMIT 10"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAction: Int = getColumnIndexOrThrow(_stmt, "action")
        val _columnIndexOfHourOfDay: Int = getColumnIndexOrThrow(_stmt, "hourOfDay")
        val _columnIndexOfDayMask: Int = getColumnIndexOrThrow(_stmt, "dayMask")
        val _columnIndexOfCount: Int = getColumnIndexOrThrow(_stmt, "count")
        val _columnIndexOfLastSeen: Int = getColumnIndexOrThrow(_stmt, "lastSeen")
        val _result: MutableList<RoutineEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RoutineEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAction: String
          _tmpAction = _stmt.getText(_columnIndexOfAction)
          val _tmpHourOfDay: Int
          _tmpHourOfDay = _stmt.getLong(_columnIndexOfHourOfDay).toInt()
          val _tmpDayMask: Int
          _tmpDayMask = _stmt.getLong(_columnIndexOfDayMask).toInt()
          val _tmpCount: Int
          _tmpCount = _stmt.getLong(_columnIndexOfCount).toInt()
          val _tmpLastSeen: Long
          _tmpLastSeen = _stmt.getLong(_columnIndexOfLastSeen)
          _item = RoutineEntity(_tmpId,_tmpAction,_tmpHourOfDay,_tmpDayMask,_tmpCount,_tmpLastSeen)
          _result.add(_item)
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
