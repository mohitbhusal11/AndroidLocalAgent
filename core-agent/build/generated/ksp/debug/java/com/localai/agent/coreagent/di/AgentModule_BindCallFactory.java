package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.CallTool;
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
public final class AgentModule_BindCallFactory implements Factory<AgentTool> {
  private final Provider<CallTool> tProvider;

  private AgentModule_BindCallFactory(Provider<CallTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindCall(tProvider.get());
  }

  public static AgentModule_BindCallFactory create(Provider<CallTool> tProvider) {
    return new AgentModule_BindCallFactory(tProvider);
  }

  public static AgentTool bindCall(CallTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindCall(t));
  }
}
