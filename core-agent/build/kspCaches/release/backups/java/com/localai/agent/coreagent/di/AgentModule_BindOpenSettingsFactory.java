package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenSettingsTool;
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
public final class AgentModule_BindOpenSettingsFactory implements Factory<AgentTool> {
  private final Provider<OpenSettingsTool> tProvider;

  private AgentModule_BindOpenSettingsFactory(Provider<OpenSettingsTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenSettings(tProvider.get());
  }

  public static AgentModule_BindOpenSettingsFactory create(Provider<OpenSettingsTool> tProvider) {
    return new AgentModule_BindOpenSettingsFactory(tProvider);
  }

  public static AgentTool bindOpenSettings(OpenSettingsTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenSettings(t));
  }
}
