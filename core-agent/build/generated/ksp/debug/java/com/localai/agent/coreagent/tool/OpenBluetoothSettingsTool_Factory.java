package com.localai.agent.coreagent.tool;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class OpenBluetoothSettingsTool_Factory implements Factory<OpenBluetoothSettingsTool> {
  private final Provider<Context> contextProvider;

  private OpenBluetoothSettingsTool_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public OpenBluetoothSettingsTool get() {
    return newInstance(contextProvider.get());
  }

  public static OpenBluetoothSettingsTool_Factory create(Provider<Context> contextProvider) {
    return new OpenBluetoothSettingsTool_Factory(contextProvider);
  }

  public static OpenBluetoothSettingsTool newInstance(Context context) {
    return new OpenBluetoothSettingsTool(context);
  }
}
