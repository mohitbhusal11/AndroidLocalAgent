package com.localai.agent.coreai.engine;

import com.localai.agent.corenetwork.SettingsRepository;
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
public final class OnDeviceAiRepository_Factory implements Factory<OnDeviceAiRepository> {
  private final Provider<GeminiNanoEngine> geminiNanoEngineProvider;

  private final Provider<LocalAssistantEngine> localAssistantEngineProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private OnDeviceAiRepository_Factory(Provider<GeminiNanoEngine> geminiNanoEngineProvider,
      Provider<LocalAssistantEngine> localAssistantEngineProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.geminiNanoEngineProvider = geminiNanoEngineProvider;
    this.localAssistantEngineProvider = localAssistantEngineProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public OnDeviceAiRepository get() {
    return newInstance(geminiNanoEngineProvider.get(), localAssistantEngineProvider.get(), settingsRepositoryProvider.get());
  }

  public static OnDeviceAiRepository_Factory create(
      Provider<GeminiNanoEngine> geminiNanoEngineProvider,
      Provider<LocalAssistantEngine> localAssistantEngineProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new OnDeviceAiRepository_Factory(geminiNanoEngineProvider, localAssistantEngineProvider, settingsRepositoryProvider);
  }

  public static OnDeviceAiRepository newInstance(GeminiNanoEngine geminiNanoEngine,
      LocalAssistantEngine localAssistantEngine, SettingsRepository settingsRepository) {
    return new OnDeviceAiRepository(geminiNanoEngine, localAssistantEngine, settingsRepository);
  }
}
