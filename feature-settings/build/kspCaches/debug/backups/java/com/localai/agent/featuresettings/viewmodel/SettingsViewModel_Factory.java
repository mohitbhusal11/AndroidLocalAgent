package com.localai.agent.featuresettings.viewmodel;

import com.localai.agent.coreai.engine.OnDeviceAiRepository;
import com.localai.agent.corenetwork.SettingsRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider;

  private SettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.onDeviceAiRepositoryProvider = onDeviceAiRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), onDeviceAiRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<OnDeviceAiRepository> onDeviceAiRepositoryProvider) {
    return new SettingsViewModel_Factory(settingsRepositoryProvider, onDeviceAiRepositoryProvider);
  }

  public static SettingsViewModel newInstance(SettingsRepository settingsRepository,
      OnDeviceAiRepository onDeviceAiRepository) {
    return new SettingsViewModel(settingsRepository, onDeviceAiRepository);
  }
}
