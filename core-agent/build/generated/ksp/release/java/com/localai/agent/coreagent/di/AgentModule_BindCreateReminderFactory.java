package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.CreateReminderTool;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AgentModule_BindCreateReminderFactory implements Factory<AgentTool> {
  private final Provider<CreateReminderTool> tProvider;

  private AgentModule_BindCreateReminderFactory(Provider<CreateReminderTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindCreateReminder(tProvider.get());
  }

  public static AgentModule_BindCreateReminderFactory create(
      Provider<CreateReminderTool> tProvider) {
    return new AgentModule_BindCreateReminderFactory(tProvider);
  }

  public static AgentTool bindCreateReminder(CreateReminderTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindCreateReminder(t));
  }
}
