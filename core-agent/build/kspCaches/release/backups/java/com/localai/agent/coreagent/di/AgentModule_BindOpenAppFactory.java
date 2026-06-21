package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenAppTool;
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
public final class AgentModule_BindOpenAppFactory implements Factory<AgentTool> {
  private final Provider<OpenAppTool> tProvider;

  private AgentModule_BindOpenAppFactory(Provider<OpenAppTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenApp(tProvider.get());
  }

  public static AgentModule_BindOpenAppFactory create(Provider<OpenAppTool> tProvider) {
    return new AgentModule_BindOpenAppFactory(tProvider);
  }

  public static AgentTool bindOpenApp(OpenAppTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenApp(t));
  }
}
