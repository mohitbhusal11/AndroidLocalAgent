package com.localai.agent.coreai.research

import com.localai.agent.core.model.AiStreamEvent
import com.localai.agent.coreai.engine.OnDeviceAiRepository
import com.localai.agent.coredatabase.dao.MemoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResearchRepository @Inject constructor(
    private val memoryDao: MemoryDao,
    private val onDeviceAiRepository: OnDeviceAiRepository
) {
    suspend fun searchLocal(query: String): String {
        val note = memoryDao.getByKey(query.lowercase())
            ?: memoryDao.getByKey(query)
        return if (note != null) "**${note.key}**: ${note.value}"
        else "No local notes matching '$query'. Save content in Memory tab."
    }

    fun summarizeText(text: String): Flow<AiStreamEvent> =
        onDeviceAiRepository.streamResponse(
            systemPrompt = "Summarize offline content in 3 bullet points.",
            history = emptyList(),
            userMessage = text
        )

    fun compareProducts(productA: String, productB: String): Flow<AiStreamEvent> =
        onDeviceAiRepository.streamResponse(
            systemPrompt = "Compare products offline with pros and cons.",
            history = emptyList(),
            userMessage = "Compare $productA vs $productB"
        )
}
