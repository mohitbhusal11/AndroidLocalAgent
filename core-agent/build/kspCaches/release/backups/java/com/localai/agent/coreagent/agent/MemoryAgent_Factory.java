package com.localai.agent.coreagent.agent;

import com.localai.agent.corememory.repository.MemoryRepository;
import com.localai.agent.corememory.routine.RoutineTracker;
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
public final class MemoryAgent_Factory implements Factory<MemoryAgent> {
  private final Provider<MemoryRepository> memoryRepositoryProvider;

  private final Provider<RoutineTracker> routineTrackerProvider;

  private MemoryAgent_Factory(Provider<MemoryRepository> memoryRepositoryProvider,
      Provider<RoutineTracker> routineTrackerProvider) {
    this.memoryRepositoryProvider = memoryRepositoryProvider;
    this.routineTrackerProvider = routineTrackerProvider;
  }

  @Override
  public MemoryAgent get() {
    return newInstance(memoryRepositoryProvider.get(), routineTrackerProvider.get());
  }

  public static MemoryAgent_Factory create(Provider<MemoryRepository> memoryRepositoryProvider,
      Provider<RoutineTracker> routineTrackerProvider) {
    return new MemoryAgent_Factory(memoryRepositoryProvider, routineTrackerProvider);
  }

  public static MemoryAgent newInstance(MemoryRepository memoryRepository,
      RoutineTracker routineTracker) {
    return new MemoryAgent(memoryRepository, routineTracker);
  }
}
