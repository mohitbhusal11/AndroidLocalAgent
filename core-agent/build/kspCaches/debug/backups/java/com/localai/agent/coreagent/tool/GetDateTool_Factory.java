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
public final class GetDateTool_Factory implements Factory<GetDateTool> {
  @Override
  public GetDateTool get() {
    return newInstance();
  }

  public static GetDateTool_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GetDateTool newInstance() {
    return new GetDateTool();
  }

  private static final class InstanceHolder {
    static final GetDateTool_Factory INSTANCE = new GetDateTool_Factory();
  }
}
