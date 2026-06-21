package com.localai.agent.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.localai.agent.coreui.navigation.AgentRoutes
import com.localai.agent.dashboard.ToolsDashboardScreen
import com.localai.agent.featureaccessibility.ui.AutomationScreen
import com.localai.agent.featurechat.ui.ChatScreen
import com.localai.agent.featurechat.ui.ConversationsScreen
import com.localai.agent.featurechat.viewmodel.ConversationsViewModel
import com.localai.agent.featuredoomscroll.ui.FocusScreen
import com.localai.agent.featurememory.ui.MemoryScreen
import com.localai.agent.featurenotifications.ui.NotificationsScreen
import com.localai.agent.featureproductivity.ui.ProductivityScreen
import com.localai.agent.featureresearch.ui.ResearchScreen
import com.localai.agent.featuresettings.ui.SettingsScreen
import com.localai.agent.featurevoice.ui.VoiceScreen
import com.localai.agent.suggestions.SuggestionsScreen
import kotlinx.coroutines.launch

@Composable
fun AgentNavHost(
    startDestination: String = AgentRoutes.CONVERSATIONS,
    initialAction: String? = null
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/")
    val scope = rememberCoroutineScope()
    val conversationsViewModel: ConversationsViewModel = hiltViewModel()

    val bottomRoutes = setOf(
        AgentRoutes.CONVERSATIONS,
        AgentRoutes.VOICE,
        AgentRoutes.TOOLS,
        AgentRoutes.SETTINGS
    )

    androidx.compose.runtime.LaunchedEffect(initialAction) {
        when (initialAction) {
            com.localai.agent.core.util.Constants.ACTION_VOICE ->
                navController.navigate(AgentRoutes.VOICE)
            com.localai.agent.core.util.Constants.ACTION_CHAT -> {
                val id = conversationsViewModel.createConversation()
                navController.navigate(AgentRoutes.chat(id))
            }
            com.localai.agent.core.util.Constants.ACTION_NOTES ->
                navController.navigate(AgentRoutes.MEMORY)
            com.localai.agent.core.util.Constants.ACTION_OPEN_ASSISTANT ->
                navController.navigate(AgentRoutes.CONVERSATIONS)
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == AgentRoutes.CONVERSATIONS,
                        onClick = {
                            navController.navigate(AgentRoutes.CONVERSATIONS) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                        label = { Text("Chat") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == AgentRoutes.VOICE,
                        onClick = {
                            navController.navigate(AgentRoutes.VOICE) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Mic, contentDescription = null) },
                        label = { Text("Voice") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == AgentRoutes.TOOLS,
                        onClick = {
                            navController.navigate(AgentRoutes.TOOLS) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Build, contentDescription = null) },
                        label = { Text("Tools") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == AgentRoutes.SETTINGS,
                        onClick = {
                            navController.navigate(AgentRoutes.SETTINGS) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Settings") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            composable(AgentRoutes.CONVERSATIONS) {
                ConversationsScreen(
                    onConversationClick = { id -> navController.navigate(AgentRoutes.chat(id)) },
                    onNewChat = {
                        scope.launch {
                            val id = conversationsViewModel.createConversation()
                            navController.navigate(AgentRoutes.chat(id))
                        }
                    }
                )
            }
            composable(
                route = AgentRoutes.CHAT,
                arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments?.getLong("conversationId") ?: return@composable
                ChatScreen(conversationId = id, onBack = { navController.popBackStack() })
            }
            composable(AgentRoutes.VOICE) { VoiceScreen() }
            composable(AgentRoutes.TOOLS) {
                ToolsDashboardScreen { route ->
                    navController.navigate(
                        when (route) {
                            "memory" -> AgentRoutes.MEMORY
                            "notifications" -> AgentRoutes.NOTIFICATIONS
                            "focus" -> AgentRoutes.FOCUS
                            "productivity" -> AgentRoutes.PRODUCTIVITY
                            "research" -> AgentRoutes.RESEARCH
                            "automation" -> AgentRoutes.AUTOMATION
                            "suggestions" -> AgentRoutes.SUGGESTIONS
                            "settings" -> AgentRoutes.SETTINGS
                            else -> AgentRoutes.TOOLS
                        }
                    )
                }
            }
            composable(AgentRoutes.MEMORY) { MemoryScreen() }
            composable(AgentRoutes.NOTIFICATIONS) { NotificationsScreen() }
            composable(AgentRoutes.FOCUS) { FocusScreen() }
            composable(AgentRoutes.PRODUCTIVITY) { ProductivityScreen() }
            composable(AgentRoutes.RESEARCH) { ResearchScreen() }
            composable(AgentRoutes.AUTOMATION) { AutomationScreen() }
            composable(AgentRoutes.SUGGESTIONS) { SuggestionsScreen() }
            composable(AgentRoutes.SETTINGS) { SettingsScreen() }
        }
    }
}
