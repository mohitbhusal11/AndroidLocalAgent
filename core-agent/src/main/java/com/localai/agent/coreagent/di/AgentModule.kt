package com.localai.agent.coreagent.di

import com.localai.agent.coreagent.tool.AgentTool
import com.localai.agent.coreagent.tool.AlarmTool
import com.localai.agent.coreagent.tool.CalendarTool
import com.localai.agent.coreagent.tool.CallContactTool
import com.localai.agent.coreagent.tool.CallTool
import com.localai.agent.coreagent.tool.ContactTool
import com.localai.agent.coreagent.tool.CreateNoteTool
import com.localai.agent.coreagent.tool.CreateReminderTool
import com.localai.agent.coreagent.tool.EmailTool
import com.localai.agent.coreagent.tool.GetBatteryTool
import com.localai.agent.coreagent.tool.GetDateTool
import com.localai.agent.coreagent.tool.GetLocationTool
import com.localai.agent.coreagent.tool.GetTimeTool
import com.localai.agent.coreagent.tool.OpenAppTool
import com.localai.agent.coreagent.tool.OpenBluetoothSettingsTool
import com.localai.agent.coreagent.tool.OpenBrowserTool
import com.localai.agent.coreagent.tool.OpenCameraTool
import com.localai.agent.coreagent.tool.OpenDisplaySettingsTool
import com.localai.agent.coreagent.tool.OpenGalleryTool
import com.localai.agent.coreagent.tool.OpenSettingsTool
import com.localai.agent.coreagent.tool.OpenWifiSettingsTool
import com.localai.agent.coreagent.tool.SearchContactTool
import com.localai.agent.coreagent.tool.ShareTextTool
import com.localai.agent.coreagent.tool.SmsTool
import com.localai.agent.coreagent.tool.StopwatchTool
import com.localai.agent.coreagent.tool.TaskTool
import com.localai.agent.coreagent.tool.TimerTool
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object AgentModule {

    @Provides @IntoSet fun bindOpenApp(t: OpenAppTool): AgentTool = t
    @Provides @IntoSet fun bindOpenCamera(t: OpenCameraTool): AgentTool = t
    @Provides @IntoSet fun bindOpenGallery(t: OpenGalleryTool): AgentTool = t
    @Provides @IntoSet fun bindOpenBrowser(t: OpenBrowserTool): AgentTool = t
    @Provides @IntoSet fun bindSearchContact(t: SearchContactTool): AgentTool = t
    @Provides @IntoSet fun bindCallContact(t: CallContactTool): AgentTool = t
    @Provides @IntoSet fun bindCreateReminder(t: CreateReminderTool): AgentTool = t
    @Provides @IntoSet fun bindCreateNote(t: CreateNoteTool): AgentTool = t
    @Provides @IntoSet fun bindGetBattery(t: GetBatteryTool): AgentTool = t
    @Provides @IntoSet fun bindGetTime(t: GetTimeTool): AgentTool = t
    @Provides @IntoSet fun bindGetDate(t: GetDateTool): AgentTool = t
    @Provides @IntoSet fun bindGetLocation(t: GetLocationTool): AgentTool = t
    @Provides @IntoSet fun bindShareText(t: ShareTextTool): AgentTool = t
    @Provides @IntoSet fun bindOpenSettings(t: OpenSettingsTool): AgentTool = t
    @Provides @IntoSet fun bindOpenWifi(t: OpenWifiSettingsTool): AgentTool = t
    @Provides @IntoSet fun bindOpenBluetooth(t: OpenBluetoothSettingsTool): AgentTool = t
    @Provides @IntoSet fun bindOpenDisplay(t: OpenDisplaySettingsTool): AgentTool = t
    @Provides @IntoSet fun bindCalendar(t: CalendarTool): AgentTool = t
    @Provides @IntoSet fun bindTask(t: TaskTool): AgentTool = t
    @Provides @IntoSet fun bindAlarm(t: AlarmTool): AgentTool = t
    @Provides @IntoSet fun bindTimer(t: TimerTool): AgentTool = t
    @Provides @IntoSet fun bindStopwatch(t: StopwatchTool): AgentTool = t
    @Provides @IntoSet fun bindSms(t: SmsTool): AgentTool = t
    @Provides @IntoSet fun bindEmail(t: EmailTool): AgentTool = t
    @Provides @IntoSet fun bindContact(t: ContactTool): AgentTool = t
    @Provides @IntoSet fun bindCall(t: CallTool): AgentTool = t
}
