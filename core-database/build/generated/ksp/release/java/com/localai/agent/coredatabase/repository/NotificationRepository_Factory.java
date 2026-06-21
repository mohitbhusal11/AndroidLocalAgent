package com.localai.agent.coredatabase.repository;

import com.localai.agent.coredatabase.dao.NotificationDao;
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
public final class NotificationRepository_Factory implements Factory<NotificationRepository> {
  private final Provider<NotificationDao> notificationDaoProvider;

  private NotificationRepository_Factory(Provider<NotificationDao> notificationDaoProvider) {
    this.notificationDaoProvider = notificationDaoProvider;
  }

  @Override
  public NotificationRepository get() {
    return newInstance(notificationDaoProvider.get());
  }

  public static NotificationRepository_Factory create(
      Provider<NotificationDao> notificationDaoProvider) {
    return new NotificationRepository_Factory(notificationDaoProvider);
  }

  public static NotificationRepository newInstance(NotificationDao notificationDao) {
    return new NotificationRepository(notificationDao);
  }
}
