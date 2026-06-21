package com.localai.agent.coreagent.tool;

import com.localai.agent.corememory.repository.MemoryRepository;
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
public final class CreateNoteTool_Factory implements Factory<CreateNoteTool> {
  private final Provider<MemoryRepository> memoryRepositoryProvider;

  private CreateNoteTool_Factory(Provider<MemoryRepository> memoryRepositoryProvider) {
    this.memoryRepositoryProvider = memoryRepositoryProvider;
  }

  @Override
  public CreateNoteTool get() {
    return newInstance(memoryRepositoryProvider.get());
  }

  public static CreateNoteTool_Factory create(Provider<MemoryRepository> memoryRepositoryProvider) {
    return new CreateNoteTool_Factory(memoryRepositoryProvider);
  }

  public static CreateNoteTool newInstance(MemoryRepository memoryRepository) {
    return new CreateNoteTool(memoryRepository);
  }
}
