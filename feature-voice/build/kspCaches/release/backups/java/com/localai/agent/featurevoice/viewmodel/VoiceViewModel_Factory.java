package com.localai.agent.featurevoice.viewmodel;

import com.localai.agent.coreagent.agent.PlannerAgent;
import com.localai.agent.corevoice.stt.SpeechToTextEngine;
import com.localai.agent.corevoice.tts.TextToSpeechEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class VoiceViewModel_Factory implements Factory<VoiceViewModel> {
  private final Provider<SpeechToTextEngine> speechEngineProvider;

  private final Provider<TextToSpeechEngine> ttsEngineProvider;

  private final Provider<PlannerAgent> plannerAgentProvider;

  private VoiceViewModel_Factory(Provider<SpeechToTextEngine> speechEngineProvider,
      Provider<TextToSpeechEngine> ttsEngineProvider, Provider<PlannerAgent> plannerAgentProvider) {
    this.speechEngineProvider = speechEngineProvider;
    this.ttsEngineProvider = ttsEngineProvider;
    this.plannerAgentProvider = plannerAgentProvider;
  }

  @Override
  public VoiceViewModel get() {
    return newInstance(speechEngineProvider.get(), ttsEngineProvider.get(), plannerAgentProvider.get());
  }

  public static VoiceViewModel_Factory create(Provider<SpeechToTextEngine> speechEngineProvider,
      Provider<TextToSpeechEngine> ttsEngineProvider, Provider<PlannerAgent> plannerAgentProvider) {
    return new VoiceViewModel_Factory(speechEngineProvider, ttsEngineProvider, plannerAgentProvider);
  }

  public static VoiceViewModel newInstance(SpeechToTextEngine speechEngine,
      TextToSpeechEngine ttsEngine, PlannerAgent plannerAgent) {
    return new VoiceViewModel(speechEngine, ttsEngine, plannerAgent);
  }
}
