package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.RoutineDao;
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
public final class DatabaseModule_ProvideRoutineDaoFactory implements Factory<RoutineDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideRoutineDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RoutineDao get() {
    return provideRoutineDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRoutineDaoFactory create(Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideRoutineDaoFactory(dbProvider);
  }

  public static RoutineDao provideRoutineDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRoutineDao(db));
  }
}
