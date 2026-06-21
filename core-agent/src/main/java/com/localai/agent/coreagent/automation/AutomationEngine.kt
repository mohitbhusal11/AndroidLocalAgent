package com.localai.agent.coreagent.automation

import com.localai.agent.core.bridge.AccessibilityController
import com.localai.agent.core.model.AgentResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutomationEngine @Inject constructor(
    private val accessibilityController: AccessibilityController
) {
    private val supportedApps = mapOf(
        "whatsapp" to "com.whatsapp",
        "instagram" to "com.instagram.android",
        "youtube" to "com.google.android.youtube",
        "chrome" to "com.android.chrome",
        "settings" to "com.android.settings"
    )

    suspend fun execute(command: String): AgentResult {
        if (!accessibilityController.isConnected.value) {
            return AgentResult(false, "Enable Accessibility Service in Settings")
        }
        val lower = command.lowercase()
        val pkg = accessibilityController.getForegroundPackage().orEmpty()

        return when {
            lower.contains("scroll down") || lower.contains("scroll") ->
                if (accessibilityController.scrollForward()) AgentResult(true, "Scrolled down")
                else AgentResult(false, "Could not scroll")

            lower.contains("scroll up") ->
                if (accessibilityController.scrollBackward()) AgentResult(true, "Scrolled up")
                else AgentResult(false, "Could not scroll up")

            lower.contains("back") ->
                if (accessibilityController.performBack()) AgentResult(true, "Pressed back")
                else AgentResult(false, "Back failed")

            lower.contains("home") ->
                if (accessibilityController.performHome()) AgentResult(true, "Pressed home")
                else AgentResult(false, "Home failed")

            lower.contains("click") || lower.contains("tap") || lower.contains("press") -> {
                val target = extractTarget(lower, listOf("click", "tap", "press"))
                if (target.isBlank()) return AgentResult(false, "Specify what to click")
                if (accessibilityController.clickByText(target)) {
                    AgentResult(true, "Clicked '$target'")
                } else {
                    AgentResult(false, "Could not find '$target' on screen")
                }
            }

            lower.contains("read screen") || lower.contains("what's on screen") -> {
                val nodes = accessibilityController.findNodesWithText(" ")
                    .ifEmpty { accessibilityController.findNodesWithText("a") }
                AgentResult(true, "On screen: ${nodes.take(12).joinToString(" | ")}")
            }

            lower.contains("open") && supportedApps.keys.any { lower.contains(it) } -> {
                val app = supportedApps.entries.first { lower.contains(it.key) }
                AgentResult(true, "Active app: $pkg. Use OpenAppTool for ${app.key}")
            }

            else -> AgentResult(
                true,
                "Accessibility active. Foreground: $pkg. Try: scroll, click Send, read screen, back, home"
            )
        }
    }

    private fun extractTarget(text: String, keywords: List<String>): String {
        keywords.forEach { kw ->
            val idx = text.indexOf(kw)
            if (idx >= 0) return text.substring(idx + kw.length).trim()
        }
        return text
    }
}
