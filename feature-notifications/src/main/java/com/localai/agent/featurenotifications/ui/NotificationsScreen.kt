package com.localai.agent.featurenotifications.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.featurenotifications.viewmodel.NotificationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel = hiltViewModel()) {
    val notifications by viewModel.notifications.collectAsState()
    val summary by viewModel.summary.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Notifications") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            summary?.let {
                Card(Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Summary", style = MaterialTheme.typography.titleSmall)
                        Text(it)
                    }
                }
            }
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(notifications) { n ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(12.dp)) {
                            Text("P${n.priority} • ${n.title}", style = MaterialTheme.typography.titleSmall)
                            Text(n.text, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Reply: ${viewModel.smartReply(n)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
