package com.localai.agent.coreagent.agent

import com.localai.agent.core.model.AgentResult
import com.localai.agent.coreagent.automation.AutomationEngine
import com.localai.agent.coreagent.tool.MapsIntentHelper
import com.localai.agent.coreagent.tool.SimpleCommandParser
import com.localai.agent.coreagent.tool.ToolRegistry
import com.localai.agent.corememory.routine.RoutineTracker
import com.localai.agent.coredatabase.repository.NotificationRepository
import com.localai.agent.coreai.research.ResearchRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

interface Agent {
    val name: String
    suspend fun process(input: String, context: AgentContext = AgentContext()): AgentResult
}

data class AgentContext(
    val conversationHistory: List<String> = emptyList(),
    val metadata: Map<String, String> = emptyMap()
)

@Singleton
class PhoneAgent @Inject constructor(
    private val toolRegistry: ToolRegistry
) : Agent {
    override val name = "PhoneAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult {
        MapsIntentHelper.toToolArgs(input)?.let { args ->
            return toolRegistry.get("GetLocationTool")?.execute(args)
                ?: AgentResult(false, "Map is not available right now.")
        }

        SimpleCommandParser.parse(input)?.let { call ->
            return toolRegistry.get(call.tool)?.execute(call.args)
                ?: AgentResult(false, "That action is not available.")
        }

        return AgentResult(
            false,
            "I did not understand. Try simple words like:\n" +
                "• Call Rahul\n" +
                "• Open WhatsApp\n" +
                "• Set alarm after 1 hour\n" +
                "• Show places near me\n" +
                "• What time is it"
        )
    }

    @Suppress("unused")
    private fun extractAfter(text: String, keywords: List<String>): String {
        keywords.forEach { keyword ->
            val idx = text.indexOf(keyword)
            if (idx >= 0) {
                return text.substring(idx + keyword.length).trim().removePrefix(":").trim()
            }
        }
        return text
    }
}

@Singleton
class MemoryAgent @Inject constructor(
    private val memoryRepository: com.localai.agent.corememory.repository.MemoryRepository,
    private val routineTracker: RoutineTracker
) : Agent {
    override val name = "MemoryAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult {
        memoryRepository.rememberCommand(input)
        routineTracker.recordAction(input)
        return AgentResult(true, "Stored in memory")
    }
}

@Singleton
class ResearchAgent @Inject constructor(
    private val researchRepository: ResearchRepository
) : Agent {
    override val name = "ResearchAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult {
        val lower = input.lowercase()
        return when {
            lower.contains("compare") -> {
                val parts = input.split(" and ", " vs ", ignoreCase = true)
                if (parts.size >= 2) {
                    var summary = ""
                    researchRepository.compareProducts(parts[0], parts[1]).collect { event ->
                        if (event is com.localai.agent.core.model.AiStreamEvent.Complete) summary = event.fullText
                    }
                    AgentResult(true, summary.ifBlank { "Comparison started — check Research screen" })
                } else AgentResult(false, "Say: compare X and Y")
            }
            lower.contains("summarize") -> {
                val text = extractAfter(lower, listOf("summarize"))
                var summary = ""
                researchRepository.summarizeText(text).collect { event ->
                    if (event is com.localai.agent.core.model.AiStreamEvent.Complete) summary = event.fullText
                }
                AgentResult(true, summary.ifBlank { "Summarizing…" })
            }
            lower.contains("report") -> {
                val topic = extractAfter(lower, listOf("report", "research"))
                AgentResult(true, "Report generated for: $topic. Open Research tab.")
            }
            else -> {
                val result = researchRepository.searchLocal(input)
                AgentResult(true, result)
            }
        }
    }

    private fun extractAfter(text: String, keywords: List<String>): String {
        keywords.forEach { kw ->
            val idx = text.lowercase().indexOf(kw)
            if (idx >= 0) return text.substring(idx + kw.length).trim()
        }
        return text
    }
}

