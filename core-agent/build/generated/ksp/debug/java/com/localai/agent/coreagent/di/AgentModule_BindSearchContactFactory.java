package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.SearchContactTool;
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
public final class AgentModule_BindSearchContactFactory implements Factory<AgentTool> {
  private final Provider<SearchContactTool> tProvider;

  private AgentModule_BindSearchContactFactory(Provider<SearchContactTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindSearchContact(tProvider.get());
  }

  public static AgentModule_BindSearchContactFactory create(Provider<SearchContactTool> tProvider) {
    return new AgentModule_BindSearchContactFactory(tProvider);
  }

  public static AgentTool bindSearchContact(SearchContactTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindSearchContact(t));
  }
}
