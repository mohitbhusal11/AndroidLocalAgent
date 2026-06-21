package com.localai.agent.corememory.routine

import com.localai.agent.coredatabase.dao.RoutineDao
import com.localai.agent.coredatabase.entity.RoutineEntity
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineTracker @Inject constructor(
    private val routineDao: RoutineDao
) {
    suspend fun recordAction(action: String) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val normalized = action.lowercase().trim().take(120)
        if (normalized.isBlank()) return
        val existing = routineDao.topForHour(hour).find { it.action == normalized }
        routineDao.upsert(
            RoutineEntity(
                id = existing?.id ?: 0,
                action = normalized,
                hourOfDay = hour,
                count = (existing?.count ?: 0) + 1,
                lastSeen = System.currentTimeMillis()
            )
        )
    }

    suspend fun getSuggestionsForNow(): List<RoutineEntity> {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return routineDao.topForHour(hour)
    }
}
