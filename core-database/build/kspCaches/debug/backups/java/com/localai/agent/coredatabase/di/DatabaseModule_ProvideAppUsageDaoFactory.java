package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.AppUsageDao;
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
public final class DatabaseModule_ProvideAppUsageDaoFactory implements Factory<AppUsageDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideAppUsageDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AppUsageDao get() {
    return provideAppUsageDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAppUsageDaoFactory create(
      Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideAppUsageDaoFactory(dbProvider);
  }

  public static AppUsageDao provideAppUsageDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppUsageDao(db));
  }
}
