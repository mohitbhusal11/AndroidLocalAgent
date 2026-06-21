package com.localai.agent.featureaccessibility.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.corepermissions.AgentPermissions
import com.localai.agent.featureaccessibility.viewmodel.AutomationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutomationScreen(viewModel: AutomationViewModel = hiltViewModel()) {
    val controller = viewModel.accessibilityController
    val connected by controller.isConnected.collectAsState()
    val foreground by controller.foregroundApp.collectAsState()
    val context = LocalContext.current

    Scaffold(topBar = { TopAppBar(title = { Text("Automation") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text(
                if (connected) "Accessibility service connected" else "Accessibility not enabled",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Text("Foreground app: ${foreground ?: "unknown"}")
            Spacer(Modifier.height(16.dp))
            Text(
                "Supports WhatsApp, Instagram, YouTube, Chrome, Settings.\n\n" +
                    "Try: scroll down, click Send, read screen, back, home",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(16.dp))
            if (!connected) {
                Button(onClick = { context.startActivity(AgentPermissions.accessibilityIntent(context)) }) {
                    Text("Enable Accessibility")
                }
            }
        }
    }
}
