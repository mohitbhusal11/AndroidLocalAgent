package com.localai.agent.coreagent.agent;

import com.localai.agent.coreai.research.ResearchRepository;
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
public final class ResearchAgent_Factory implements Factory<ResearchAgent> {
  private final Provider<ResearchRepository> researchRepositoryProvider;

  private ResearchAgent_Factory(Provider<ResearchRepository> researchRepositoryProvider) {
    this.researchRepositoryProvider = researchRepositoryProvider;
  }

  @Override
  public ResearchAgent get() {
    return newInstance(researchRepositoryProvider.get());
  }

  public static ResearchAgent_Factory create(
      Provider<ResearchRepository> researchRepositoryProvider) {
    return new ResearchAgent_Factory(researchRepositoryProvider);
  }

  public static ResearchAgent newInstance(ResearchRepository researchRepository) {
    return new ResearchAgent(researchRepository);
  }
}
