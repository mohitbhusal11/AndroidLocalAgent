package com.localai.agent.featuredoomscroll.tracker

import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import com.localai.agent.coredatabase.dao.AppUsageDao
import com.localai.agent.coredatabase.dao.FocusLimitDao
import com.localai.agent.coredatabase.entity.AppUsageEntity
import com.localai.agent.coredatabase.entity.FocusLimitEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageTracker @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appUsageDao: AppUsageDao,
    private val focusLimitDao: FocusLimitDao
) {
    val doomscrollPackages = setOf(
        "com.google.android.youtube",
        "com.instagram.android",
        "com.zhiliaoapp.musically",
        "com.ss.android.ugc.trill",
        "com.facebook.katana"
    )

    fun hasUsagePermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun usageSettingsIntent(): Intent =
        Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    suspend fun syncTodayUsage() {
        if (!hasUsagePermission()) return
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val end = System.currentTimeMillis()
        val start = end - 86400000
        val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end)
        val dateKey = todayKey()
        stats.filter { it.totalTimeInForeground > 0 }.forEach { stat ->
            appUsageDao.upsert(
                AppUsageEntity(
                    packageName = stat.packageName,
                    appName = stat.packageName.substringAfterLast('.'),
                    durationMs = stat.totalTimeInForeground,
                    launchCount = 1,
                    dateKey = dateKey
                )
            )
        }
    }

    suspend fun getReport(): String {
        syncTodayUsage()
        val top = appUsageDao.topApps(todayKey())
        if (top.isEmpty()) return "No usage data. Grant Usage Access in Settings."
        return top.joinToString("\n") {
            val mins = it.durationMs / 60000
            "• ${it.appName}: ${mins}m"
        }
    }

    suspend fun checkDoomscroll(): String? {
        val dateKey = todayKey()
        for (pkg in doomscrollPackages) {
            val used = appUsageDao.totalForPackageToday(pkg, dateKey) ?: 0
            val limit = focusLimitDao.get(pkg)?.dailyLimitMinutes ?: 30
            val usedMinutes = used / 60000
            if (usedMinutes >= limit) {
                return "⚠️ ${pkg.substringAfterLast('.')} limit reached ($usedMinutes/$limit min). Focus mode active."
            }
            if (usedMinutes >= limit * 0.8) {
                return "⚠️ Approaching limit on ${pkg.substringAfterLast('.')} ($usedMinutes/$limit min)"
            }
        }
        return null
    }

    suspend fun setLimit(packageName: String, minutes: Int) {
        focusLimitDao.upsert(FocusLimitEntity(packageName, minutes))
    }

    suspend fun lockApp(packageName: String, locked: Boolean) {
        val existing = focusLimitDao.get(packageName)
        focusLimitDao.upsert(
            FocusLimitEntity(
                packageName = packageName,
                dailyLimitMinutes = existing?.dailyLimitMinutes ?: 30,
                isLocked = locked
            )
        )
    }

    fun isDoomscrollApp(packageName: String): Boolean = packageName in doomscrollPackages

    private fun todayKey(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}
