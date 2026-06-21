package com.localai.agent.core.bridge;

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
public final class AccessibilityController_Factory implements Factory<AccessibilityController> {
  @Override
  public AccessibilityController get() {
    return newInstance();
  }

  public static AccessibilityController_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AccessibilityController newInstance() {
    return new AccessibilityController();
  }

  private static final class InstanceHolder {
    static final AccessibilityController_Factory INSTANCE = new AccessibilityController_Factory();
  }
}
