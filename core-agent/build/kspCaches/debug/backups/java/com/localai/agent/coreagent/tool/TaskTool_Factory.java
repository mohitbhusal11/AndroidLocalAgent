package com.localai.agent.coreagent.tool;

import com.localai.agent.coredatabase.dao.TaskDao;
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
public final class TaskTool_Factory implements Factory<TaskTool> {
  private final Provider<TaskDao> taskDaoProvider;

  private TaskTool_Factory(Provider<TaskDao> taskDaoProvider) {
    this.taskDaoProvider = taskDaoProvider;
  }

  @Override
  public TaskTool get() {
    return newInstance(taskDaoProvider.get());
  }

  public static TaskTool_Factory create(Provider<TaskDao> taskDaoProvider) {
    return new TaskTool_Factory(taskDaoProvider);
  }

  public static TaskTool newInstance(TaskDao taskDao) {
    return new TaskTool(taskDao);
  }
}
