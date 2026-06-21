package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenWifiSettingsTool;
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
public final class AgentModule_BindOpenWifiFactory implements Factory<AgentTool> {
  private final Provider<OpenWifiSettingsTool> tProvider;

  private AgentModule_BindOpenWifiFactory(Provider<OpenWifiSettingsTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenWifi(tProvider.get());
  }

  public static AgentModule_BindOpenWifiFactory create(Provider<OpenWifiSettingsTool> tProvider) {
    return new AgentModule_BindOpenWifiFactory(tProvider);
  }

  public static AgentTool bindOpenWifi(OpenWifiSettingsTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenWifi(t));
  }
}
