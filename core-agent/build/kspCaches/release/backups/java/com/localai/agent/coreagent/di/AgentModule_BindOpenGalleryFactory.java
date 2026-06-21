package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenGalleryTool;
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
public final class AgentModule_BindOpenGalleryFactory implements Factory<AgentTool> {
  private final Provider<OpenGalleryTool> tProvider;

  private AgentModule_BindOpenGalleryFactory(Provider<OpenGalleryTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenGallery(tProvider.get());
  }

  public static AgentModule_BindOpenGalleryFactory create(Provider<OpenGalleryTool> tProvider) {
    return new AgentModule_BindOpenGalleryFactory(tProvider);
  }

  public static AgentTool bindOpenGallery(OpenGalleryTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenGallery(t));
  }
}
