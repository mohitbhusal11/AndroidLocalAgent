package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.ContactTool;
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
public final class AgentModule_BindContactFactory implements Factory<AgentTool> {
  private final Provider<ContactTool> tProvider;

  private AgentModule_BindContactFactory(Provider<ContactTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindContact(tProvider.get());
  }

  public static AgentModule_BindContactFactory create(Provider<ContactTool> tProvider) {
    return new AgentModule_BindContactFactory(tProvider);
  }

  public static AgentTool bindContact(ContactTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindContact(t));
  }
}
