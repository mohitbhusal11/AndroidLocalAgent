package com.localai.agent.coredatabase.di;

import com.localai.agent.coredatabase.AgentDatabase;
import com.localai.agent.coredatabase.dao.FocusLimitDao;
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
public final class DatabaseModule_ProvideFocusLimitDaoFactory implements Factory<FocusLimitDao> {
  private final Provider<AgentDatabase> dbProvider;

  private DatabaseModule_ProvideFocusLimitDaoFactory(Provider<AgentDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FocusLimitDao get() {
    return provideFocusLimitDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideFocusLimitDaoFactory create(
      Provider<AgentDatabase> dbProvider) {
    return new DatabaseModule_ProvideFocusLimitDaoFactory(dbProvider);
  }

  public static FocusLimitDao provideFocusLimitDao(AgentDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFocusLimitDao(db));
  }
}
