package com.localai.agent.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.localai.agent.coreui.layout.TabScaffoldInsets
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ToolEntry(val title: String, val route: String, val emoji: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsDashboardScreen(onNavigate: (String) -> Unit) {
    val tools = listOf(
        ToolEntry("Memory", "memory", "🧠"),
        ToolEntry("Notifications", "notifications", "🔔"),
        ToolEntry("Focus Mode", "focus", "🎯"),
        ToolEntry("Productivity", "productivity", "📋"),
        ToolEntry("Research", "research", "🔍"),
        ToolEntry("Automation", "automation", "🤖"),
        ToolEntry("Suggestions", "suggestions", "💡"),
        ToolEntry("Settings", "settings", "⚙️")
    )

    Scaffold(
        contentWindowInsets = TabScaffoldInsets(),
        topBar = { TopAppBar(title = { Text("Tools") }) }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tools) { tool ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(tool.route) }
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(tool.emoji, style = MaterialTheme.typography.headlineMedium)
                        Text(tool.title, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
