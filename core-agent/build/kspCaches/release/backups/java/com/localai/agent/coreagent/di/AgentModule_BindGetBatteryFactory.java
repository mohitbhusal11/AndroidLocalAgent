package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.GetBatteryTool;
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
public final class AgentModule_BindGetBatteryFactory implements Factory<AgentTool> {
  private final Provider<GetBatteryTool> tProvider;

  private AgentModule_BindGetBatteryFactory(Provider<GetBatteryTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindGetBattery(tProvider.get());
  }

  public static AgentModule_BindGetBatteryFactory create(Provider<GetBatteryTool> tProvider) {
    return new AgentModule_BindGetBatteryFactory(tProvider);
  }

  public static AgentTool bindGetBattery(GetBatteryTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindGetBattery(t));
  }
}
