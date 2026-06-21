package com.localai.agent.featureautomation.executor;

import com.localai.agent.coreagent.agent.PlannerAgent;
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
public final class AutomationExecutor_Factory implements Factory<AutomationExecutor> {
  private final Provider<PlannerAgent> plannerAgentProvider;

  private AutomationExecutor_Factory(Provider<PlannerAgent> plannerAgentProvider) {
    this.plannerAgentProvider = plannerAgentProvider;
  }

  @Override
  public AutomationExecutor get() {
    return newInstance(plannerAgentProvider.get());
  }

  public static AutomationExecutor_Factory create(Provider<PlannerAgent> plannerAgentProvider) {
    return new AutomationExecutor_Factory(plannerAgentProvider);
  }

  public static AutomationExecutor newInstance(PlannerAgent plannerAgent) {
    return new AutomationExecutor(plannerAgent);
  }
}
