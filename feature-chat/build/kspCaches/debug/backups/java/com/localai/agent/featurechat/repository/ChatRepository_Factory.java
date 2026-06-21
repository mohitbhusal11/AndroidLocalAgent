package com.localai.agent.featurechat.repository;

import com.localai.agent.coredatabase.dao.ConversationDao;
import com.localai.agent.coredatabase.dao.MessageDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<ConversationDao> conversationDaoProvider;

  private final Provider<MessageDao> messageDaoProvider;

  private ChatRepository_Factory(Provider<ConversationDao> conversationDaoProvider,
      Provider<MessageDao> messageDaoProvider) {
    this.conversationDaoProvider = conversationDaoProvider;
    this.messageDaoProvider = messageDaoProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(conversationDaoProvider.get(), messageDaoProvider.get());
  }

  public static ChatRepository_Factory create(Provider<ConversationDao> conversationDaoProvider,
      Provider<MessageDao> messageDaoProvider) {
    return new ChatRepository_Factory(conversationDaoProvider, messageDaoProvider);
  }

  public static ChatRepository newInstance(ConversationDao conversationDao, MessageDao messageDao) {
    return new ChatRepository(conversationDao, messageDao);
  }
}
