package com.localai.agent.featureresearch.viewmodel;

import com.localai.agent.coreai.research.ResearchRepository;
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
public final class ResearchViewModel_Factory implements Factory<ResearchViewModel> {
  private final Provider<ResearchRepository> researchRepositoryProvider;

  private ResearchViewModel_Factory(Provider<ResearchRepository> researchRepositoryProvider) {
    this.researchRepositoryProvider = researchRepositoryProvider;
  }

  @Override
  public ResearchViewModel get() {
    return newInstance(researchRepositoryProvider.get());
  }

  public static ResearchViewModel_Factory create(
      Provider<ResearchRepository> researchRepositoryProvider) {
    return new ResearchViewModel_Factory(researchRepositoryProvider);
  }

  public static ResearchViewModel newInstance(ResearchRepository researchRepository) {
    return new ResearchViewModel(researchRepository);
  }
}