@Singleton
class NotificationAgent @Inject constructor(
    private val notificationRepository: NotificationRepository
) : Agent {
    override val name = "NotificationAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult {
        val lower = input.lowercase()
        val unread = notificationRepository.unreadCount()
        return when {
            lower.contains("summary") || lower.contains("summarize") -> {
                val recent = notificationRepository.observeRecent(5).first()
                val summary = recent.joinToString("\n") { n ->
                    "• [P${n.priority}] ${n.title}: ${n.text.take(60)}"
                }
                AgentResult(true, summary.ifBlank { "No notifications captured yet. Enable notification access." })
            }
            lower.contains("reply") -> {
                val recent = notificationRepository.observeRecent(1).first().firstOrNull()
                if (recent == null) return AgentResult(false, "No notifications to reply to")
                val reply = notificationRepository.smartReply(recent)
                AgentResult(true, "Suggested reply: \"$reply\"", mapOf("reply" to reply))
            }
            lower.contains("priority") || lower.contains("important") -> {
                val recent = notificationRepository.observeUnread().first()
                val high = recent.filter { it.priority >= 2 }
                AgentResult(
                    true,
                    if (high.isEmpty()) "No high-priority notifications ($unread unread total)"
                    else high.joinToString("\n") { "🔴 ${it.title}: ${it.text.take(40)}" }
                )
            }
            else -> AgentResult(true, "$unread unread notifications. Try: summarize notifications")
        }
    }
}

@Singleton
class AutomationAgent @Inject constructor(
    private val automationEngine: AutomationEngine
) : Agent {
    override val name = "AutomationAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult =
        automationEngine.execute(input)
}

@Singleton
class ProductivityAgent @Inject constructor(
    private val toolRegistry: ToolRegistry
) : Agent {
    override val name = "ProductivityAgent"

    override suspend fun process(input: String, context: AgentContext): AgentResult {
        val lower = input.lowercase()
        val toolCall = when {
            lower.contains("calendar") || lower.contains("meeting") || lower.contains("event") ||
                lower.contains("appointment") ->
                "CalendarTool" to mapOf("title" to input)
            lower.contains("task") || lower.contains("todo") || lower.contains("kaam") ->
                "TaskTool" to mapOf("title" to input.replace("task", "", ignoreCase = true).trim())
            lower.contains("alarm") || lower.contains("wake me") || lower.contains("wake up") ||
                lower.contains("bajao") || lower.contains("ring at") ->
                "AlarmTool" to mapOf("message" to input)
            lower.contains("timer") -> {
                val mins = Regex("(\\d+)").find(lower)?.value?.toIntOrNull() ?: 5
                "TimerTool" to mapOf("seconds" to (mins * 60).toString())
            }
            lower.contains("stopwatch") -> "StopwatchTool" to emptyMap()
            lower.contains("reminder") || lower.contains("remind") ->
                "CreateReminderTool" to mapOf("title" to input)
            lower.contains("note") ->
                "CreateNoteTool" to mapOf("title" to "Note", "content" to input)
            else -> null
        }
        return toolCall?.let { (tool, args) ->
            toolRegistry.get(tool)?.execute(args) ?: AgentResult(false, "Tool not found")
        } ?: AgentResult(false, "Try: create task, set alarm, timer, calendar event")
    }
}

