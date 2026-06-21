package com.localai.agent.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.localai.agent.coredatabase.entity.ConversationEntity
import com.localai.agent.coredatabase.entity.MemoryEntity
import com.localai.agent.coredatabase.entity.MessageEntity
import com.localai.agent.coredatabase.entity.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations ORDER BY isPinned DESC, updatedAt DESC")
    fun observeAll(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM conversations WHERE id = :id")
    suspend fun getById(id: Long): ConversationEntity?

    @Insert
    suspend fun insert(conversation: ConversationEntity): Long

    @Update
    suspend fun update(conversation: ConversationEntity)

    @Query("UPDATE conversations SET isPinned = :pinned WHERE id = :id")
    suspend fun setPinned(id: Long, pinned: Boolean)

    @Query("DELETE FROM conversations WHERE id = :id")
    suspend fun delete(id: Long)

    @Query(
        """
        SELECT DISTINCT c.* FROM conversations c
        INNER JOIN messages m ON m.conversationId = c.id
        WHERE m.content LIKE '%' || :query || '%' OR c.title LIKE '%' || :query || '%'
        ORDER BY c.updatedAt DESC
        """
    )
    fun search(query: String): Flow<List<ConversationEntity>>
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun observeByConversation(conversationId: Long): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    suspend fun getByConversation(conversationId: Long): List<MessageEntity>

    @Insert
    suspend fun insert(message: MessageEntity): Long

    @Query("SELECT * FROM messages WHERE content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun search(query: String): Flow<List<MessageEntity>>
}

@Dao
interface MemoryDao {
    @Query("SELECT * FROM memories ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM memories WHERE type = :type ORDER BY updatedAt DESC")
    fun observeByType(type: String): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM memories WHERE key = :key LIMIT 1")
    suspend fun getByKey(key: String): MemoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(memory: MemoryEntity)

    @Query("DELETE FROM memories WHERE id = :id")
    suspend fun delete(id: Long)
}

@Dao
interface UserPreferenceDao {
    @Query("SELECT * FROM user_preferences WHERE `key` = :key")
    suspend fun get(key: String): UserPreferenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(preference: UserPreferenceEntity)
}
