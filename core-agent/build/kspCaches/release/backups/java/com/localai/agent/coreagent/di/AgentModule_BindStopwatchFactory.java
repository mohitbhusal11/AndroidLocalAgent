package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.StopwatchTool;
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
public final class AgentModule_BindStopwatchFactory implements Factory<AgentTool> {
  private final Provider<StopwatchTool> tProvider;

  private AgentModule_BindStopwatchFactory(Provider<StopwatchTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindStopwatch(tProvider.get());
  }

  public static AgentModule_BindStopwatchFactory create(Provider<StopwatchTool> tProvider) {
    return new AgentModule_BindStopwatchFactory(tProvider);
  }

  public static AgentTool bindStopwatch(StopwatchTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindStopwatch(t));
  }
}
