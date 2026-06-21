package com.localai.agent.featuredoomscroll.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.featuredoomscroll.viewmodel.FocusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusScreen(viewModel: FocusViewModel = hiltViewModel()) {
    val report by viewModel.report.collectAsState()
    val warning by viewModel.warning.collectAsState()
    var limitMinutes by remember { mutableStateOf("30") }
    val context = LocalContext.current

    Scaffold(topBar = { TopAppBar(title = { Text("Focus / Screen Time") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            if (!viewModel.hasPermission()) {
                Button(onClick = { context.startActivity(viewModel.usageSettingsIntent()) }) {
                    Text("Grant Usage Access")
                }
                Spacer(Modifier.height(12.dp))
            }
            warning?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            Text("Today's usage:", style = MaterialTheme.typography.titleSmall)
            Text(report ?: "Loading…", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = limitMinutes,
                onValueChange = { limitMinutes = it },
                label = { Text("Daily limit (minutes) for YouTube") }
            )
            Button(
                onClick = {
                    viewModel.setYoutubeLimit(limitMinutes.toIntOrNull() ?: 30)
                },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Set YouTube Limit") }
            Text(
                "\nDetects: Reels, Shorts, TikTok, Instagram, YouTube\nFocus mode warns at 80% and blocks at 100%.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
