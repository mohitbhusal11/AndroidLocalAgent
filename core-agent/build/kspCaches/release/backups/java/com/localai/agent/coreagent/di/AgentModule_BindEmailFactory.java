package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.EmailTool;
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
public final class AgentModule_BindEmailFactory implements Factory<AgentTool> {
  private final Provider<EmailTool> tProvider;

  private AgentModule_BindEmailFactory(Provider<EmailTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindEmail(tProvider.get());
  }

  public static AgentModule_BindEmailFactory create(Provider<EmailTool> tProvider) {
    return new AgentModule_BindEmailFactory(tProvider);
  }

  public static AgentTool bindEmail(EmailTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindEmail(t));
  }
}
