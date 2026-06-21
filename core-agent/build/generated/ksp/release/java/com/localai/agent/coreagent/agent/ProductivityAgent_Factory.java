package com.localai.agent.coreagent.agent;

import com.localai.agent.coreagent.tool.ToolRegistry;
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
public final class ProductivityAgent_Factory implements Factory<ProductivityAgent> {
  private final Provider<ToolRegistry> toolRegistryProvider;

  private ProductivityAgent_Factory(Provider<ToolRegistry> toolRegistryProvider) {
    this.toolRegistryProvider = toolRegistryProvider;
  }

  @Override
  public ProductivityAgent get() {
    return newInstance(toolRegistryProvider.get());
  }

  public static ProductivityAgent_Factory create(Provider<ToolRegistry> toolRegistryProvider) {
    return new ProductivityAgent_Factory(toolRegistryProvider);
  }

  public static ProductivityAgent newInstance(ToolRegistry toolRegistry) {
    return new ProductivityAgent(toolRegistry);
  }
}
