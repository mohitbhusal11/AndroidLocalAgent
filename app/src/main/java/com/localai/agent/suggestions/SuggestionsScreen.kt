package com.localai.agent.suggestions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.suggestions.SuggestionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionsScreen(viewModel: SuggestionsViewModel = hiltViewModel()) {
    val suggestions by viewModel.suggestions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Suggestions") },
                actions = {
                    TextButton(onClick = viewModel::refresh) { Text("Refresh") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(suggestions) { s ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(s.title)
                        Text(s.message)
                        TextButton(onClick = { viewModel.dismiss(s.id) }) { Text("Dismiss") }
                    }
                }
            }
        }
    }
}
