package com.localai.agent.core.model

enum class MessageRole {
    USER,
    ASSISTANT,
    SYSTEM,
    TOOL
}

enum class MemoryType {
    PREFERENCE,
    CONTACT,
    FAVORITE_APP,
    NOTE,
    COMMAND
}

enum class AiProvider {
    GEMINI_NANO,
    LOCAL_FALLBACK,
    GEMMA_OFFLINE,
    QWEN_OFFLINE,
    MEDIAPIPE_OFFLINE
}

data class AgentResult(
    val success: Boolean,
    val message: String,
    val data: Map<String, String> = emptyMap()
)

sealed class AiStreamEvent {
    data class Token(val text: String) : AiStreamEvent()
    data class ToolCall(val toolName: String, val args: Map<String, String>) : AiStreamEvent()
    data class Complete(val fullText: String) : AiStreamEvent()
    data class Error(val message: String) : AiStreamEvent()
}
