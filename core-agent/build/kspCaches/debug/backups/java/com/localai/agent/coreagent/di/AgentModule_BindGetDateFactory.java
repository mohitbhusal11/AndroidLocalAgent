package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.GetDateTool;
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
public final class AgentModule_BindGetDateFactory implements Factory<AgentTool> {
  private final Provider<GetDateTool> tProvider;

  private AgentModule_BindGetDateFactory(Provider<GetDateTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindGetDate(tProvider.get());
  }

  public static AgentModule_BindGetDateFactory create(Provider<GetDateTool> tProvider) {
    return new AgentModule_BindGetDateFactory(tProvider);
  }

  public static AgentTool bindGetDate(GetDateTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindGetDate(t));
  }
}
