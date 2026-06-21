package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.TaskDao;
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
public final class DatabaseModule_ProvideTaskDaoFactory implements Factory<TaskDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideTaskDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TaskDao get() {
    return provideTaskDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTaskDaoFactory create(Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideTaskDaoFactory(dbProvider);
  }

  public static TaskDao provideTaskDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTaskDao(db));
  }
}
