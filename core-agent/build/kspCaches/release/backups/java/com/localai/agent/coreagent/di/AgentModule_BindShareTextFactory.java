package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.ShareTextTool;
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
public final class AgentModule_BindShareTextFactory implements Factory<AgentTool> {
  private final Provider<ShareTextTool> tProvider;

  private AgentModule_BindShareTextFactory(Provider<ShareTextTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindShareText(tProvider.get());
  }

  public static AgentModule_BindShareTextFactory create(Provider<ShareTextTool> tProvider) {
    return new AgentModule_BindShareTextFactory(tProvider);
  }

  public static AgentTool bindShareText(ShareTextTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindShareText(t));
  }
}
