package com.localai.agent.suggestions

import com.localai.agent.coredatabase.dao.SuggestionDao
import com.localai.agent.coredatabase.entity.SuggestionEntity
import com.localai.agent.corememory.routine.RoutineTracker
import com.localai.agent.featuredoomscroll.tracker.UsageTracker
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionEngine @Inject constructor(
    private val suggestionDao: SuggestionDao,
    private val routineTracker: RoutineTracker,
    private val usageTracker: UsageTracker
) {
    suspend fun generateProactiveSuggestions() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        routineTracker.getSuggestionsForNow().firstOrNull()?.let { routine ->
            suggestionDao.insert(
                SuggestionEntity(
                    type = "ROUTINE",
                    title = "Usual activity",
                    message = "You often do \"${routine.action}\" around this time.",
                    actionHint = routine.action
                )
            )
        }

        usageTracker.checkDoomscroll()?.let { warning ->
            suggestionDao.insert(
                SuggestionEntity(
                    type = "FOCUS",
                    title = "Focus mode",
                    message = warning,
                    actionHint = "Open Focus settings"
                )
            )
        }

        if (hour in 8..9) {
            suggestionDao.insert(
                SuggestionEntity(
                    type = "MORNING",
                    title = "Good morning",
                    message = "Check your tasks and today's schedule.",
                    actionHint = "Open productivity"
                )
            )
        }
    }
}
