package com.localai.agent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Trampoline for legacy shortcut intents that target this activity directly.
 * Manifest shortcuts launch [MainActivity] with the appropriate action instead.
 */
class ShortcutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action = intent?.action
        val launch = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            if (action != null) this.action = action
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(launch)
        finish()
    }
}
