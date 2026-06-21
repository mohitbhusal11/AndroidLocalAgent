package com.localai.agent.featuredoomscroll.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.featuredoomscroll.tracker.UsageTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val usageTracker: UsageTracker
) : ViewModel() {
    private val _report = MutableStateFlow<String?>(null)
    val report: StateFlow<String?> = _report

    private val _warning = MutableStateFlow<String?>(null)
    val warning: StateFlow<String?> = _warning

    init { refresh() }

    fun hasPermission() = usageTracker.hasUsagePermission()
    fun usageSettingsIntent() = usageTracker.usageSettingsIntent()

    fun refresh() {
        viewModelScope.launch {
            _report.value = usageTracker.getReport()
            _warning.value = usageTracker.checkDoomscroll()
        }
    }

    fun setYoutubeLimit(minutes: Int) {
        viewModelScope.launch {
            usageTracker.setLimit("com.google.android.youtube", minutes)
            refresh()
        }
    }
}
