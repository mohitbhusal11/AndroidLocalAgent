package com.localai.agent.featureproductivity.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.localai.agent.featureproductivity.viewmodel.ProductivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductivityScreen(viewModel: ProductivityViewModel = hiltViewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    var newTask by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Productivity") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("New task") }
            )
            androidx.compose.material3.Button(
                onClick = { if (newTask.isNotBlank()) { viewModel.addTask(newTask); newTask = "" } },
                modifier = Modifier.padding(vertical = 8.dp)
            ) { Text("Add Task") }
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(tasks) { task ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(task.title, Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
