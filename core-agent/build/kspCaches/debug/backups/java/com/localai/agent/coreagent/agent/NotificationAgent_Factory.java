package com.localai.agent.coreagent.agent;

import com.localai.agent.coredatabase.repository.NotificationRepository;
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
public final class NotificationAgent_Factory implements Factory<NotificationAgent> {
  private final Provider<NotificationRepository> notificationRepositoryProvider;

  private NotificationAgent_Factory(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public NotificationAgent get() {
    return newInstance(notificationRepositoryProvider.get());
  }

  public static NotificationAgent_Factory create(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new NotificationAgent_Factory(notificationRepositoryProvider);
  }

  public static NotificationAgent newInstance(NotificationRepository notificationRepository) {
    return new NotificationAgent(notificationRepository);
  }
}
