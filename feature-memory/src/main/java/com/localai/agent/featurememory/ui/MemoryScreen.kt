package com.localai.agent.featurememory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.coredatabase.entity.MemoryEntity
import com.localai.agent.featurememory.viewmodel.MemoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryScreen(
    viewModel: MemoryViewModel = hiltViewModel()
) {
    val memories by viewModel.memories.collectAsState()
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Memory") }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Add Note", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title") }
            )
            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Content") }
            )
            androidx.compose.material3.Button(
                onClick = {
                    if (noteContent.isNotBlank()) {
                        viewModel.addNote(noteTitle.ifBlank { "Note" }, noteContent)
                        noteTitle = ""
                        noteContent = ""
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Save")
            }

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(memories) { memory ->
                    MemoryItem(memory) { viewModel.delete(memory.id) }
                }
            }
        }
    }
}

@Composable
private fun MemoryItem(memory: MemoryEntity, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(memory.type, style = MaterialTheme.typography.labelMedium)
            Text(memory.key, style = MaterialTheme.typography.titleSmall)
            Text(memory.value, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}
