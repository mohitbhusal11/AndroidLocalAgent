package com.localai.agent.featureresearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.featureresearch.viewmodel.ResearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchScreen(viewModel: ResearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val result by viewModel.result.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Research (Offline)") }) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Topic or text to summarize") }
            )
            Button(onClick = { viewModel.summarize(query) }, modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Summarize")
            }
            Button(onClick = { viewModel.searchLocal(query) }, modifier = Modifier.padding(bottom = 8.dp)) {
                Text("Search Local Memory")
            }
            result?.let { Text(it) }
        }
    }
}
