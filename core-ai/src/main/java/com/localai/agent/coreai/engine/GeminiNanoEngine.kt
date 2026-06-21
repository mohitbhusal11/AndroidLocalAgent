package com.localai.agent.coreai.engine

import android.util.Log
import com.google.mlkit.genai.common.DownloadStatus
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.GenerativeModel
import com.google.mlkit.genai.prompt.TextPart
import com.google.mlkit.genai.prompt.generateContentRequest
import com.localai.agent.core.model.AiProvider
import com.localai.agent.core.model.AiStreamEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiNanoEngine @Inject constructor() : OnDeviceAiEngine {

    override val provider = AiProvider.GEMINI_NANO

    private val mutex = Mutex()
    private var model: GenerativeModel? = null
    private var warmedUp = false

    private fun getOrCreateModel(): GenerativeModel {
        return model ?: Generation.getClient().also { model = it }
    }

    override suspend fun isReady(): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            val status = getOrCreateModel().checkStatus()
            status == FeatureStatus.AVAILABLE || status == FeatureStatus.DOWNLOADING
        }.getOrDefault(false)
    }

    suspend fun checkStatus(): Int = withContext(Dispatchers.IO) {
        runCatching { getOrCreateModel().checkStatus() }.getOrDefault(FeatureStatus.UNAVAILABLE)
    }

    suspend fun getModelName(): String? = withContext(Dispatchers.IO) {
        runCatching { getOrCreateModel().getBaseModelName() }.getOrNull()
    }

    suspend fun warmup() = withContext(Dispatchers.IO) {
        mutex.withLock {
            if (warmedUp) return@withContext
            runCatching {
                getOrCreateModel().warmup()
                warmedUp = true
            }.onFailure { Log.w(TAG, "Gemini Nano warmup failed", it) }
        }
    }

    fun downloadModel(): Flow<DownloadStatus> = getOrCreateModel().download()

    override suspend fun streamResponse(
        systemPrompt: String,
        history: List<Pair<String, String>>,
        userMessage: String
    ): Flow<AiStreamEvent> = flow {
        val status = checkStatus()
        if (status != FeatureStatus.AVAILABLE && status != FeatureStatus.DOWNLOADING) {
            emit(AiStreamEvent.Error("Gemini Nano not available on this device"))
            return@flow
        }

        warmup()
        val prompt = buildPrompt(systemPrompt, history, userMessage)
        val generativeModel = getOrCreateModel()

        try {
            var fullText = ""
            generativeModel.generateContentStream(
                generateContentRequest(TextPart(prompt)) {
                    temperature = 0.4f
                    maxOutputTokens = 512
                }
            ).collect { chunk ->
                val token = chunk.candidates.firstOrNull()?.text.orEmpty()
                if (token.isNotEmpty()) {
                    fullText += token
                    emit(AiStreamEvent.Token(token))
                }
            }
            if (fullText.isBlank()) {
                emit(AiStreamEvent.Error("Empty response from Gemini Nano"))
            } else {
                emit(AiStreamEvent.Complete(fullText))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini Nano inference failed", e)
            emit(AiStreamEvent.Error(e.message ?: "On-device AI failed"))
        }
    }.flowOn(Dispatchers.IO)

    private fun buildPrompt(
        systemPrompt: String,
        history: List<Pair<String, String>>,
        userMessage: String
    ): String {
        val historyBlock = history.takeLast(6).joinToString("\n") { (role, content) ->
            val label = if (role == "user") "User" else "Assistant"
            "$label: $content"
        }
        return buildString {
            appendLine(systemPrompt)
            appendLine()
            if (historyBlock.isNotBlank()) {
                appendLine("Conversation so far:")
                appendLine(historyBlock)
                appendLine()
            }
            appendLine("User: $userMessage")
            appendLine("Assistant:")
        }
    }

    companion object {
        private const val TAG = "GeminiNanoEngine"
    }
}
