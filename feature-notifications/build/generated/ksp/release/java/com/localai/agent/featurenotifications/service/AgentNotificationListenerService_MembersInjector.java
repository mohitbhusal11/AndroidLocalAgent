package com.localai.agent.featurenotifications.service;

import com.localai.agent.coredatabase.repository.NotificationRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class AgentNotificationListenerService_MembersInjector implements MembersInjector<AgentNotificationListenerService> {
  private final Provider<NotificationRepository> notificationRepositoryProvider;

  private AgentNotificationListenerService_MembersInjector(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public void injectMembers(AgentNotificationListenerService instance) {
    injectNotificationRepository(instance, notificationRepositoryProvider.get());
  }

  public static MembersInjector<AgentNotificationListenerService> create(
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new AgentNotificationListenerService_MembersInjector(notificationRepositoryProvider);
  }

  @InjectedFieldSignature("com.localai.agent.featurenotifications.service.AgentNotificationListenerService.notificationRepository")
  public static void injectNotificationRepository(AgentNotificationListenerService instance,
      NotificationRepository notificationRepository) {
    instance.notificationRepository = notificationRepository;
  }
}
