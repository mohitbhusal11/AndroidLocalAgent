package com.localai.agent.featureaccessibility.service;

import com.localai.agent.core.bridge.AccessibilityController;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class AgentAccessibilityService_MembersInjector implements MembersInjector<AgentAccessibilityService> {
  private final Provider<AccessibilityController> accessibilityControllerProvider;

  private AgentAccessibilityService_MembersInjector(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    this.accessibilityControllerProvider = accessibilityControllerProvider;
  }

  @Override
  public void injectMembers(AgentAccessibilityService instance) {
    injectAccessibilityController(instance, accessibilityControllerProvider.get());
  }

  public static MembersInjector<AgentAccessibilityService> create(
      Provider<AccessibilityController> accessibilityControllerProvider) {
    return new AgentAccessibilityService_MembersInjector(accessibilityControllerProvider);
  }

  @InjectedFieldSignature("com.localai.agent.featureaccessibility.service.AgentAccessibilityService.accessibilityController")
  public static void injectAccessibilityController(AgentAccessibilityService instance,
      AccessibilityController accessibilityController) {
    instance.accessibilityController = accessibilityController;
  }
}