@Singleton
class PlannerAgent @Inject constructor(
    private val phoneAgent: PhoneAgent,
    private val memoryAgent: MemoryAgent,
    private val researchAgent: ResearchAgent,
    private val notificationAgent: NotificationAgent,
    private val automationAgent: AutomationAgent,
    private val productivityAgent: ProductivityAgent,
    private val toolRegistry: ToolRegistry,
    private val onDeviceAiRepository: com.localai.agent.coreai.engine.OnDeviceAiRepository,
    private val routineTracker: RoutineTracker
) {
    suspend fun planAndExecute(userInput: String): PlannerResult {
        routineTracker.recordAction(userInput)
        val steps = parseMultiStep(userInput)
        val results = mutableListOf<StepResult>()

        for (step in steps) {
            val result = executeStep(step)
            results.add(result)
        }

        val allSuccess = results.all { it.result.success }
        val summary = results.joinToString("\n") { "• ${it.step}: ${it.result.message}" }

        return PlannerResult(
            steps = results,
            summary = if (results.size > 1) "Completed ${results.size} steps:\n$summary" else summary,
            success = allSuccess
        )
    }

    suspend fun chat(
        userInput: String,
        history: List<Pair<String, String>>
    ): kotlinx.coroutines.flow.Flow<com.localai.agent.core.model.AiStreamEvent> {
        routineTracker.recordAction(userInput)

        if (isMultiStep(userInput)) {
            val result = planAndExecute(userInput)
            return kotlinx.coroutines.flow.flow {
                emit(com.localai.agent.core.model.AiStreamEvent.Complete(result.summary))
            }
        }

        val specialized = routeToAgent(userInput)
        if (specialized != null && specialized.success) {
            return kotlinx.coroutines.flow.flow {
                emit(com.localai.agent.core.model.AiStreamEvent.Complete(specialized.message))
            }
        }

        val phoneResult = phoneAgent.process(userInput)
        if (phoneResult.success) {
            return kotlinx.coroutines.flow.flow {
                emit(com.localai.agent.core.model.AiStreamEvent.Complete(phoneResult.message))
            }
        }

        return onDeviceAiRepository.streamResponse(buildSystemPrompt(), history, userInput)
    }

    private suspend fun routeToAgent(input: String): AgentResult? {
        val lower = input.lowercase()
        return when {
            lower.contains("notification") -> notificationAgent.process(input)
            lower.contains("research") || lower.contains("summarize") || lower.contains("compare") ->
                researchAgent.process(input)
            lower.contains("scroll") || lower.contains("click") || lower.contains("automate") ->
                automationAgent.process(input)
            lower.contains("task") || lower.contains("calendar") || lower.contains("alarm") ||
                lower.contains("wake") || lower.contains("timer") || lower.contains("reminder") ||
                lower.contains("kaam") ->
                productivityAgent.process(input)
            else -> null
        }?.takeIf { it.success }
    }

    private fun isMultiStep(input: String): Boolean {
        val markers = listOf(" and ", " then ", ", then ")
        return markers.any { input.contains(it, ignoreCase = true) }
    }

    private suspend fun executeStep(step: String): StepResult {
        val lower = step.lowercase()
        val agent: Agent = when {
            lower.contains("notification") -> notificationAgent
            lower.contains("research") || lower.contains("summarize") || lower.contains("compare") -> researchAgent
            lower.contains("scroll") || lower.contains("click") || lower.contains("automate") -> automationAgent
            lower.contains("calendar") || lower.contains("task") || lower.contains("alarm") ||
                lower.contains("timer") || lower.contains("reminder") -> productivityAgent
            lower.contains("remember") || lower.contains("save") -> memoryAgent
            else -> phoneAgent
        }
        return StepResult(step, agent.process(step))
    }

    private fun parseMultiStep(input: String): List<String> {
        val separators = listOf(" and ", ", then ", " then ", ". ")
        var parts = listOf(input)
        separators.forEach { sep ->
            parts = parts.flatMap { it.split(sep, ignoreCase = true) }
        }
        return parts.map { it.trim() }.filter { it.isNotBlank() }
    }

    private suspend fun buildSystemPrompt(): String {
        val provider = onDeviceAiRepository.currentProvider()
        return """
            You are a simple phone helper for people who are not tech-savvy.
            AI engine: $provider
            
            Rules:
            - Use very easy words. Short sentences.
            - No technical words like API, database, offline mode, markdown tables.
            - If user wants phone action, tell them simple examples they can say.
            - Be warm and patient, like helping a family member.
            
            Phone tools:
            ${toolRegistry.descriptions()}
        """.trimIndent()
    }
}

data class StepResult(val step: String, val result: AgentResult)
data class PlannerResult(
    val steps: List<StepResult>,
    val summary: String,
    val success: Boolean
)
