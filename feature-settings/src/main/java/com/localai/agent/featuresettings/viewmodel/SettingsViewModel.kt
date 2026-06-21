package com.localai.agent.featuresettings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.coreai.engine.OnDeviceAiRepository
import com.localai.agent.coreai.engine.OnDeviceAiStatus
import com.localai.agent.corenetwork.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val onDeviceAiRepository: OnDeviceAiRepository
) : ViewModel() {

    val forceLocalAssistant: StateFlow<Boolean> = settingsRepository.forceLocalAssistant
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val darkMode: StateFlow<Boolean> = settingsRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private val _aiStatus = MutableStateFlow<OnDeviceAiStatus?>(null)
    val aiStatus: StateFlow<OnDeviceAiStatus?> = _aiStatus.asStateFlow()

    private val _downloadMessage = MutableStateFlow<String?>(null)
    val downloadMessage: StateFlow<String?> = _downloadMessage.asStateFlow()

    init {
        refreshAiStatus()
    }

    fun refreshAiStatus() {
        viewModelScope.launch {
            _aiStatus.value = onDeviceAiRepository.getStatus()
        }
    }

    fun setForceLocalAssistant(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setForceLocalAssistant(enabled) }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDarkMode(enabled) }
    }

    fun downloadGeminiNano() {
        viewModelScope.launch {
            onDeviceAiRepository.downloadGeminiNanoWithProgress().collect { message ->
                _downloadMessage.value = message
                if (message.contains("ready", ignoreCase = true)) {
                    refreshAiStatus()
                }
            }
        }
    }

    fun clearDownloadMessage() {
        _downloadMessage.value = null
    }
}
