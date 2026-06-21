package com.localai.agent.featurememory.viewmodel;

import com.localai.agent.corememory.repository.MemoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MemoryViewModel_Factory implements Factory<MemoryViewModel> {
  private final Provider<MemoryRepository> memoryRepositoryProvider;

  private MemoryViewModel_Factory(Provider<MemoryRepository> memoryRepositoryProvider) {
    this.memoryRepositoryProvider = memoryRepositoryProvider;
  }

  @Override
  public MemoryViewModel get() {
    return newInstance(memoryRepositoryProvider.get());
  }

  public static MemoryViewModel_Factory create(
      Provider<MemoryRepository> memoryRepositoryProvider) {
    return new MemoryViewModel_Factory(memoryRepositoryProvider);
  }

  public static MemoryViewModel newInstance(MemoryRepository memoryRepository) {
    return new MemoryViewModel(memoryRepository);
  }
}
