package com.localai.agent.coreagent.agent;

import com.localai.agent.coreagent.tool.ToolRegistry;
import com.localai.agent.coreai.engine.OnDeviceAiRepository;
import com.localai.agent.corememory.routine.RoutineTracker;
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
public final class PlannerAgent_Factory implements Factory<PlannerAgent> {
  private final Provider<PhoneAgent> phoneAgentProvider;

  private final Provider<MemoryAgent> memoryAgentProvider;

  private final Provider<ResearchAgent> researchAgentProvider;

  private final Provider<NotificationAgent> notificationAgentProvider;

  private final Provider<AutomationAgent> automationAgentProvider;

  private final Provider<ProductivityAgent> productivityAgentProvider;

  private final Provider<ToolRegistry> toolRegistryProvider;

  private final Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider;

  private final Provider<RoutineTracker> routineTrackerProvider;

  private PlannerAgent_Factory(Provider<PhoneAgent> phoneAgentProvider,
      Provider<MemoryAgent> memoryAgentProvider, Provider<ResearchAgent> researchAgentProvider,
      Provider<NotificationAgent> notificationAgentProvider,
      Provider<AutomationAgent> automationAgentProvider,
      Provider<ProductivityAgent> productivityAgentProvider,
      Provider<ToolRegistry> toolRegistryProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider,
      Provider<RoutineTracker> routineTrackerProvider) {
    this.phoneAgentProvider = phoneAgentProvider;
    this.memoryAgentProvider = memoryAgentProvider;
    this.researchAgentProvider = researchAgentProvider;
    this.notificationAgentProvider = notificationAgentProvider;
    this.automationAgentProvider = automationAgentProvider;
    this.productivityAgentProvider = productivityAgentProvider;
    this.toolRegistryProvider = toolRegistryProvider;
    this.onDeviceAiRepositoryProvider = onDeviceAiRepositoryProvider;
    this.routineTrackerProvider = routineTrackerProvider;
  }

  @Override
  public PlannerAgent get() {
    return newInstance(phoneAgentProvider.get(), memoryAgentProvider.get(), researchAgentProvider.get(), notificationAgentProvider.get(), automationAgentProvider.get(), productivityAgentProvider.get(), toolRegistryProvider.get(), onDeviceAiRepositoryProvider.get(), routineTrackerProvider.get());
  }

  public static PlannerAgent_Factory create(Provider<PhoneAgent> phoneAgentProvider,
      Provider<MemoryAgent> memoryAgentProvider, Provider<ResearchAgent> researchAgentProvider,
      Provider<NotificationAgent> notificationAgentProvider,
      Provider<AutomationAgent> automationAgentProvider,
      Provider<ProductivityAgent> productivityAgentProvider,
      Provider<ToolRegistry> toolRegistryProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider,
      Provider<RoutineTracker> routineTrackerProvider) {
    return new PlannerAgent_Factory(phoneAgentProvider, memoryAgentProvider, researchAgentProvider, notificationAgentProvider, automationAgentProvider, productivityAgentProvider, toolRegistryProvider, onDeviceAiRepositoryProvider, routineTrackerProvider);
  }

  public static PlannerAgent newInstance(PhoneAgent phoneAgent, MemoryAgent memoryAgent,
      ResearchAgent researchAgent, NotificationAgent notificationAgent,
      AutomationAgent automationAgent, ProductivityAgent productivityAgent,
      ToolRegistry toolRegistry, OnDeviceAiRepository onDeviceAiRepository,
      RoutineTracker routineTracker) {
    return new PlannerAgent(phoneAgent, memoryAgent, researchAgent, notificationAgent, automationAgent, productivityAgent, toolRegistry, onDeviceAiRepository, routineTracker);
  }
}
