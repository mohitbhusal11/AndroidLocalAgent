package com.localai.agent.featureaccessibility.viewmodel;

import com.localai.agent.core.bridge.AccessibilityController;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AutomationViewModel_Factory implements Factory<AutomationViewModel> {
  private final Provider<AccessibilityController> accessibilityControllerProvider;

  private AutomationViewModel_Factory(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    this.accessibilityControllerProvider = accessibilityControllerProvider;
  }

  @Override
  public AutomationViewModel get() {
    return newInstance(accessibilityControllerProvider.get());
  }

  public static AutomationViewModel_Factory create(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    return new AutomationViewModel_Factory(accessibilityControllerProvider);
  }

  public static AutomationViewModel newInstance(AccessibilityController accessibilityController) {
    return new AutomationViewModel(accessibilityController);
  }
}
