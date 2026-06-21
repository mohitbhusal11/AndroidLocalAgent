package com.localai.agent.corememory.repository;

import com.localai.agent.coredatabase.dao.MemoryDao;
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
public final class MemoryRepository_Factory implements Factory<MemoryRepository> {
  private final Provider<MemoryDao> memoryDaoProvider;

  private MemoryRepository_Factory(Provider<MemoryDao> memoryDaoProvider) {
    this.memoryDaoProvider = memoryDaoProvider;
  }

  @Override
  public MemoryRepository get() {
    return newInstance(memoryDaoProvider.get());
  }

  public static MemoryRepository_Factory create(Provider<MemoryDao> memoryDaoProvider) {
    return new MemoryRepository_Factory(memoryDaoProvider);
  }

  public static MemoryRepository newInstance(MemoryDao memoryDao) {
    return new MemoryRepository(memoryDao);
  }
}
