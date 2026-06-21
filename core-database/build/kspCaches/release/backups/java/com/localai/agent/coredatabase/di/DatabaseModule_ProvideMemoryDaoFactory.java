package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.MemoryDao;
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
public final class DatabaseModule_ProvideMemoryDaoFactory implements Factory<MemoryDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideMemoryDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MemoryDao get() {
    return provideMemoryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideMemoryDaoFactory create(Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideMemoryDaoFactory(dbProvider);
  }

  public static MemoryDao provideMemoryDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMemoryDao(db));
  }
}
