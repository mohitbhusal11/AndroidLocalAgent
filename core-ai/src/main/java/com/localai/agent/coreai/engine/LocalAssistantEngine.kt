package com.localai.agent.coreai.engine

import com.localai.agent.core.model.AiProvider
import com.localai.agent.core.model.AiStreamEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple offline replies when Gemini Nano is not used.
 * Written for people who are not comfortable with phones or apps.
 */
@Singleton
class LocalAssistantEngine @Inject constructor() : OnDeviceAiEngine {

    override val provider = AiProvider.LOCAL_FALLBACK

    override suspend fun isReady(): Boolean = true

    override suspend fun streamResponse(
        systemPrompt: String,
        history: List<Pair<String, String>>,
        userMessage: String
    ): Flow<AiStreamEvent> = flow {
        val response = generateResponse(userMessage)
        response.chunked(8).forEach { chunk ->
            emit(AiStreamEvent.Token(chunk))
            delay(15)
        }
        emit(AiStreamEvent.Complete(response))
    }

    private fun generateResponse(userMessage: String): String {
        val lower = userMessage.lowercase().trim()

        return when {
            lower.matches(Regex("^(hi|hello|hey|namaste|good morning|good evening).*")) ->
                "Hello! I am your phone helper.\n\n" +
                    "Just talk normally. For example:\n" +
                    "• Call Mom\n" +
                    "• Open WhatsApp\n" +
                    "• Set alarm after 1 hour\n" +
                    "• Show food places near me\n" +
                    "• What time is it"

            lower.contains("battery") || lower.contains("charge") ->
                "Say: How much battery is left?\nI will tell you."

            lower.contains("time") && !lower.contains("timer") ->
                "Say: What time is it?\nI will tell you the time."

            lower.contains("date") || lower.contains("today") ->
                "Say: What is today's date?\nI will tell you."

            lower.contains("call") || lower.contains("phone") ->
                "Say the person's name.\nExample: Call Rahul"

            lower.contains("photo") || lower.contains("camera") || lower.contains("selfie") ->
                "Say: Open camera\nOr: Take photo"

            lower.contains("message") || lower.contains("sms") || lower.contains("whatsapp") ->
                "Say: Open WhatsApp\nOr: Send message to Rahul"

            lower.contains("alarm") || lower.contains("wake") ->
                "Say: Set alarm after 1 hour\nOr: Wake me at 7 AM"

            lower.contains("map") || lower.contains("direction") || lower.contains("near") ||
                lower.contains("rasta") || lower.contains("jana") ->
                "For directions say:\n" +
                    "• Take me to New Market\n" +
                    "• I need direction to the hospital\n\n" +
                    "For nearby places say:\n" +
                    "• Show places near me\n" +
                    "• Food places near me"

            lower.contains("wifi") || lower.contains("internet") ->
                "Say: Open WiFi settings"

            lower.contains("help") || lower.contains("what can you") || lower.contains("kya kar") ->
                "You can say things like:\n\n" +
                    "Call someone — Call Mom\n" +
                    "Open app — Open YouTube\n" +
                    "Camera — Take photo\n" +
                    "Alarm — Wake me at 6 AM\n" +
                    "Map — Take me to the market\n" +
                    "Time — What time is it\n" +
                    "Battery — How much battery left\n\n" +
                    "Speak simply. I will try to understand."

            else ->
                "I did not fully understand.\n\n" +
                    "Try saying it in simple words, like:\n" +
                    "• Open WhatsApp\n" +
                    "• Call Rahul\n" +
                    "• Set alarm after 30 minutes\n" +
                    "• Show restaurants near me\n" +
                    "• What time is it\n\n" +
                    "You asked: \"$userMessage\""
        }
    }
}
