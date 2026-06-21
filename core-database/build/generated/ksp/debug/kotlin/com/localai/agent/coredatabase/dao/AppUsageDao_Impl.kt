package com.localai.agent.coredatabase.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.localai.agent.coredatabase.entity.AppUsageEntity
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
public class AppUsageDao_Impl(
  __db: RoomDatabase,
) : AppUsageDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAppUsageEntity: EntityInsertAdapter<AppUsageEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAppUsageEntity = object : EntityInsertAdapter<AppUsageEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `app_usage` (`id`,`packageName`,`appName`,`durationMs`,`launchCount`,`dateKey`,`recordedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AppUsageEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.packageName)
        statement.bindText(3, entity.appName)
        statement.bindLong(4, entity.durationMs)
        statement.bindLong(5, entity.launchCount.toLong())
        statement.bindText(6, entity.dateKey)
        statement.bindLong(7, entity.recordedAt)
      }
    }
  }

  public override suspend fun upsert(usage: AppUsageEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfAppUsageEntity.insert(_connection, usage)
  }

  public override fun observeByDate(dateKey: String): Flow<List<AppUsageEntity>> {
    val _sql: String = "SELECT * FROM app_usage WHERE dateKey = ? ORDER BY durationMs DESC"
    return createFlow(__db, false, arrayOf("app_usage")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, dateKey)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfAppName: Int = getColumnIndexOrThrow(_stmt, "appName")
        val _columnIndexOfDurationMs: Int = getColumnIndexOrThrow(_stmt, "durationMs")
        val _columnIndexOfLaunchCount: Int = getColumnIndexOrThrow(_stmt, "launchCount")
        val _columnIndexOfDateKey: Int = getColumnIndexOrThrow(_stmt, "dateKey")
        val _columnIndexOfRecordedAt: Int = getColumnIndexOrThrow(_stmt, "recordedAt")
        val _result: MutableList<AppUsageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AppUsageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpAppName: String
          _tmpAppName = _stmt.getText(_columnIndexOfAppName)
          val _tmpDurationMs: Long
          _tmpDurationMs = _stmt.getLong(_columnIndexOfDurationMs)
          val _tmpLaunchCount: Int
          _tmpLaunchCount = _stmt.getLong(_columnIndexOfLaunchCount).toInt()
          val _tmpDateKey: String
          _tmpDateKey = _stmt.getText(_columnIndexOfDateKey)
          val _tmpRecordedAt: Long
          _tmpRecordedAt = _stmt.getLong(_columnIndexOfRecordedAt)
          _item =
              AppUsageEntity(_tmpId,_tmpPackageName,_tmpAppName,_tmpDurationMs,_tmpLaunchCount,_tmpDateKey,_tmpRecordedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun totalForPackageToday(pkg: String, dateKey: String): Long? {
    val _sql: String = "SELECT SUM(durationMs) FROM app_usage WHERE packageName = ? AND dateKey = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, pkg)
        _argIndex = 2
        _stmt.bindText(_argIndex, dateKey)
        val _result: Long?
        if (_stmt.step()) {
          val _tmp: Long?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0)
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun topApps(dateKey: String): List<AppUsageEntity> {
    val _sql: String = "SELECT * FROM app_usage WHERE dateKey = ? ORDER BY durationMs DESC LIMIT 10"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, dateKey)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPackageName: Int = getColumnIndexOrThrow(_stmt, "packageName")
        val _columnIndexOfAppName: Int = getColumnIndexOrThrow(_stmt, "appName")
        val _columnIndexOfDurationMs: Int = getColumnIndexOrThrow(_stmt, "durationMs")
        val _columnIndexOfLaunchCount: Int = getColumnIndexOrThrow(_stmt, "launchCount")
        val _columnIndexOfDateKey: Int = getColumnIndexOrThrow(_stmt, "dateKey")
        val _columnIndexOfRecordedAt: Int = getColumnIndexOrThrow(_stmt, "recordedAt")
        val _result: MutableList<AppUsageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AppUsageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPackageName: String
          _tmpPackageName = _stmt.getText(_columnIndexOfPackageName)
          val _tmpAppName: String
          _tmpAppName = _stmt.getText(_columnIndexOfAppName)
          val _tmpDurationMs: Long
          _tmpDurationMs = _stmt.getLong(_columnIndexOfDurationMs)
          val _tmpLaunchCount: Int
          _tmpLaunchCount = _stmt.getLong(_columnIndexOfLaunchCount).toInt()
          val _tmpDateKey: String
          _tmpDateKey = _stmt.getText(_columnIndexOfDateKey)
          val _tmpRecordedAt: Long
          _tmpRecordedAt = _stmt.getLong(_columnIndexOfRecordedAt)
          _item =
              AppUsageEntity(_tmpId,_tmpPackageName,_tmpAppName,_tmpDurationMs,_tmpLaunchCount,_tmpDateKey,_tmpRecordedAt)
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
