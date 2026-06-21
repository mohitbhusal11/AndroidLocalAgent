package com.localai.agent.featurenotifications.viewmodel;

import com.localai.agent.coredatabase.repository.NotificationRepository;
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
public final class NotificationsViewModel_Factory implements Factory<NotificationsViewModel> {
  private final Provider<NotificationRepository> notificationRepositoryProvider;

  private NotificationsViewModel_Factory(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public NotificationsViewModel get() {
    return newInstance(notificationRepositoryProvider.get());
  }

  public static NotificationsViewModel_Factory create(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new NotificationsViewModel_Factory(notificationRepositoryProvider);
  }

  public static NotificationsViewModel newInstance(NotificationRepository notificationRepository) {
    return new NotificationsViewModel(notificationRepository);
  }
}
