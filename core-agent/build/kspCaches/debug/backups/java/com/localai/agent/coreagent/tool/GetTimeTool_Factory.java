package com.localai.agent.coreagent.tool;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class GetTimeTool_Factory implements Factory<GetTimeTool> {
  @Override
  public GetTimeTool get() {
    return newInstance();
  }

  public static GetTimeTool_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GetTimeTool newInstance() {
    return new GetTimeTool();
  }

  private static final class InstanceHolder {
    static final GetTimeTool_Factory INSTANCE = new GetTimeTool_Factory();
  }
}
