package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.CallContactTool;
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
public final class AgentModule_BindCallContactFactory implements Factory<AgentTool> {
  private final Provider<CallContactTool> tProvider;

  private AgentModule_BindCallContactFactory(Provider<CallContactTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindCallContact(tProvider.get());
  }

  public static AgentModule_BindCallContactFactory create(Provider<CallContactTool> tProvider) {
    return new AgentModule_BindCallContactFactory(tProvider);
  }

  public static AgentTool bindCallContact(CallContactTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindCallContact(t));
  }
}
