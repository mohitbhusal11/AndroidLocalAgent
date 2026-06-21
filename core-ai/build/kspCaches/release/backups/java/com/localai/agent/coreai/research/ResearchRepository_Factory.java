package com.localai.agent.coreai.research;

import com.localai.agent.coreai.engine.OnDeviceAiRepository;
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
public final class ResearchRepository_Factory implements Factory<ResearchRepository> {
  private final Provider<MemoryDao> memoryDaoProvider;

  private final Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider;

  private ResearchRepository_Factory(Provider<MemoryDao> memoryDaoProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider) {
    this.memoryDaoProvider = memoryDaoProvider;
    this.onDeviceAiRepositoryProvider = onDeviceAiRepositoryProvider;
  }

  @Override
  public ResearchRepository get() {
    return newInstance(memoryDaoProvider.get(), onDeviceAiRepositoryProvider.get());
  }

  public static ResearchRepository_Factory create(Provider<MemoryDao> memoryDaoProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider) {
    return new ResearchRepository_Factory(memoryDaoProvider, onDeviceAiRepositoryProvider);
  }

  public static ResearchRepository newInstance(MemoryDao memoryDao,
      OnDeviceAiRepository onDeviceAiRepository) {
    return new ResearchRepository(memoryDao, onDeviceAiRepository);
  }
}
