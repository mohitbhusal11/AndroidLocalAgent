package com.localai.agent.coreagent.di;

import com.localai.agent.coreagent.tool.AgentTool;
import com.localai.agent.coreagent.tool.CalendarTool;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AgentModule_BindCalendarFactory implements Factory<AgentTool> {
  private final Provider<CalendarTool> tProvider;

  private AgentModule_BindCalendarFactory(Provider<CalendarTool> tProvider) {
    this.tProvider = tProvider;
  }

  @Override
  public AgentTool get() {
    return bindCalendar(tProvider.get());
  }

  public static AgentModule_BindCalendarFactory create(Provider<CalendarTool> tProvider) {
    return new AgentModule_BindCalendarFactory(tProvider);
  }

  public static AgentTool bindCalendar(CalendarTool t) {
    return Preconditions.checkNotNullFromProvides(AgentModule.INSTANCE.bindCalendar(t));
  }
}
