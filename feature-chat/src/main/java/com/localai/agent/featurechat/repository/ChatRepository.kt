package com.localai.agent.featurechat.repository

import com.localai.agent.core.model.MessageRole
import com.localai.agent.coredatabase.dao.ConversationDao
import com.localai.agent.coredatabase.dao.MessageDao
import com.localai.agent.coredatabase.entity.ConversationEntity
import com.localai.agent.coredatabase.entity.MessageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao
) {
    fun observeConversations(): Flow<List<ConversationEntity>> = conversationDao.observeAll()

    fun observeMessages(conversationId: Long): Flow<List<MessageEntity>> =
        messageDao.observeByConversation(conversationId)

    fun searchConversations(query: String): Flow<List<ConversationEntity>> =
        conversationDao.search(query)

    fun searchMessages(query: String): Flow<List<MessageEntity>> = messageDao.search(query)

    suspend fun createConversation(title: String = "New Chat"): Long {
        return conversationDao.insert(
            ConversationEntity(title = title)
        )
    }

    suspend fun addMessage(conversationId: Long, role: MessageRole, content: String): Long {
        val id = messageDao.insert(
            MessageEntity(
                conversationId = conversationId,
                role = role.name,
                content = content
            )
        )
        conversationDao.getById(conversationId)?.let { conv ->
            val newTitle = if (conv.title == "New Chat" && role == MessageRole.USER) {
                content.take(40).let { if (content.length > 40) "$it..." else it }
            } else conv.title
            conversationDao.update(
                conv.copy(title = newTitle, updatedAt = System.currentTimeMillis())
            )
        }
        return id
    }

    suspend fun togglePin(conversationId: Long, pinned: Boolean) {
        conversationDao.setPinned(conversationId, pinned)
    }

    suspend fun deleteConversation(conversationId: Long) {
        conversationDao.delete(conversationId)
    }

    suspend fun exportConversation(conversationId: Long): String {
        val messages = messageDao.getByConversation(conversationId)
        val conv = conversationDao.getById(conversationId)
        val header = "# ${conv?.title ?: "Chat"}\nExported: ${java.util.Date()}\n\n"
        return header + messages.joinToString("\n\n") { msg ->
            "**${msg.role}** (${java.text.SimpleDateFormat("HH:mm").format(java.util.Date(msg.timestamp))}):\n${msg.content}"
        }
    }
}
