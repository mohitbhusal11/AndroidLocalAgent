package com.localai.agent.coreai.engine

import com.localai.agent.core.model.AiProvider
import com.localai.agent.core.model.AiStreamEvent
import kotlinx.coroutines.flow.Flow

data class OnDeviceAiStatus(
    val provider: AiProvider,
    val featureStatus: String,
    val modelName: String?,
    val isReady: Boolean,
    val message: String
)

interface OnDeviceAiEngine {
    val provider: AiProvider
    suspend fun isReady(): Boolean
    suspend fun streamResponse(
        systemPrompt: String,
        history: List<Pair<String, String>>,
        userMessage: String
    ): Flow<AiStreamEvent>
}
