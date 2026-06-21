package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.OpenCameraTool;
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
public final class AgentModule_BindOpenCameraFactory implements Factory<AgentTool> {
  private final Provider<OpenCameraTool> tProvider;

  private AgentModule_BindOpenCameraFactory(Provider<OpenCameraTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindOpenCamera(tProvider.get());
  }

  public static AgentModule_BindOpenCameraFactory create(Provider<OpenCameraTool> tProvider) {
    return new AgentModule_BindOpenCameraFactory(tProvider);
  }

  public static AgentTool bindOpenCamera(OpenCameraTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindOpenCamera(t));
  }
}
