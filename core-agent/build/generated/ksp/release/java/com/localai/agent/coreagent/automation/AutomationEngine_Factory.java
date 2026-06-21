package com.localai.agent.coreagent.automation;

import com.localai.agent.core.bridge.AccessibilityController;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class AutomationEngine_Factory implements Factory<AutomationEngine> {
  private final Provider<AccessibilityController> accessibilityControllerProvider;

  private AutomationEngine_Factory(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    this.accessibilityControllerProvider = accessibilityControllerProvider;
  }

  @Override
  public AutomationEngine get() {
    return newInstance(accessibilityControllerProvider.get());
  }

  public static AutomationEngine_Factory create(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    return new AutomationEngine_Factory(accessibilityControllerProvider);
  }

  public static AutomationEngine newInstance(AccessibilityController accessibilityController) {
    return new AutomationEngine(accessibilityController);
  }
}
