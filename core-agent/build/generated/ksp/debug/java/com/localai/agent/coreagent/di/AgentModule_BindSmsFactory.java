package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.SmsTool;
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
public final class AgentModule_BindSmsFactory implements Factory<AgentTool> {
  private final Provider<SmsTool> tProvider;

  private AgentModule_BindSmsFactory(Provider<SmsTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindSms(tProvider.get());
  }

  public static AgentModule_BindSmsFactory create(Provider<SmsTool> tProvider) {
    return new AgentModule_BindSmsFactory(tProvider);
  }

  public static AgentTool bindSms(SmsTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindSms(t));
  }
}
