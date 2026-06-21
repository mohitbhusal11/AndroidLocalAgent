package com.localai.agent.coreui.navigation

object AgentRoutes {
    const val CONVERSATIONS = "conversations"
    const val CHAT = "chat/{conversationId}"
    const val VOICE = "voice"
    const val TOOLS = "tools"
    const val MEMORY = "memory"
    const val SETTINGS = "settings"
    const val NOTIFICATIONS = "notifications"
    const val FOCUS = "focus"
    const val PRODUCTIVITY = "productivity"
    const val RESEARCH = "research"
    const val AUTOMATION = "automation"
    const val SUGGESTIONS = "suggestions"

    fun chat(conversationId: Long) = "chat/$conversationId"
}
