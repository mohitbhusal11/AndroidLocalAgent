package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.GetLocationTool;
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
public final class AgentModule_BindGetLocationFactory implements Factory<AgentTool> {
  private final Provider<GetLocationTool> tProvider;

  private AgentModule_BindGetLocationFactory(Provider<GetLocationTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindGetLocation(tProvider.get());
  }

  public static AgentModule_BindGetLocationFactory create(Provider<GetLocationTool> tProvider) {
    return new AgentModule_BindGetLocationFactory(tProvider);
  }

  public static AgentTool bindGetLocation(GetLocationTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindGetLocation(t));
  }
}
