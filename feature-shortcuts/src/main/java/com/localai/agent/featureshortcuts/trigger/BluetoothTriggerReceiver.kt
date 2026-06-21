package com.localai.agent.featureshortcuts.trigger

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.localai.agent.core.util.Constants

class BluetoothTriggerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1)
            if (state == BluetoothAdapter.STATE_CONNECTED) {
                launchVoice(context)
            }
        }
    }

    private fun launchVoice(context: Context) {
        val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            action = Constants.ACTION_VOICE
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(launch)
    }
}
