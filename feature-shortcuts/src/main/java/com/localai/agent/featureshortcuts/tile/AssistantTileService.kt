package com.localai.agent.featureshortcuts.tile

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import com.localai.agent.core.util.Constants

class AssistantTileService : TileService() {

    override fun onClick() {
        super.onClick()
        unlockAndRun {
            val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                action = Constants.ACTION_VOICE
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            } ?: return@unlockAndRun
            launchAssistant(intent)
        }
    }

    private fun launchAssistant(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("DEPRECATION")
            startActivityAndCollapse(intent)
        }
    }
}
