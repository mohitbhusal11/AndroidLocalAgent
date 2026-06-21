package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.AlarmTool;
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
public final class AgentModule_BindAlarmFactory implements Factory<AgentTool> {
  private final Provider<AlarmTool> tProvider;

  private AgentModule_BindAlarmFactory(Provider<AlarmTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindAlarm(tProvider.get());
  }

  public static AgentModule_BindAlarmFactory create(Provider<AlarmTool> tProvider) {
    return new AgentModule_BindAlarmFactory(tProvider);
  }

  public static AgentTool bindAlarm(AlarmTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindAlarm(t));
  }
}
