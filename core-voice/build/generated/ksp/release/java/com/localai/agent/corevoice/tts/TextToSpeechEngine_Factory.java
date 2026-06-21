package com.localai.agent.corevoice.tts;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class TextToSpeechEngine_Factory implements Factory<TextToSpeechEngine> {
  private final Provider<Context> contextProvider;

  private TextToSpeechEngine_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TextToSpeechEngine get() {
    return newInstance(contextProvider.get());
  }

  public static TextToSpeechEngine_Factory create(Provider<Context> contextProvider) {
    return new TextToSpeechEngine_Factory(contextProvider);
  }

  public static TextToSpeechEngine newInstance(Context context) {
    return new TextToSpeechEngine(context);
  }
}
