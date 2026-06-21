package com.localai.agent.featuredoomscroll.tracker;

import android.content.Context;
import com.localai.agent.coredatabase.dao.AppUsageDao;
import com.localai.agent.coredatabase.dao.FocusLimitDao;
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
public final class UsageTracker_Factory implements Factory<UsageTracker> {
  private final Provider<Context> contextProvider;

  private final Provider<AppUsageDao> appUsageDaoProvider;

  private final Provider<FocusLimitDao> focusLimitDaoProvider;

  private UsageTracker_Factory(Provider<Context> contextProvider,
      Provider<AppUsageDao> appUsageDaoProvider, Provider<FocusLimitDao> focusLimitDaoProvider) {
    this.contextProvider = contextProvider;
    this.appUsageDaoProvider = appUsageDaoProvider;
    this.focusLimitDaoProvider = focusLimitDaoProvider;
  }

  @Override
  public UsageTracker get() {
    return newInstance(contextProvider.get(), appUsageDaoProvider.get(), focusLimitDaoProvider.get());
  }

  public static UsageTracker_Factory create(Provider<Context> contextProvider,
      Provider<AppUsageDao> appUsageDaoProvider, Provider<FocusLimitDao> focusLimitDaoProvider) {
    return new UsageTracker_Factory(contextProvider, appUsageDaoProvider, focusLimitDaoProvider);
  }

  public static UsageTracker newInstance(Context context, AppUsageDao appUsageDao,
      FocusLimitDao focusLimitDao) {
    return new UsageTracker(context, appUsageDao, focusLimitDao);
  }
}
