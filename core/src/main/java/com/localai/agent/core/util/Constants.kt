package com.localai.agent.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {
    val io: CoroutineDispatcher = Dispatchers.IO
    val main: CoroutineDispatcher = Dispatchers.Main
    val default: CoroutineDispatcher = Dispatchers.Default
}

object Constants {
    const val APP_NAME = "Local AI Agent"
    const val NOTIFICATION_CHANNEL_ID = "assistant_channel"
    const val FOREGROUND_SERVICE_ID = 1001
    const val DATASTORE_NAME = "agent_preferences"
    const val KEY_FORCE_LOCAL_ASSISTANT = "force_local_assistant"
    const val KEY_DARK_MODE = "dark_mode"
    const val KEY_VOICE_ENABLED = "voice_enabled"
    const val ACTION_VOICE = "com.localai.agent.ACTION_VOICE"
    const val ACTION_CHAT = "com.localai.agent.ACTION_CHAT"
    const val ACTION_NOTES = "com.localai.agent.ACTION_NOTES"
    const val ACTION_OPEN_ASSISTANT = "com.localai.agent.ACTION_OPEN"
}
