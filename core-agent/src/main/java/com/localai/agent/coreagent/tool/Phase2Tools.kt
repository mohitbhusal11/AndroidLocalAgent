package com.localai.agent.coreagent.tool

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.Telephony
import com.localai.agent.core.model.AgentResult
import com.localai.agent.coredatabase.dao.TaskDao
import com.localai.agent.coredatabase.entity.TaskEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "CalendarTool"
    override val description = "Open calendar or create event. Args: title, time (optional)"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val title = args["title"] ?: "Event"
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, args["description"] ?: "")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Calendar event created: $title")
    }
}

@Singleton
class TaskTool @Inject constructor(
    private val taskDao: TaskDao
) : AgentTool {
    override val name = "TaskTool"
    override val description = "Create or list tasks. Args: title, action=list|create"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        return if (args["action"] == "list") {
            val tasks = taskDao.observeActive()
            AgentResult(true, "Use Tasks screen to view active tasks")
        } else {
            val title = args["title"] ?: return AgentResult(false, "Title required")
            taskDao.insert(TaskEntity(title = title, description = args["description"] ?: ""))
            AgentResult(true, "Task created: $title")
        }
    }
}

@Singleton
class AlarmTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "AlarmTool"
    override val description = "Set an alarm. Args: message, hour, minutes"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val message = args["message"] ?: "Alarm"
        val (hour, minutes) = resolveAlarmTime(
            message = message,
            hourArg = args["hour"]?.toIntOrNull(),
            minuteArg = args["minutes"]?.toIntOrNull()
        )
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return context.launchClockIntent(
            intent = intent,
            successMessage = "Alarm set for ${hour.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
        )
    }
}

internal fun resolveAlarmTime(
    message: String,
    hourArg: Int?,
    minuteArg: Int?
): Pair<Int, Int> {
    if (hourArg != null) return hourArg to (minuteArg ?: 0)

    val lower = message.lowercase()
    val calendar = Calendar.getInstance()

    Regex("""(?:after|in)\s+(\d+)\s*(?:h|hr|hrs|hour|hours)""").find(lower)?.let { match ->
        calendar.add(Calendar.HOUR_OF_DAY, match.groupValues[1].toInt())
        return calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
    }

    Regex("""(?:after|in)\s+(\d+)\s*(?:m|min|mins|minute|minutes)""").find(lower)?.let { match ->
        calendar.add(Calendar.MINUTE, match.groupValues[1].toInt())
        return calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
    }

    return (hourArg ?: 8) to (minuteArg ?: 0)
}

internal fun Context.launchClockIntent(intent: Intent, successMessage: String): AgentResult {
    return try {
        startActivity(intent)
        AgentResult(true, successMessage)
    } catch (_: SecurityException) {
        AgentResult(false, "Cannot set alarm — clock permission is missing on this device.")
    } catch (e: Exception) {
        AgentResult(false, e.message ?: "Could not open clock app")
    }
}

@Singleton
class TimerTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "TimerTool"
    override val description = "Set a timer. Args: seconds"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val seconds = args["seconds"]?.toIntOrNull() ?: 60
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return context.launchClockIntent(intent, "Timer set for ${seconds}s")
    }
}

@Singleton
class StopwatchTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "StopwatchTool"
    override val description = "Open clock stopwatch"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return context.launchClockIntent(intent, "Opened clock app")
    }
}

@Singleton
class SmsTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "SmsTool"
    override val description = "Send SMS. Args: phone, message OR name, message"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val message = args["message"] ?: args["text"] ?: return AgentResult(false, "Message required")
        val phone = args["phone"]
        val uri = if (!phone.isNullOrBlank()) {
            Uri.parse("smsto:$phone")
        } else {
            Uri.parse("smsto:")
        }
        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "SMS compose opened")
    }
}

@Singleton
class EmailTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "EmailTool"
    override val description = "Compose email. Args: to, subject, body"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(args["to"] ?: ""))
            putExtra(Intent.EXTRA_SUBJECT, args["subject"] ?: "")
            putExtra(Intent.EXTRA_TEXT, args["body"] ?: args["message"] ?: "")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Email compose opened")
    }
}

@Singleton
class ContactTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "ContactTool"
    override val description = "Open contacts app or search. Args: query (optional)"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val query = args["query"] ?: args["name"]
        val contactsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.contacts/contacts/")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(contactsIntent)
        return AgentResult(true, "Contacts opened${if (!query.isNullOrBlank()) " — search for $query" else ""}")
    }
}

@Singleton
class CallTool @Inject constructor(
    @ApplicationContext private val context: Context
) : AgentTool {
    override val name = "CallTool"
    override val description = "Call a phone number. Args: phone"

    override suspend fun execute(args: Map<String, String>): AgentResult {
        val phone = args["phone"] ?: return AgentResult(false, "Phone required")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return AgentResult(true, "Dialing $phone")
    }
}
