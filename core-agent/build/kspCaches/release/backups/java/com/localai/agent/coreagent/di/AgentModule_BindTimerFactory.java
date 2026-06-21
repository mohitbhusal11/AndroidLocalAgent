package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.TimerTool;
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
public final class AgentModule_BindTimerFactory implements Factory<AgentTool> {
  private final Provider<TimerTool> tProvider;

  private AgentModule_BindTimerFactory(Provider<TimerTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindTimer(tProvider.get());
  }

  public static AgentModule_BindTimerFactory create(Provider<TimerTool> tProvider) {
    return new AgentModule_BindTimerFactory(tProvider);
  }

  public static AgentTool bindTimer(TimerTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindTimer(t));
  }
}
