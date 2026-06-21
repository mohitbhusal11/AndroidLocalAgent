package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.ConversationDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class DatabaseModule_ProvideConversationDaoFactory implements Factory<ConversationDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideConversationDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ConversationDao get() {
    return provideConversationDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideConversationDaoFactory create(
      Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideConversationDaoFactory(dbProvider);
  }

  public static ConversationDao provideConversationDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideConversationDao(db));
  }
}
