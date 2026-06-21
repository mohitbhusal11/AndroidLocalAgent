package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.GetTimeTool;
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
public final class AgentModule_BindGetTimeFactory implements Factory<AgentTool> {
  private final Provider<GetTimeTool> tProvider;

  private AgentModule_BindGetTimeFactory(Provider<GetTimeTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindGetTime(tProvider.get());
  }

  public static AgentModule_BindGetTimeFactory create(Provider<GetTimeTool> tProvider) {
    return new AgentModule_BindGetTimeFactory(tProvider);
  }

  public static AgentTool bindGetTime(GetTimeTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindGetTime(t));
  }
}
