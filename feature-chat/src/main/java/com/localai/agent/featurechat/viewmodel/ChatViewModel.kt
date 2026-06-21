package com.localai.agent.featurechat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.core.model.AiStreamEvent
import com.localai.agent.core.model.MessageRole
import com.localai.agent.coreagent.agent.PlannerAgent
import com.localai.agent.coredatabase.entity.ConversationEntity
import com.localai.agent.coredatabase.entity.MessageEntity
import com.localai.agent.featurechat.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<MessageEntity> = emptyList(),
    val inputText: String = "",
    val isStreaming: Boolean = false,
    val streamingText: String = "",
    val error: String? = null
)

data class ConversationsUiState(
    val conversations: List<ConversationEntity> = emptyList(),
    val searchQuery: String = "",
    val exportText: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val plannerAgent: PlannerAgent
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var conversationId: Long = 0
    private var observeJob: kotlinx.coroutines.Job? = null

    fun loadConversation(id: Long) {
        conversationId = id
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            chatRepository.observeMessages(id).collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    fun updateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val text = _uiState.value.inputText.trim()
        if (text.isBlank() || _uiState.value.isStreaming) return

        viewModelScope.launch {
            _uiState.update { it.copy(inputText = "", isStreaming = true, streamingText = "", error = null) }
            chatRepository.addMessage(conversationId, MessageRole.USER, text)

            val history = _uiState.value.messages.map { msg ->
                val role = if (msg.role == MessageRole.USER.name) "user" else "model"
                role to msg.content
            } + ("user" to text)

            var fullResponse = ""
            plannerAgent.chat(text, history).collect { event ->
                when (event) {
                    is AiStreamEvent.Token -> {
                        fullResponse += event.text
                        _uiState.update { it.copy(streamingText = fullResponse) }
                    }
                    is AiStreamEvent.Complete -> {
                        fullResponse = event.fullText
                    }
                    is AiStreamEvent.Error -> {
                        _uiState.update { it.copy(error = event.message, isStreaming = false) }
                    }
                    else -> {}
                }
            }

            if (fullResponse.isNotBlank()) {
                chatRepository.addMessage(conversationId, MessageRole.ASSISTANT, fullResponse)
            }
            _uiState.update { it.copy(isStreaming = false, streamingText = "") }
        }
    }
}

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val conversations: StateFlow<List<ConversationEntity>> = chatRepository.observeConversations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _exportText = MutableStateFlow<String?>(null)
    val exportText: StateFlow<String?> = _exportText.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchResults(): StateFlow<List<ConversationEntity>> =
        chatRepository.searchConversations(_searchQuery.value)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun createConversation(): Long = chatRepository.createConversation()

    fun togglePin(id: Long, pinned: Boolean) {
        viewModelScope.launch { chatRepository.togglePin(id, pinned) }
    }

    fun deleteConversation(id: Long) {
        viewModelScope.launch { chatRepository.deleteConversation(id) }
    }

    fun exportConversation(id: Long) {
        viewModelScope.launch {
            _exportText.value = chatRepository.exportConversation(id)
        }
    }

    fun clearExport() {
        _exportText.value = null
    }
}
