package com.localai.agent.coreagent.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.localai.agent.core.model.AgentResult

internal object MapsIntentHelper {

    enum class Mode { SEARCH, DIRECTIONS }

    data class MapsRequest(val query: String, val mode: Mode)

    fun parse(input: String): MapsRequest? {
        val lower = SimpleCommandParser.normalize(input)
        if (lower.isBlank()) return null

        parseDirections(lower)?.let { return it }
        parseNearbySearch(lower)?.let { return it }
        parseGoToPlace(lower)?.let { return it }
        parseWhereIs(lower)?.let { return it }
        parseGeneralMaps(lower)?.let { return it }

        return null
    }

    fun toToolArgs(input: String): Map<String, String>? {
        val request = parse(input) ?: return null
        return mapOf(
            "query" to request.query,
            "mode" to request.mode.name.lowercase()
        )
    }

    fun launch(context: Context, request: MapsRequest): AgentResult {
        val uri = when (request.mode) {
            Mode.DIRECTIONS -> "google.navigation:q=${Uri.encode(request.query)}"
            Mode.SEARCH -> "geo:0,0?q=${Uri.encode(request.query)}"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(intent)
            val message = when (request.mode) {
                Mode.DIRECTIONS -> "Opening map. Follow the route to ${request.query}."
                Mode.SEARCH -> "Opening map to show ${request.query}."
            }
            AgentResult(true, message)
        } catch (_: SecurityException) {
            AgentResult(false, "Cannot open map. Check app permissions.")
        } catch (_: Exception) {
            AgentResult(false, "Map app not found. Install Google Maps first.")
        }
    }

    private fun parseDirections(lower: String): MapsRequest? {
        val patterns = listOf(
            Regex("""need directions? to go (?:to )?(?:the )?(.+)"""),
            Regex("""need directions? (?:to )?(?:the )?(.+)"""),
            Regex("""(?:get )?directions? to (?:the )?(.+)"""),
            Regex("""(?:navigate|navigation) to (?:the )?(.+)"""),
            Regex("""how to get to (?:the )?(.+)"""),
            Regex("""how do i reach (?:the )?(.+)"""),
            Regex("""(?:drive|walk) to (?:the )?(.+)"""),
            Regex("""route to (?:the )?(.+)"""),
            Regex("""way to (?:the )?(.+)"""),
            Regex("""rasta (?:to|for|dikhao|batao)?\s*(?:the )?(.+)"""),
            Regex("""(?:take me|le chalo|leke chalo) to (?:the )?(.+)"""),
            Regex("""(?:i want to go|want to go|have to go|need to go) to (?:the )?(.+)"""),
        )
        for (pattern in patterns) {
            pattern.find(lower)?.let { match ->
                cleanPlace(match.groupValues[1])?.let { place ->
                    if (!isGenericNoise(place) && !AppLauncher.isKnownApp(place)) {
                        return MapsRequest(place, Mode.DIRECTIONS)
                    }
                }
            }
        }
        return null
    }

    private fun parseGoToPlace(lower: String): MapsRequest? {
        if (!listOf("market", "hospital", "station", "airport", "mall", "shop", "store", "home", "office")
                .any { lower.contains(it) }
        ) return null

        if (listOf("direction", "route", "rasta", "jana", "reach", "get to", "go to", "take me").any { lower.contains(it) }) {
            val place = extractPlaceFromGo(lower) ?: return null
            return MapsRequest(place, Mode.DIRECTIONS)
        }
        return null
    }

    private fun parseWhereIs(lower: String): MapsRequest? {
        val patterns = listOf(
            Regex("""where is (?:the )?(.+)"""),
            Regex("""where's (?:the )?(.+)"""),
            Regex("""find (?:the )?(.+) near me"""),
            Regex("""(.+) kahan hai"""),
        )
        for (pattern in patterns) {
            pattern.find(lower)?.let { match ->
                cleanPlace(match.groupValues[1])?.let { place ->
                    return MapsRequest(place, Mode.SEARCH)
                }
            }
        }
        return null
    }

    private fun parseNearbySearch(lower: String): MapsRequest? {
        val isNearby = listOf(
            "nearby", "near me", "around me", "close by", "hangout", "hang out",
            "pass me", "paas me", "aas paas", "ghumne", "fursat", "time pass"
        ).any { lower.contains(it) }

        val isPlacesQuery = lower.contains("places") &&
            (lower.contains("near") || lower.contains("hangout") || lower.contains("hang out"))

        if (!isNearby && !isPlacesQuery) return null

        val query = when {
            lower.contains("hangout") || lower.contains("hang out") || lower.contains("ghumne") ->
                "places to hangout near me"
            lower.contains("restaurant") || lower.contains("food") || lower.contains("eat") ||
                lower.contains("khana") || lower.contains("hotel") ->
                "restaurants near me"
            lower.contains("coffee") || lower.contains("cafe") || lower.contains("chai") ->
                "cafes near me"
            lower.contains("park") ->
                "parks near me"
            lower.contains("mall") || lower.contains("shopping") ->
                "shopping malls near me"
            lower.contains("doctor") || lower.contains("hospital") ->
                "hospitals near me"
            lower.contains("atm") || lower.contains("bank") ->
                "atm near me"
            else -> "places near me"
        }
        return MapsRequest(query, Mode.SEARCH)
    }

    private fun parseGeneralMaps(lower: String): MapsRequest? {
        if (!listOf("location", "maps", "map", "navigate", "navigation", "rasta", "direction")
                .any { lower.contains(it) }
        ) return null

        val extracted = extractAfterKeywords(
            lower,
            listOf("maps for", "map for", "location of", "show me", "find", "open maps", "open map")
        ) ?: extractAfterKeywords(lower, listOf("maps", "map", "location"))

        val query = cleanPlace(extracted ?: "") ?: "current location"
        return MapsRequest(query, Mode.SEARCH)
    }

    private fun extractPlaceFromGo(lower: String): String? {
        val patterns = listOf(
            Regex("""go to (?:the )?(.+)"""),
            Regex("""reach (?:the )?(.+)"""),
            Regex("""(?:the )?(.+?) jana hai"""),
            Regex("""direction to (?:the )?(.+)"""),
        )
        for (pattern in patterns) {
            pattern.find(lower)?.let { match ->
                cleanPlace(match.groupValues[1])?.let { return it }
            }
        }
        return null
    }

    private fun extractAfterKeywords(text: String, keywords: List<String>): String? {
        keywords.forEach { keyword ->
            val idx = text.indexOf(keyword)
            if (idx >= 0) {
                return text.substring(idx + keyword.length).trim().removePrefix(":").trim()
            }
        }
        return null
    }

    private fun cleanPlace(raw: String): String? {
        return raw
            .replace(Regex("""\b(please|help|i need|i want|the|a|an)\b"""), " ")
            .trim()
            .replace(Regex("""\s+"""), " ")
            .takeIf { it.isNotBlank() }
    }

    private fun isGenericNoise(place: String): Boolean {
        return place in setOf("there", "here", "it", "that", "this", "me")
    }
}
