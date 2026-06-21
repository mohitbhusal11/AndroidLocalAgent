package com.localai.agent.featureproductivity.viewmodel;

import com.localai.agent.coredatabase.dao.TaskDao;
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
public final class ProductivityViewModel_Factory implements Factory<ProductivityViewModel> {
  private final Provider<TaskDao> taskDaoProvider;

  private ProductivityViewModel_Factory(Provider<TaskDao> taskDaoProvider) {
    this.taskDaoProvider = taskDaoProvider;
  }

  @Override
  public ProductivityViewModel get() {
    return newInstance(taskDaoProvider.get());
  }

  public static ProductivityViewModel_Factory create(Provider<TaskDao> taskDaoProvider) {
    return new ProductivityViewModel_Factory(taskDaoProvider);
  }

  public static ProductivityViewModel newInstance(TaskDao taskDao) {
    return new ProductivityViewModel(taskDao);
  }
}
