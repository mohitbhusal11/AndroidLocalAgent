package com.localai.agent.coreai.engine;

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
public final class GeminiNanoEngine_Factory implements Factory<GeminiNanoEngine> {
  @Override
  public GeminiNanoEngine get() {
    return newInstance();
  }

  public static GeminiNanoEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GeminiNanoEngine newInstance() {
    return new GeminiNanoEngine();
  }

  private static final class InstanceHolder {
    static final GeminiNanoEngine_Factory INSTANCE = new GeminiNanoEngine_Factory();
  }
}
