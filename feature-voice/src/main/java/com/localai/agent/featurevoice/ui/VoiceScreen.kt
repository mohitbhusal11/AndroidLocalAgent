package com.localai.agent.featurevoice.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.coreui.layout.TabScaffoldInsets
import com.localai.agent.featurevoice.viewmodel.VoiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceScreen(
    viewModel: VoiceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        contentWindowInsets = TabScaffoldInsets(),
        topBar = { TopAppBar(title = { Text("Voice Assistant") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Tap the mic and speak a command",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            FilledIconButton(
                onClick = {
                    if (uiState.isListening) viewModel.stopListening()
                    else viewModel.startListening()
                },
                modifier = Modifier.size(96.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (uiState.isListening)
                        MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    if (uiState.isListening) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Microphone",
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(
                when {
                    uiState.isListening -> "Listening..."
                    uiState.isSpeaking -> "Speaking..."
                    else -> "Ready"
                },
                style = MaterialTheme.typography.labelMedium
            )

            RowSetting(
                label = "Voice-first (say \"Hey Assistant\")",
                checked = uiState.voiceFirstMode,
                onCheckedChange = { viewModel.toggleVoiceFirstMode() }
            )
            RowSetting(
                label = "Continuous conversation",
                checked = uiState.continuousMode,
                onCheckedChange = { viewModel.toggleContinuousMode() }
            )

            uiState.partialText.takeIf { it.isNotBlank() }?.let {
                VoiceCard("Hearing", it)
            }
            uiState.lastCommand.takeIf { it.isNotBlank() }?.let {
                VoiceCard("You said", it)
            }
            uiState.response.takeIf { it.isNotBlank() }?.let {
                VoiceCard("Assistant", it)
            }
            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Try: \"Open WhatsApp\", \"Call Mom\", \"Open Camera\", \"What's the battery?\"",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun VoiceCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            Text(content)
        }
    }
}

@Composable
private fun RowSetting(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
