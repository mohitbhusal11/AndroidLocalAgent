package com.localai.agent.corememory.routine;

import com.localai.agent.coredatabase.dao.RoutineDao;
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
public final class RoutineTracker_Factory implements Factory<RoutineTracker> {
  private final Provider<RoutineDao> routineDaoProvider;

  private RoutineTracker_Factory(Provider<RoutineDao> routineDaoProvider) {
    this.routineDaoProvider = routineDaoProvider;
  }

  @Override
  public RoutineTracker get() {
    return newInstance(routineDaoProvider.get());
  }

  public static RoutineTracker_Factory create(Provider<RoutineDao> routineDaoProvider) {
    return new RoutineTracker_Factory(routineDaoProvider);
  }

  public static RoutineTracker newInstance(RoutineDao routineDao) {
    return new RoutineTracker(routineDao);
  }
}
