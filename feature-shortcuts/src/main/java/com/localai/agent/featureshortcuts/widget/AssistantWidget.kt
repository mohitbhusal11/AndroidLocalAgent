package com.localai.agent.featureshortcuts.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.localai.agent.core.util.Constants
import com.localai.agent.featureshortcuts.R

class AssistantWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { WidgetContent() }
    }
}

@Composable
private fun WidgetContent() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(R.color.widget_primary))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Local AI",
            style = TextStyle(color = ColorProvider(R.color.widget_on_primary), fontSize = 16.sp),
            modifier = GlanceModifier.clickable(
                actionRunCallback<OpenAssistantAction>()
            )
        )
        Row(modifier = GlanceModifier.padding(top = 8.dp)) {
            Text(
                "Voice",
                modifier = GlanceModifier
                    .padding(4.dp)
                    .clickable(actionRunCallback<VoiceAction>()),
                style = TextStyle(color = ColorProvider(R.color.widget_on_primary), fontSize = 12.sp)
            )
            Text(
                "Chat",
                modifier = GlanceModifier
                    .padding(4.dp)
                    .clickable(actionRunCallback<ChatAction>()),
                style = TextStyle(color = ColorProvider(R.color.widget_on_primary), fontSize = 12.sp)
            )
        }
    }
}

private fun launchWithAction(context: Context, action: String) {
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
        this.action = action
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    } ?: return
    context.startActivity(intent)
}

class OpenAssistantAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        launchWithAction(context, Constants.ACTION_OPEN_ASSISTANT)
    }
}

class VoiceAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        launchWithAction(context, Constants.ACTION_VOICE)
    }
}

class ChatAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        launchWithAction(context, Constants.ACTION_CHAT)
    }
}

class AssistantWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AssistantWidget()
}
