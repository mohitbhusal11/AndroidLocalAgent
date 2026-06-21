package com.localai.agent.featuresettings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.coreui.layout.TabScaffoldInsets
import com.localai.agent.featuresettings.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val forceLocal by viewModel.forceLocalAssistant.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()
    val aiStatus by viewModel.aiStatus.collectAsState()
    val downloadMessage by viewModel.downloadMessage.collectAsState()
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = TabScaffoldInsets(),
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("On-Device AI", style = MaterialTheme.typography.titleMedium)
            Text(
                "This app runs 100% offline. No cloud API keys. No data leaves your device.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        aiStatus?.provider?.name ?: "Checking…",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        aiStatus?.message ?: "Detecting on-device AI…",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    aiStatus?.modelName?.let {
                        Text("Model: $it", style = MaterialTheme.typography.bodySmall)
                    }
                    if (aiStatus?.featureStatus == "DOWNLOADABLE") {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = viewModel::downloadGeminiNano) {
                            Text("Download Gemini Nano")
                        }
                    }
                    TextButton(onClick = viewModel::refreshAiStatus) {
                        Text("Refresh status")
                    }
                }
            }

            downloadMessage?.let { msg ->
                Spacer(Modifier.height(8.dp))
                Text(msg, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(24.dp))
            SettingSwitch(
                label = "Force local assistant only (skip Gemini Nano)",
                checked = forceLocal
            ) {
                viewModel.setForceLocalAssistant(it)
            }
            SettingSwitch("Dark mode", darkMode) {
                viewModel.setDarkMode(it)
            }

            Spacer(Modifier.height(24.dp))
            Text("Supported devices for Gemini Nano", style = MaterialTheme.typography.titleMedium)
            Text(
                "Pixel 8+, Samsung Galaxy S24/S25/S26, select OnePlus/Oppo/vivo devices with AICore. " +
                    "Other devices use the built-in local assistant + phone tools — still fully offline.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))
            Text("Permissions", style = MaterialTheme.typography.titleMedium)
            Text(
                "Grant microphone, contacts, overlay, notification listener, accessibility, and usage access when prompted.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
            androidx.compose.material3.Button(onClick = {
                context.startActivity(com.localai.agent.corepermissions.AgentPermissions.notificationListenerIntent(context))
            }) { Text("Enable Notification Access") }
            androidx.compose.material3.Button(
                onClick = { context.startActivity(com.localai.agent.corepermissions.AgentPermissions.accessibilityIntent(context)) },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Enable Accessibility") }
            androidx.compose.material3.Button(
                onClick = { context.startActivity(com.localai.agent.corepermissions.AgentPermissions.usageStatsIntent(context)) },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Enable Usage Access") }
        }
    }
}

@Composable
private fun SettingSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
