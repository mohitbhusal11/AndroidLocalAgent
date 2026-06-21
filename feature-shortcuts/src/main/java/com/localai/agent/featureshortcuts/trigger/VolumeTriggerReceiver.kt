package com.localai.agent.featureshortcuts.trigger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import com.localai.agent.core.util.Constants

class VolumeTriggerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_MEDIA_BUTTON) return
        val event = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT) ?: return
        if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            val now = System.currentTimeMillis()
            val prefs = context.getSharedPreferences("volume_trigger", Context.MODE_PRIVATE)
            val last = prefs.getLong("last_vol_up", 0)
            if (now - last < 400) {
                val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                    action = Constants.ACTION_VOICE
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(launch)
            }
            prefs.edit().putLong("last_vol_up", now).apply()
        }
    }
}
