package com.localai.agent.coreagent.tool

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.localai.agent.core.model.AgentResult

internal object AppLauncher {

    private val knownPackages = mapOf(
        "whatsapp" to "com.whatsapp",
        "wa" to "com.whatsapp",
        "youtube" to "com.google.android.youtube",
        "yt" to "com.google.android.youtube",
        "chrome" to "com.android.chrome",
        "instagram" to "com.instagram.android",
        "insta" to "com.instagram.android",
        "tiktok" to "com.zhiliaoapp.musically",
        "facebook" to "com.facebook.katana",
        "fb" to "com.facebook.katana",
        "twitter" to "com.twitter.android",
        "x" to "com.twitter.android",
        "gmail" to "com.google.android.gm",
        "maps" to "com.google.android.apps.maps",
        "google maps" to "com.google.android.apps.maps",
        "photos" to "com.google.android.apps.photos",
        "gallery" to "com.google.android.apps.photos",
        "messages" to "com.google.android.apps.messaging",
        "sms" to "com.google.android.apps.messaging",
        "phone" to "com.google.android.dialer",
        "dialer" to "com.google.android.dialer",
        "contacts" to "com.google.android.contacts",
        "clock" to "com.google.android.deskclock",
        "calendar" to "com.google.android.calendar",
        "settings" to "com.android.settings",
        "play store" to "com.android.vending",
        "spotify" to "com.spotify.music",
        "telegram" to "org.telegram.messenger",
        "discord" to "com.discord",
        "reddit" to "com.reddit.frontpage",
        "snapchat" to "com.snapchat.android",
        "linkedin" to "com.linkedin.android",
        "netflix" to "com.netflix.mediaclient",
        "amazon" to "com.amazon.mShop.android.shopping",
        "flipkart" to "com.flipkart.android",
        "paytm" to "net.one97.paytm",
        "phonepe" to "com.phonepe.app",
        "gpay" to "com.google.android.apps.nbu.paisa.user",
        "google pay" to "com.google.android.apps.nbu.paisa.user",
        "slack" to "com.Slack",
        "zoom" to "us.zoom.videomeetings",
        "teams" to "com.microsoft.teams",
        "outlook" to "com.microsoft.office.outlook",
        "drive" to "com.google.android.apps.docs",
        "google drive" to "com.google.android.apps.docs",
        "keep" to "com.google.android.keep",
        "notes" to "com.google.android.keep",
        "calculator" to "com.google.android.calculator",
        "files" to "com.google.android.apps.nbu.files",
        "camera" to "com.google.android.GoogleCamera",
    )

    fun isKnownApp(name: String): Boolean {
        val query = normalizeQuery(name) ?: return false
        return knownPackages.containsKey(query)
    }

    fun normalizeQuery(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        return raw
            .lowercase()
            .replace(Regex("""^(please|can you|could you)\s+"""), "")
            .replace(Regex("""\b(the|my|app|application)\b"""), " ")
            .replace(Regex("""[^\w\s]"""), " ")
            .trim()
            .replace(Regex("""\s+"""), " ")
            .takeIf { it.isNotBlank() }
    }

    fun open(context: Context, appName: String?, packageName: String?): AgentResult {
        val pm = context.packageManager
        val query = normalizeQuery(appName)

        if (!packageName.isNullOrBlank()) {
            launchPackage(context, pm, packageName)?.let { return it }
        }

        if (query != null) {
            knownPackages[query]?.let { pkg ->
                launchPackage(context, pm, pkg)?.let { return it }
            }

            findLauncherMatch(pm, query)?.let { (label, pkg) ->
                launchPackage(context, pm, pkg, label)?.let { return it }
            }
        }

        return AgentResult(false, "I could not find that app. Try saying the app name clearly, like WhatsApp or YouTube.")
    }

    private fun findLauncherMatch(pm: PackageManager, query: String): Pair<String, String>? {
        val launcherIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val activities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(launcherIntent, PackageManager.ResolveInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(launcherIntent, 0)
        }

        val candidates = activities
            .mapNotNull { info ->
                val label = info.loadLabel(pm)?.toString()?.trim().orEmpty()
                val pkg = info.activityInfo?.packageName?.trim().orEmpty()
                if (label.isBlank() || pkg.isBlank()) null else label to pkg
            }
            .distinctBy { it.second }

        val exact = candidates.firstOrNull { (label, pkg) ->
            label.equals(query, ignoreCase = true) ||
                pkg.equals(query, ignoreCase = true)
        }
        if (exact != null) return exact

        val contains = candidates.firstOrNull { (label, pkg) ->
            label.contains(query, ignoreCase = true) ||
                query.contains(label, ignoreCase = true) ||
                pkg.contains(query.replace(" ", ""), ignoreCase = true)
        }
        if (contains != null) return contains

        val queryWords = query.split(" ").filter { it.length > 2 }
        return candidates.firstOrNull { (label, _) ->
            val labelLower = label.lowercase()
            queryWords.all { word -> labelLower.contains(word) }
        }
    }

    private fun launchPackage(
        context: Context,
        pm: PackageManager,
        packageName: String,
        displayName: String? = null
    ): AgentResult? {
        val intent = pm.getLaunchIntentForPackage(packageName) ?: return null
        return try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            val label = displayName ?: runCatching {
                val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pm.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
                } else {
                    @Suppress("DEPRECATION")
                    pm.getApplicationInfo(packageName, 0)
                }
                pm.getApplicationLabel(info).toString()
            }.getOrDefault(packageName)
            AgentResult(true, "Done. Opening $label.")
        } catch (_: SecurityException) {
            AgentResult(false, "Cannot open $packageName — permission denied.")
        } catch (e: Exception) {
            AgentResult(false, e.message ?: "Could not open app")
        }
    }
}
