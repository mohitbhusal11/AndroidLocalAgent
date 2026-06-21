package com.localai.agent.coreagent.tool

/**
 * Understands everyday, non-technical speech for people who don't know app names or commands.
 */
internal object SimpleCommandParser {

    data class ToolCall(val tool: String, val args: Map<String, String>)

    fun parse(input: String): ToolCall? {
        val text = normalize(input)
        if (text.isBlank()) return null

        parseCamera(text)?.let { return it }
        parseCall(text, input)?.let { return it }
        parseAlarm(text, input)?.let { return it }
        parseTimer(text)?.let { return it }
        parseBattery(text)?.let { return it }
        parseTime(text)?.let { return it }
        parseDate(text)?.let { return it }
        parseSms(text, input)?.let { return it }
        parseEmail(text, input)?.let { return it }
        parseContact(text)?.let { return it }
        parseGallery(text)?.let { return it }
        parseSettings(text)?.let { return it }
        parseReminder(text, input)?.let { return it }
        parseNote(text, input)?.let { return it }
        parseTask(text, input)?.let { return it }
        parseCalendar(text, input)?.let { return it }
        parseOpenApp(text, input)?.let { return it }

        return null
    }

    fun normalize(input: String): String {
        return input
            .lowercase()
            .trim()
            .replace(Regex("""[^\w\s']"""), " ")
            .replace(Regex("""\s+"""), " ")
            .replace(Regex("""\b(please|plz|kindly|can you|could you|would you|help me|help me to|i want to|i need to|i want|i need|mujhe|mujhe chahiye|karo|krdo|kardo|chalao|chala do|chala do|jara|zara|thoda|abhi|please kar do)\b"""), " ")
            .replace(Regex("""\s+"""), " ")
            .trim()
    }

    private fun parseCamera(text: String): ToolCall? {
        val hits = listOf(
            "camera", "photo", "picture", "selfie", "snap", "click photo", "take photo",
            "take picture", "take a photo", "take a picture"
        )
        return if (hits.any { text.contains(it) }) ToolCall("OpenCameraTool", emptyMap()) else null
    }

    private fun parseContact(text: String): ToolCall? {
        if (text.contains("near me") || text.contains("nearby")) return null
        val patterns = listOf(
            Regex("""(?:find|search|lookup|look up) contact\s+(.+)"""),
            Regex("""contact\s+(.+)"""),
            Regex("""find\s+(.+)"""),
            Regex("""number of\s+(.+)"""),
            Regex("""(.+) ka number"""),
        )
        for (pattern in patterns) {
            pattern.find(text)?.let { match ->
                val name = cleanTarget(match.groupValues[1]) ?: return null
                if (name.isNotBlank() && !isNoise(name)) {
                    return ToolCall("SearchContactTool", mapOf("name" to name))
                }
            }
        }
        return null
    }

    private fun parseGallery(text: String): ToolCall? {
        val hits = listOf("gallery", "photos", "pictures", "my photos", "my pictures", "album")
        return if (hits.any { text.contains(it) }) ToolCall("OpenGalleryTool", emptyMap()) else null
    }

    private fun parseCall(text: String, original: String): ToolCall? {
        val patterns = listOf(
            Regex("""(?:call|phone|ring|dial|talk to|speak to|phone call)\s+(.+)"""),
            Regex("""(?:call|phone)\s+(.+)"""),
        )
        for (pattern in patterns) {
            pattern.find(text)?.let { match ->
                val name = cleanTarget(match.groupValues[1]) ?: return null
                if (name.isNotBlank() && !isNoise(name)) {
                    return ToolCall("CallContactTool", mapOf("name" to name))
                }
            }
        }
        return null
    }

    private fun parseAlarm(text: String, original: String): ToolCall? {
        val hits = listOf(
            "alarm", "wake me", "wake up", "get me up", "bajao", "ring at",
            "morning alarm", "subah uthao"
        )
        return if (hits.any { text.contains(it) }) {
            ToolCall("AlarmTool", mapOf("message" to original.trim()))
        } else null
    }

    private fun parseTimer(text: String): ToolCall? {
        if (!text.contains("timer") && !text.contains("countdown") && !text.contains("minute")) return null
        if (text.contains("alarm")) return null

        val mins = Regex("""(\d+)\s*(?:min|mins|minute|minutes|minut)""").find(text)?.groupValues?.get(1)?.toIntOrNull()
            ?: Regex("""(\d+)""").find(text)?.groupValues?.get(1)?.toIntOrNull()
            ?: 5
        return ToolCall("TimerTool", mapOf("seconds" to (mins * 60).toString()))
    }

    private fun parseBattery(text: String): ToolCall? {
        val hits = listOf("battery", "charge", "charging", "power left", "kitna charge")
        return if (hits.any { text.contains(it) }) ToolCall("GetBatteryTool", emptyMap()) else null
    }

    private fun parseTime(text: String): ToolCall? {
        if (text.contains("timer")) return null
        val hits = listOf("what time", "time kya", "kitna baje", "current time", "time now", "tell me time")
        val short = text == "time" || text.endsWith(" time") || text.startsWith("time ")
        return if (hits.any { text.contains(it) } || short) ToolCall("GetTimeTool", emptyMap()) else null
    }

    private fun parseDate(text: String): ToolCall? {
        val hits = listOf("what date", "today date", "today's date", "which day", "aaj kya date", "date kya")
        val short = text == "date" || text == "today"
        return if (hits.any { text.contains(it) } || short) ToolCall("GetDateTool", emptyMap()) else null
    }

