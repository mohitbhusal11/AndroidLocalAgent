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
public final class OpenSettingsTool_Factory implements Factory<OpenSettingsTool> {
  private final Provider<Context> contextProvider;

  private OpenSettingsTool_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public OpenSettingsTool get() {
    return newInstance(contextProvider.get());
  }

  public static OpenSettingsTool_Factory create(Provider<Context> contextProvider) {
    return new OpenSettingsTool_Factory(contextProvider);
  }

  public static OpenSettingsTool newInstance(Context context) {
    return new OpenSettingsTool(context);
  }
}
