package com.localai.agent.coredatabase.di

import android.content.Context
import androidx.room.Room
import com.localai.agent.coredatabase.AgentDatabase
import com.localai.agent.coredatabase.dao.AppUsageDao
import com.localai.agent.coredatabase.dao.ConversationDao
import com.localai.agent.coredatabase.dao.FocusLimitDao
import com.localai.agent.coredatabase.dao.MemoryDao
import com.localai.agent.coredatabase.dao.MessageDao
import com.localai.agent.coredatabase.dao.NotificationDao
import com.localai.agent.coredatabase.dao.RoutineDao
import com.localai.agent.coredatabase.dao.SuggestionDao
import com.localai.agent.coredatabase.dao.TaskDao
import com.localai.agent.coredatabase.dao.UserPreferenceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AgentDatabase =
        Room.databaseBuilder(context, AgentDatabase::class.java, "agent_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideConversationDao(db: AgentDatabase): ConversationDao = db.conversationDao()
    @Provides fun provideMessageDao(db: AgentDatabase): MessageDao = db.messageDao()
    @Provides fun provideMemoryDao(db: AgentDatabase): MemoryDao = db.memoryDao()
    @Provides fun provideUserPreferenceDao(db: AgentDatabase): UserPreferenceDao = db.userPreferenceDao()
    @Provides fun provideNotificationDao(db: AgentDatabase): NotificationDao = db.notificationDao()
    @Provides fun provideAppUsageDao(db: AgentDatabase): AppUsageDao = db.appUsageDao()
    @Provides fun provideFocusLimitDao(db: AgentDatabase): FocusLimitDao = db.focusLimitDao()
    @Provides fun provideTaskDao(db: AgentDatabase): TaskDao = db.taskDao()
    @Provides fun provideSuggestionDao(db: AgentDatabase): SuggestionDao = db.suggestionDao()
    @Provides fun provideRoutineDao(db: AgentDatabase): RoutineDao = db.routineDao()
}
