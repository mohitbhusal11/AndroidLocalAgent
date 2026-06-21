package com.localai.agent.featurevoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.agent.coreagent.agent.PlannerAgent
import com.localai.agent.corevoice.stt.SpeechEvent
import com.localai.agent.corevoice.stt.SpeechToTextEngine
import com.localai.agent.corevoice.tts.TextToSpeechEngine
import com.localai.agent.corevoice.tts.TtsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VoiceUiState(
    val isListening: Boolean = false,
    val partialText: String = "",
    val lastCommand: String = "",
    val response: String = "",
    val continuousMode: Boolean = false,
    val voiceFirstMode: Boolean = false,
    val error: String? = null,
    val isSpeaking: Boolean = false
)

@HiltViewModel
class VoiceViewModel @Inject constructor(
    private val speechEngine: SpeechToTextEngine,
    private val ttsEngine: TextToSpeechEngine,
    private val plannerAgent: PlannerAgent
) : ViewModel() {

    private val _uiState = MutableStateFlow(VoiceUiState())
    val uiState: StateFlow<VoiceUiState> = _uiState.asStateFlow()

    init {
        ttsEngine.initialize()
    }

    fun toggleVoiceFirstMode() {
        val enabled = !_uiState.value.voiceFirstMode
        _uiState.update { it.copy(voiceFirstMode = enabled, continuousMode = enabled) }
        if (enabled) startListening()
        else stopListening()
    }

    fun toggleContinuousMode() {
        _uiState.update { it.copy(continuousMode = !it.continuousMode) }
    }

    fun startListening() {
        if (_uiState.value.isListening) return
        _uiState.update { it.copy(isListening = true, partialText = "", error = null) }

        viewModelScope.launch {
            speechEngine.listen(_uiState.value.continuousMode).collect { event ->
                when (event) {
                    is SpeechEvent.Partial -> {
                        _uiState.update { it.copy(partialText = event.text) }
                        if (_uiState.value.voiceFirstMode) {
                            val lower = event.text.lowercase()
                            if (lower.contains("hey assistant")) {
                                val cmd = lower.substringAfter("hey assistant").trim()
                                if (cmd.length > 3) processVoiceCommand(cmd.replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                    is SpeechEvent.Final -> processVoiceCommand(event.text)
                    is SpeechEvent.Error -> _uiState.update {
                        it.copy(error = event.message, isListening = false)
                    }
                    is SpeechEvent.Listening -> {}
                    is SpeechEvent.Ready -> {}
                }
            }
        }
    }

    fun stopListening() {
        speechEngine.stop()
        _uiState.update { it.copy(isListening = false) }
    }

    private fun processVoiceCommand(text: String) {
        if (text.isBlank()) {
            _uiState.update { it.copy(isListening = false) }
            return
        }
        _uiState.update { it.copy(lastCommand = text, isListening = false) }

        viewModelScope.launch {
            val plannerResult = plannerAgent.planAndExecute(text)
            val response = plannerResult.summary
            _uiState.update { it.copy(response = response) }
            speakResponse(response)
        }
    }

    private fun speakResponse(text: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSpeaking = true) }
            ttsEngine.speak(text.replace(Regex("[*_#`]"), "")).collect { event ->
                when (event) {
                    is TtsEvent.Completed, is TtsEvent.Error -> {
                        _uiState.update { it.copy(isSpeaking = false) }
                        if (_uiState.value.continuousMode || _uiState.value.voiceFirstMode) startListening()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onCleared() {
        ttsEngine.shutdown()
        super.onCleared()
    }
}
