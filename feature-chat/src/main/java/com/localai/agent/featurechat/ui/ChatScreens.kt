@file:OptIn(ExperimentalMaterial3Api::class)

package com.localai.agent.featurechat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.localai.agent.core.model.MessageRole
import com.localai.agent.coreui.layout.TabScaffoldInsets
import com.localai.agent.coreui.theme.AssistantBubble
import com.localai.agent.coreui.theme.AssistantBubbleDark
import com.localai.agent.coreui.theme.UserBubble
import com.localai.agent.coredatabase.entity.ConversationEntity
import com.localai.agent.coredatabase.entity.MessageEntity
import com.localai.agent.featurechat.viewmodel.ChatViewModel
import com.localai.agent.featurechat.viewmodel.ConversationsViewModel
import com.mikepenz.markdown.m3.Markdown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsScreen(
    onConversationClick: (Long) -> Unit,
    onNewChat: () -> Unit,
    viewModel: ConversationsViewModel = hiltViewModel()
) {
    val conversations by viewModel.conversations.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val exportText by viewModel.exportText.collectAsState()

    Scaffold(
        contentWindowInsets = TabScaffoldInsets(),
        topBar = {
            TopAppBar(title = { Text("Conversations") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNewChat) {
                Text("+")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::setSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search messages...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(conversations.filter {
                    searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true)
                }) { conv ->
                    ConversationItem(
                        conversation = conv,
                        onClick = { onConversationClick(conv.id) },
                        onPin = { viewModel.togglePin(conv.id, !conv.isPinned) },
                        onExport = { viewModel.exportConversation(conv.id) }
                    )
                }
            }
        }
    }

    exportText?.let { text ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = viewModel::clearExport,
            title = { Text("Export Chat") },
            text = { Text(text.take(2000) + if (text.length > 2000) "..." else "") },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = viewModel::clearExport) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun ConversationItem(
    conversation: ConversationEntity,
    onClick: () -> Unit,
    onPin: () -> Unit,
    onExport: () -> Unit
) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(conversation.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
                        .format(Date(conversation.updatedAt)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onPin) {
                Icon(
                    Icons.Default.PushPin,
                    contentDescription = "Pin",
                    tint = if (conversation.isPinned) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onExport) {
                Text("⤴")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    conversationId: Long,
    onBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    LaunchedEffect(conversationId) { viewModel.loadConversation(conversationId) }
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size, uiState.streamingText) {
        listState.animateScrollToItem(
            maxOf(0, uiState.messages.size + if (uiState.isStreaming) 1 else 0)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.inputText,
                    onValueChange = viewModel::updateInput,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message your assistant...") },
                    enabled = !uiState.isStreaming
                )
                IconButton(
                    onClick = viewModel::sendMessage,
                    enabled = uiState.inputText.isNotBlank() && !uiState.isStreaming
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.messages) { message ->
                MessageBubble(message)
            }
            if (uiState.isStreaming && uiState.streamingText.isNotEmpty()) {
                item {
                    MessageBubble(
                        MessageEntity(
                            conversationId = conversationId,
                            role = MessageRole.ASSISTANT.name,
                            content = uiState.streamingText
                        ),
                        isStreaming = true
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: MessageEntity, isStreaming: Boolean = false) {
    val isUser = message.role == MessageRole.USER.name
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val bubbleColor = when {
        isUser -> UserBubble
        isDark -> AssistantBubbleDark
        else -> AssistantBubble
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            if (isUser) {
                Text(message.content, color = androidx.compose.ui.graphics.Color.White)
            } else {
                Markdown(message.content)
                if (isStreaming) {
                    CircularProgressIndicator(Modifier.size(16.dp).padding(top = 8.dp))
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.Color.luminance(): Float {
    return 0.299f * red + 0.587f * green + 0.114f * blue
}
