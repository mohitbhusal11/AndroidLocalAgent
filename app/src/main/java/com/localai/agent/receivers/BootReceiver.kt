package com.localai.agent.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.localai.agent.core.util.Constants
import com.localai.agent.featureshortcuts.notification.AssistantForegroundService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            AssistantForegroundService.start(context)
        }
    }
}

/**
 * Headset hook / media button trigger (Shortcut #6).
 */
class HeadsetTriggerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_MEDIA_BUTTON) {
            val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                action = Constants.ACTION_VOICE
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launch)
        }
    }
}
