package com.localai.agent.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.localai.agent.suggestions.SuggestionEngine
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class SuggestionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val suggestionEngine: SuggestionEngine
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        suggestionEngine.generateProactiveSuggestions()
        return Result.success()
    }

    companion object {
        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<SuggestionWorker>(6, TimeUnit.HOURS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "suggestion_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
