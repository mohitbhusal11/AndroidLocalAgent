package com.localai.agent.coreagent.tool;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Set;
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
public final class ToolRegistry_Factory implements Factory<ToolRegistry> {
  private final Provider<Set<AgentTool>> toolsProvider;

  private ToolRegistry_Factory(Provider<Set<AgentTool>> toolsProvider) {
    this.toolsProvider = toolsProvider;
  }

  @Override
  public ToolRegistry get() {
    return newInstance(toolsProvider.get());
  }

  public static ToolRegistry_Factory create(Provider<Set<AgentTool>> toolsProvider) {
    return new ToolRegistry_Factory(toolsProvider);
  }

  public static ToolRegistry newInstance(Set<AgentTool> tools) {
    return new ToolRegistry(tools);
  }
}
