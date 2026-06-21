package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenBrowserTool;
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
public final class AgentModule_BindOpenBrowserFactory implements Factory<AgentTool> {
  private final Provider<OpenBrowserTool> tProvider;

  private AgentModule_BindOpenBrowserFactory(Provider<OpenBrowserTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenBrowser(tProvider.get());
  }

  public static AgentModule_BindOpenBrowserFactory create(Provider<OpenBrowserTool> tProvider) {
    return new AgentModule_BindOpenBrowserFactory(tProvider);
  }

  public static AgentTool bindOpenBrowser(OpenBrowserTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenBrowser(t));
  }
}
