package com.localai.agent

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.localai.agent.coreai.engine.OnDeviceAiRepository
import com.localai.agent.corenetwork.SettingsRepository
import com.localai.agent.corepermissions.AgentPermissions
import com.localai.agent.coreui.theme.AgentTheme
import com.localai.agent.featureshortcuts.bubble.FloatingBubbleService
import com.localai.agent.featureshortcuts.notification.AssistantForegroundService
import com.localai.agent.featureshortcuts.sensor.ShakeDetector
import com.localai.agent.navigation.AgentNavHost
import com.localai.agent.worker.SuggestionWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var onDeviceAiRepository: OnDeviceAiRepository

    private var shakeDetector: ShakeDetector? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { /* per-feature */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestCorePermissions()
        startAssistantServices()
        SuggestionWorker.schedule(this)
        shakeDetector = ShakeDetector(this).also { it.start() }
        lifecycleScope.launch { runCatching { onDeviceAiRepository.warmup() } }

        setContent {
            val darkMode by settingsRepository.isDarkMode.collectAsState(initial = true)
            AgentTheme(darkTheme = darkMode) {
                AgentNavHost(initialAction = intent?.action)
            }
        }
    }

    override fun onDestroy() {
        shakeDetector?.stop()
        super.onDestroy()
    }

    private fun requestCorePermissions() {
        val needed = AgentPermissions.RUNTIME.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (needed.isNotEmpty()) permissionLauncher.launch(needed.toTypedArray())
    }

    private fun startAssistantServices() {
        AssistantForegroundService.start(this)
        if (Settings.canDrawOverlays(this)) {
            FloatingBubbleService.start(this)
        }
    }
}
