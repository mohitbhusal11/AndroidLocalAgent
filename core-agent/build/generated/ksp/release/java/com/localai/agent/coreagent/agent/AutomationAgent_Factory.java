package com.localai.agent.coreagent.agent;

import com.localai.agent.coreagent.automation.AutomationEngine;
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
public final class AutomationAgent_Factory implements Factory<AutomationAgent> {
  private final Provider<AutomationEngine> automationEngineProvider;

  private AutomationAgent_Factory(Provider<AutomationEngine> automationEngineProvider) {
    this.automationEngineProvider = automationEngineProvider;
  }

  @Override
  public AutomationAgent get() {
    return newInstance(automationEngineProvider.get());
  }

  public static AutomationAgent_Factory create(
      Provider<AutomationEngine> automationEngineProvider) {
    return new AutomationAgent_Factory(automationEngineProvider);
  }

  public static AutomationAgent newInstance(AutomationEngine automationEngine) {
    return new AutomationAgent(automationEngine);
  }
}
