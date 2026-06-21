package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.CreateNoteTool;
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
public final class AgentModule_BindCreateNoteFactory implements Factory<AgentTool> {
  private final Provider<CreateNoteTool> tProvider;

  private AgentModule_BindCreateNoteFactory(Provider<CreateNoteTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindCreateNote(tProvider.get());
  }

  public static AgentModule_BindCreateNoteFactory create(Provider<CreateNoteTool> tProvider) {
    return new AgentModule_BindCreateNoteFactory(tProvider);
  }

  public static AgentTool bindCreateNote(CreateNoteTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindCreateNote(t));
  }
}
