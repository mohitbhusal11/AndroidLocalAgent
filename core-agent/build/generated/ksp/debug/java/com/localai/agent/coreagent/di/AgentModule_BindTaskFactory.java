package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.TaskTool;
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
public final class AgentModule_BindTaskFactory implements Factory<AgentTool> {
  private final Provider<TaskTool> tProvider;

  private AgentModule_BindTaskFactory(Provider<TaskTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindTask(tProvider.get());
  }

  public static AgentModule_BindTaskFactory create(Provider<TaskTool> tProvider) {
    return new AgentModule_BindTaskFactory(tProvider);
  }

  public static AgentTool bindTask(TaskTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindTask(t));
  }
}
