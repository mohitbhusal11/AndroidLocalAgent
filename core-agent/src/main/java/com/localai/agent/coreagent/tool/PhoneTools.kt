package com.localai.agent.coreagent.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import com.localai.agent.core.model.AgentResult
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

interface AgentTool {
    val name: String
    val description: String
    suspend fun execute(args: Map<String, String>): AgentResult
}

@Singleton
class ToolRegistry @Inject constructor(
    private val tools: Set<@JvmSuppressWildcards AgentTool>
) {
    fun get(name: String): AgentTool? = tools.find { it.name == name }
    fun all(): List<AgentTool> = tools.toList()
    fun descriptions(): String = tools.joinToString("\n") { "- ${it.name}: ${it.description}" }
}

@Singleton
class OpenAppTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "OpenAppTool"
    override val description = "Opens an app by name or package. Args: appName or packageName"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        return AppLauncher.open(
            context = context,
            appName = args["appName"] ?: args["app"],
            packageName = args["packageName"]
        )
    }
}

@Singleton
class OpenCameraTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "OpenCameraTool"
    override val description = "Opens the camera app"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Camera opened")
    }
}

@Singleton
class OpenGalleryTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "OpenGalleryTool"
    override val description = "Opens the gallery/photos app"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            type = "image/*"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Gallery opened")
    }
}

@Singleton
class OpenBrowserTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "OpenBrowserTool"
    override val description = "Opens a URL in browser. Args: url"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val url = args["url"] ?: "https://www.google.com"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Opened $url")
    }
}

@Singleton
class SearchContactTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "SearchContactTool"
    override val description = "Search contacts by name. Args: name"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val name = args["name"] ?: return AgentResult(false, "Name required")
        val uri = Uri.withAppendedPath(
            ContactsContract.Contacts.CONTENT_FILTER_URI,
            Uri.encode(name)
        )
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
            null, null, null
        )
        val results = buildList {
            cursor?.use {
                while (it.moveToNext()) {
                    add(it.getString(1))
                }
            }
        }
        return if (results.isEmpty()) {
            AgentResult(false, "No contacts found for $name")
        } else {
            AgentResult(true, "Found: ${results.joinToString(", ")}", mapOf("contacts" to results.joinToString("|")))
        }
    }
}

@Singleton
class CallContactTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "CallContactTool"
    override val description = "Call a contact by name. Args: name"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val name = args["name"] ?: return AgentResult(false, "Name required")
        val uri = Uri.withAppendedPath(
            ContactsContract.Contacts.CONTENT_FILTER_URI,
            Uri.encode(name)
        )
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var phoneUri: Uri? = null
        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val hasPhone = it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhone > 0) {
                    val phoneCursor = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(id),
                        null
                    )
                    phoneCursor?.use { pc ->
                        if (pc.moveToFirst()) {
                            val number = pc.getString(0)
                            phoneUri = Uri.parse("tel:$number")
                        }
                    }
                }
            }
        }
        return if (phoneUri != null) {
            val intent = Intent(Intent.ACTION_DIAL, phoneUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            AgentResult(true, "Dialing $name")
        } else {
            AgentResult(false, "Could not find phone number for $name")
        }
    }
}

@Singleton
class CreateReminderTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "CreateReminderTool"
    override val description = "Create a reminder. Args: title, seconds (optional delay)"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val title = args["title"] ?: args["message"] ?: "Reminder"
        val seconds = args["seconds"]?.toLongOrNull() ?: 0L
        val (hour, minutes) = resolveAlarmTime(
            message = title,
            hourArg = null,
            minuteArg = null
        )
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, title)
            if (seconds > 0) {
                val cal = java.util.Calendar.getInstance().apply { add(java.util.Calendar.SECOND, seconds.toInt()) }
                putExtra(AlarmClock.EXTRA_HOUR, cal.get(java.util.Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, cal.get(java.util.Calendar.MINUTE))
            } else {
                putExtra(AlarmClock.EXTRA_HOUR, hour)
                putExtra(AlarmClock.EXTRA_MINUTES, minutes)
            }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return context.launchClockIntent(intent, "Reminder created: $title")
    }
}

@Singleton
class CreateNoteTool @Inject constructor(
    private val memoryRepository: com.localai.agent.corememory.repository.MemoryRepository
) : AgentTool {
    override val name = "CreateNoteTool"
    override val description = "Save a note. Args: title, content"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val title = args["title"] ?: "Note ${System.currentTimeMillis()}"
        val content = args["content"] ?: args["text"] ?: return AgentResult(false, "Content required")
        memoryRepository.rememberNote(title, content)
        return AgentResult(true, "Note saved: $title")
    }
}

@Singleton
class GetBatteryTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "GetBatteryTool"
    override val description = "Get current battery level"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
        val level = bm.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return AgentResult(true, "Battery: $level%", mapOf("level" to level.toString()))
    }
}

@Singleton
class GetTimeTool @Inject constructor() : AgentTool {
    override val name = "GetTimeTool"
    override val description = "Get current time"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        return AgentResult(true, "Current time: $time", mapOf("time" to time))
    }
}

@Singleton
class GetDateTool @Inject constructor() : AgentTool {
    override val name = "GetDateTool"
    override val description = "Get current date"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val date = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(Date())
        return AgentResult(true, "Today is $date", mapOf("date" to date))
    }
}

@Singleton
class GetLocationTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "GetLocationTool"
    override val description = "Open maps search or turn-by-turn directions. Args: query, mode=search|directions"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val query = args["query"] ?: "current location"
        val mode = when (args["mode"]?.lowercase()) {
            "directions" -> MapsIntentHelper.Mode.DIRECTIONS
            else -> MapsIntentHelper.Mode.SEARCH
        }
        return MapsIntentHelper.launch(context, MapsIntentHelper.MapsRequest(query, mode))
    }
}

@Singleton
class ShareTextTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "ShareTextTool"
    override val description = "Share text. Args: text"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val text = args["text"] ?: return AgentResult(false, "Text required")
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(Intent.createChooser(intent, "Share via").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
        return AgentResult(true, "Share sheet opened")
    }
}

abstract class SettingsTool(
    private val context: Context,
    override val name: String,
    override val description: String,
    private val action: String
) : AgentTool {
    override suspend fun execute(args: Map<String, String>): AgentResult {
        val intent = Intent(action).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        context.startActivity(intent)
        return AgentResult(true, "Opened $name")
    }
}

@Singleton
class OpenSettingsTool @Inject constructor(
    @ApplicationContext context: Context
) : SettingsTool(context, "OpenSettingsTool", "Open system settings", Settings.ACTION_SETTINGS)

@Singleton
class OpenWifiSettingsTool @Inject constructor(
    @ApplicationContext context: Context
) : SettingsTool(context, "OpenWifiSettingsTool", "Open WiFi settings", Settings.ACTION_WIFI_SETTINGS)

@Singleton
class OpenBluetoothSettingsTool @Inject constructor(
    @ApplicationContext context: Context
) : SettingsTool(context, "OpenBluetoothSettingsTool", "Open Bluetooth settings", Settings.ACTION_BLUETOOTH_SETTINGS)

@Singleton
class OpenDisplaySettingsTool @Inject constructor(
    @ApplicationContext context: Context
) : SettingsTool(context, "OpenDisplaySettingsTool", "Open display settings", Settings.ACTION_DISPLAY_SETTINGS)
