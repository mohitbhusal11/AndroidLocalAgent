package com.localai.agent.coreui.layout

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable

/**
 * Use on child [androidx.compose.material3.Scaffold]s that sit above a parent bottom nav bar.
 * Avoids double bottom padding from system navigation bar insets.
 */
@Composable
fun TabScaffoldInsets(): WindowInsets = WindowInsets.statusBars
