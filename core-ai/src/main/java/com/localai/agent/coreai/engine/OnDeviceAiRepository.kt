package com.localai.agent.coreai.engine

import com.google.mlkit.genai.common.FeatureStatus
import com.localai.agent.core.model.AiProvider
import com.localai.agent.core.model.AiStreamEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnDeviceAiRepository @Inject constructor(
    private val geminiNanoEngine: GeminiNanoEngine,
    private val localAssistantEngine: LocalAssistantEngine,
    private val settingsRepository: com.localai.agent.corenetwork.SettingsRepository
) {
    suspend fun getStatus(): OnDeviceAiStatus {
        val status = geminiNanoEngine.checkStatus()
        val modelName = geminiNanoEngine.getModelName()
        return when (status) {
            FeatureStatus.AVAILABLE -> OnDeviceAiStatus(
                provider = AiProvider.GEMINI_NANO,
                featureStatus = "AVAILABLE",
                modelName = modelName,
                isReady = true,
                message = "Gemini Nano is ready (${modelName ?: "on-device"})"
            )
            FeatureStatus.DOWNLOADABLE -> OnDeviceAiStatus(
                provider = AiProvider.GEMINI_NANO,
                featureStatus = "DOWNLOADABLE",
                modelName = modelName,
                isReady = false,
                message = "Gemini Nano can be downloaded — tap Download in Settings"
            )
            FeatureStatus.DOWNLOADING -> OnDeviceAiStatus(
                provider = AiProvider.GEMINI_NANO,
                featureStatus = "DOWNLOADING",
                modelName = modelName,
                isReady = false,
                message = "Gemini Nano is downloading…"
            )
            FeatureStatus.UNAVAILABLE -> OnDeviceAiStatus(
                provider = AiProvider.LOCAL_FALLBACK,
                featureStatus = "UNAVAILABLE",
                modelName = null,
                isReady = true,
                message = "Gemini Nano unavailable — using local assistant (still fully offline)"
            )
            else -> OnDeviceAiStatus(
                provider = AiProvider.LOCAL_FALLBACK,
                featureStatus = "UNKNOWN",
                modelName = null,
                isReady = true,
                message = "Using local offline assistant"
            )
        }
    }

    suspend fun currentProvider(): AiProvider {
        if (settingsRepository.isForceLocalAssistantSync()) return AiProvider.LOCAL_FALLBACK
        return if (geminiNanoEngine.checkStatus() == FeatureStatus.AVAILABLE) {
            AiProvider.GEMINI_NANO
        } else {
            AiProvider.LOCAL_FALLBACK
        }
    }

    fun streamResponse(
        systemPrompt: String,
        history: List<Pair<String, String>>,
        userMessage: String
    ): Flow<AiStreamEvent> = flow {
        val forceLocal = settingsRepository.isForceLocalAssistantSync()
        val useNano = !forceLocal &&
            geminiNanoEngine.checkStatus() == FeatureStatus.AVAILABLE

        if (!useNano) {
            localAssistantEngine.streamResponse(systemPrompt, history, userMessage).collect { emit(it) }
            return@flow
        }

        var failed = false
        geminiNanoEngine.streamResponse(systemPrompt, history, userMessage).collect { event ->
            if (event is AiStreamEvent.Error) {
                failed = true
            } else {
                emit(event)
            }
        }
        if (failed) {
            localAssistantEngine.streamResponse(systemPrompt, history, userMessage).collect { emit(it) }
        }
    }

    suspend fun warmup() {
        if (geminiNanoEngine.checkStatus() == FeatureStatus.AVAILABLE) {
            geminiNanoEngine.warmup()
        }
    }

    fun downloadGeminiNano() = geminiNanoEngine.downloadModel()

    fun downloadGeminiNanoWithProgress(): kotlinx.coroutines.flow.Flow<String> = kotlinx.coroutines.flow.flow {
        geminiNanoEngine.downloadModel().collect { status ->
            val message = when (status) {
                is com.google.mlkit.genai.common.DownloadStatus.DownloadStarted -> "Downloading Gemini Nano…"
                is com.google.mlkit.genai.common.DownloadStatus.DownloadProgress ->
                    "Downloaded ${status.totalBytesDownloaded / 1024 / 1024} MB"
                com.google.mlkit.genai.common.DownloadStatus.DownloadCompleted -> {
                    warmup()
                    "Gemini Nano ready!"
                }
                is com.google.mlkit.genai.common.DownloadStatus.DownloadFailed ->
                    "Download failed: ${status.e.message}"
            }
            emit(message)
        }
    }
}
