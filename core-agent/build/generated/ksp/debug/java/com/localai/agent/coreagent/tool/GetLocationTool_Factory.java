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
public final class GetLocationTool_Factory implements Factory<GetLocationTool> {
  private final Provider<Context> contextProvider;

  private GetLocationTool_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GetLocationTool get() {
    return newInstance(contextProvider.get());
  }

  public static GetLocationTool_Factory create(Provider<Context> contextProvider) {
    return new GetLocationTool_Factory(contextProvider);
  }

  public static GetLocationTool newInstance(Context context) {
    return new GetLocationTool(context);
  }
}
