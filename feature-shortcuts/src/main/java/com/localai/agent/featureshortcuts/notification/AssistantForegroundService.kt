package com.localai.agent.featureshortcuts.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.localai.agent.core.util.Constants

class AssistantForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel()
        startForeground(Constants.FOREGROUND_SERVICE_ID, buildNotification())
        return START_STICKY
    }

    private fun buildNotification(): Notification {
        val voiceIntent = pendingIntent(Constants.ACTION_VOICE)
        val chatIntent = pendingIntent(Constants.ACTION_CHAT)
        val notesIntent = pendingIntent(Constants.ACTION_NOTES)
        val openIntent = pendingIntent(Constants.ACTION_OPEN_ASSISTANT)

        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(Constants.APP_NAME)
            .setContentText("Tap to interact with your assistant")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(openIntent)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_btn_speak_now, "Voice", voiceIntent)
            .addAction(android.R.drawable.ic_menu_edit, "Chat", chatIntent)
            .addAction(android.R.drawable.ic_menu_agenda, "Notes", notesIntent)
            .build()
    }

    private fun pendingIntent(action: String): PendingIntent {
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            this.action = action
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        } ?: Intent(action)
        return PendingIntent.getActivity(
            this,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "AI Assistant",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AssistantForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }
}
