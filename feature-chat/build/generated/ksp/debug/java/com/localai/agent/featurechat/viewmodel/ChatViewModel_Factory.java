package com.localai.agent.featurechat.viewmodel;

import com.localai.agent.coreagent.agent.PlannerAgent;
import com.localai.agent.featurechat.repository.ChatRepository;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<PlannerAgent> plannerAgentProvider;

  private ChatViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<PlannerAgent> plannerAgentProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.plannerAgentProvider = plannerAgentProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepositoryProvider.get(), plannerAgentProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<PlannerAgent> plannerAgentProvider) {
    return new ChatViewModel_Factory(chatRepositoryProvider, plannerAgentProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepository,
      PlannerAgent plannerAgent) {
    return new ChatViewModel(chatRepository, plannerAgent);
  }
}
