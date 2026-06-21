package com.localai.agent.corememory.repository

import com.localai.agent.core.model.MemoryType
import com.localai.agent.coredatabase.dao.MemoryDao
import com.localai.agent.coredatabase.entity.MemoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryRepository @Inject constructor(
    private val memoryDao: MemoryDao
) {
    fun observeAll(): Flow<List<MemoryEntity>> = memoryDao.observeAll()

    fun observeByType(type: MemoryType): Flow<List<MemoryEntity>> =
        memoryDao.observeByType(type.name)

    suspend fun save(type: MemoryType, key: String, value: String, metadata: String = "") {
        val existing = memoryDao.getByKey(key)
        memoryDao.upsert(
            MemoryEntity(
                id = existing?.id ?: 0,
                type = type.name,
                key = key,
                value = value,
                metadata = metadata,
                createdAt = existing?.createdAt ?: System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun get(key: String): MemoryEntity? = memoryDao.getByKey(key)

    suspend fun delete(id: Long) = memoryDao.delete(id)

    suspend fun rememberCommand(command: String) {
        save(MemoryType.COMMAND, "cmd_${System.currentTimeMillis()}", command)
    }

    suspend fun rememberContact(name: String, phone: String) {
        save(MemoryType.CONTACT, name.lowercase(), phone, metadata = name)
    }

    suspend fun rememberFavoriteApp(appName: String, packageName: String) {
        save(MemoryType.FAVORITE_APP, appName.lowercase(), packageName)
    }

    suspend fun rememberNote(title: String, content: String) {
        save(MemoryType.NOTE, title.lowercase(), content)
    }

    suspend fun rememberPreference(key: String, value: String) {
        save(MemoryType.PREFERENCE, key, value)
    }
}
