package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.NotificationDao;
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
public final class DatabaseModule_ProvideNotificationDaoFactory implements Factory<NotificationDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideNotificationDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NotificationDao get() {
    return provideNotificationDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideNotificationDaoFactory create(
      Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideNotificationDaoFactory(dbProvider);
  }

  public static NotificationDao provideNotificationDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNotificationDao(db));
  }
}
