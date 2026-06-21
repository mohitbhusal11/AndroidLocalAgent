package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenBluetoothSettingsTool;
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
public final class AgentModule_BindOpenBluetoothFactory implements Factory<AgentTool> {
  private final Provider<OpenBluetoothSettingsTool> tProvider;

  private AgentModule_BindOpenBluetoothFactory(Provider<OpenBluetoothSettingsTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenBluetooth(tProvider.get());
  }

  public static AgentModule_BindOpenBluetoothFactory create(
      Provider<OpenBluetoothSettingsTool> tProvider) {
    return new AgentModule_BindOpenBluetoothFactory(tProvider);
  }

  public static AgentTool bindOpenBluetooth(OpenBluetoothSettingsTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenBluetooth(t));
  }
}
