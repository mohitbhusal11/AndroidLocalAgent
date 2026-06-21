package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.SuggestionDao;
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
public final class DatabaseModule_ProvideSuggestionDaoFactory implements Factory<SuggestionDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideSuggestionDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SuggestionDao get() {
    return provideSuggestionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSuggestionDaoFactory create(
      Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideSuggestionDaoFactory(dbProvider);
  }

  public static SuggestionDao provideSuggestionDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSuggestionDao(db));
  }
}