    private fun parseSms(text: String, original: String): ToolCall? {
        val hits = listOf("sms", "text message", "send message", "message bhejo", "msg bhejo")
        if (!hits.any { text.contains(it) } && !(text.contains("message") && text.contains("send"))) return null
        val body = extractAfter(text, listOf("message", "sms", "text")) ?: original.trim()
        return ToolCall("SmsTool", mapOf("message" to body))
    }

    private fun parseEmail(text: String, original: String): ToolCall? {
        if (!text.contains("email") && !text.contains("mail")) return null
        return ToolCall("EmailTool", mapOf("subject" to "Message", "body" to original.trim()))
    }

    private fun parseSettings(text: String): ToolCall? {
        return when {
            text.contains("wifi") || text.contains("wi fi") || text.contains("internet setting") ||
                text.contains("internet connection") ->
                ToolCall("OpenWifiSettingsTool", emptyMap())
            text.contains("bluetooth") ->
                ToolCall("OpenBluetoothSettingsTool", emptyMap())
            text.contains("brightness") || text.contains("display") || text.contains("screen light") ->
                ToolCall("OpenDisplaySettingsTool", emptyMap())
            text.contains("setting") || text.contains("settings") ->
                ToolCall("OpenSettingsTool", emptyMap())
            else -> null
        }
    }

    private fun parseReminder(text: String, original: String): ToolCall? {
        val hits = listOf("remind", "reminder", "yaad dilao", "mat bhoolna", "don't forget")
        return if (hits.any { text.contains(it) }) {
            ToolCall("CreateReminderTool", mapOf("title" to original.trim()))
        } else null
    }

    private fun parseNote(text: String, original: String): ToolCall? {
        val hits = listOf("save note", "write down", "note likho", "remember this", "save this", "likh lo")
        if (text.contains("note") || hits.any { text.contains(it) }) {
            val content = extractAfter(text, listOf("note", "remember", "save", "write")) ?: original.trim()
            return ToolCall("CreateNoteTool", mapOf("title" to "Note", "content" to content))
        }
        if (text.startsWith("remember ") && !text.contains("contact")) {
            return ToolCall("CreateNoteTool", mapOf("title" to "Note", "content" to original.trim()))
        }
        return null
    }

    private fun parseTask(text: String, original: String): ToolCall? {
        val hits = listOf("task", "todo", "to do", "kaam", "list mein")
        if (!hits.any { text.contains(it) }) return null
        val title = extractAfter(text, listOf("task", "todo", "to do", "add")) ?: original.trim()
        return ToolCall("TaskTool", mapOf("title" to title))
    }

    private fun parseCalendar(text: String, original: String): ToolCall? {
        val hits = listOf("calendar", "meeting", "event", "appointment")
        if (!hits.any { text.contains(it) }) return null
        val title = extractAfter(text, listOf("calendar", "meeting", "event", "add")) ?: original.trim()
        return ToolCall("CalendarTool", mapOf("title" to title))
    }

    private fun parseOpenApp(text: String, original: String): ToolCall? {
        if (isMapsLike(text)) return null

        val prefixes = listOf(
            "open", "launch", "start", "show", "go to", "use", "play",
            "start using", "run", "switch to", "take me to the"
        )
        for (prefix in prefixes) {
            if (text.startsWith(prefix) || text.contains(" $prefix ")) {
                val app = extractAfter(text, listOf(prefix)) ?: continue
                val cleaned = cleanTarget(app) ?: continue
                if (cleaned.isNotBlank() && !isNoise(cleaned) && !isMapsLike(cleaned)) {
                    return ToolCall("OpenAppTool", mapOf("appName" to cleaned))
                }
            }
        }

        // Single app name: "whatsapp", "youtube", "phone"
        val single = text.trim()
        if (single.split(" ").size <= 3 && AppLauncher.normalizeQuery(single) != null) {
            if (!isMapsLike(single)) {
                return ToolCall("OpenAppTool", mapOf("appName" to single))
            }
        }

        // "i want whatsapp" / "need youtube"
        Regex("""(?:want|need|use)\s+(?:the\s+)?(.+)""").find(text)?.let { match ->
            val app = cleanTarget(match.groupValues[1]) ?: return null
            if (app.isNotBlank() && !isNoise(app) && !isMapsLike(app)) {
                return ToolCall("OpenAppTool", mapOf("appName" to app))
            }
        }

        if (text.contains("browser") || text.contains("internet") && text.contains("search")) {
            return ToolCall("OpenBrowserTool", mapOf("url" to "https://www.google.com"))
        }

        return null
    }

    private fun isMapsLike(text: String): Boolean {
        return listOf(
            "direction", "navigate", "navigation", "route", "nearby", "near me",
            "hangout", "hang out", "map", "maps", "location", "how to get",
            "way to", "take me to", "go to the market", "jana hai", "rasta"
        ).any { text.contains(it) }
    }

    private fun extractAfter(text: String, keywords: List<String>): String? {
        keywords.forEach { keyword ->
            val idx = text.indexOf(keyword)
            if (idx >= 0) {
                return text.substring(idx + keyword.length).trim().removePrefix(":").trim()
            }
        }
        return null
    }

    private fun cleanTarget(raw: String): String? {
        return raw
            .replace(Regex("""\b(the|a|an|app|application|for me|please)\b"""), " ")
            .trim()
            .replace(Regex("""\s+"""), " ")
            .takeIf { it.isNotBlank() }
    }

    private fun isNoise(text: String): Boolean {
        return text in setOf("it", "this", "that", "me", "up", "now", "here", "there")
    }
}
