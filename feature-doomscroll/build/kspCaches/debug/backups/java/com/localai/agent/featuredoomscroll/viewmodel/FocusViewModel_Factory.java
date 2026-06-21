package com.localai.agent.featuredoomscroll.viewmodel;

import com.localai.agent.featuredoomscroll.tracker.UsageTracker;
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
public final class FocusViewModel_Factory implements Factory<FocusViewModel> {
  private final Provider<UsageTracker> usageTrackerProvider;

  private FocusViewModel_Factory(Provider<UsageTracker> usageTrackerProvider) {
    this.usageTrackerProvider = usageTrackerProvider;
  }

  @Override
  public FocusViewModel get() {
    return newInstance(usageTrackerProvider.get());
  }

  public static FocusViewModel_Factory create(Provider<UsageTracker> usageTrackerProvider) {
    return new FocusViewModel_Factory(usageTrackerProvider);
  }

  public static FocusViewModel newInstance(UsageTracker usageTracker) {
    return new FocusViewModel(usageTracker);
  }
}
