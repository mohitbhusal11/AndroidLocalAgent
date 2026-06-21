package com.localai.agent.corevoice.stt;

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
public final class SpeechToTextEngine_Factory implements Factory<SpeechToTextEngine> {
  private final Provider<Context> contextProvider;

  private SpeechToTextEngine_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SpeechToTextEngine get() {
    return newInstance(contextProvider.get());
  }

  public static SpeechToTextEngine_Factory create(Provider<Context> contextProvider) {
    return new SpeechToTextEngine_Factory(contextProvider);
  }

  public static SpeechToTextEngine newInstance(Context context) {
    return new SpeechToTextEngine(context);
  }
}
