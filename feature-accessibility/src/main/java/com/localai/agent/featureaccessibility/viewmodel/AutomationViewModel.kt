package com.localai.agent.featureaccessibility.viewmodel

import androidx.lifecycle.ViewModel
import com.localai.agent.core.bridge.AccessibilityController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutomationViewModel @Inject constructor(
    val accessibilityController: AccessibilityController
) : ViewModel()
