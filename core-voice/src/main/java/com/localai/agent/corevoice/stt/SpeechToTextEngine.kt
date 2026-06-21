package com.localai.agent.corevoice.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class SpeechEvent {
    data class Partial(val text: String) : SpeechEvent()
    data class Final(val text: String) : SpeechEvent()
    data class Error(val message: String) : SpeechEvent()
    data object Ready : SpeechEvent()
    data object Listening : SpeechEvent()
}

@Singleton
class SpeechToTextEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var speechRecognizer: SpeechRecognizer? = null

    fun isAvailable(): Boolean = SpeechRecognizer.isRecognitionAvailable(context)

    fun listen(continuous: Boolean = false): Flow<SpeechEvent> = callbackFlow {
        if (!isAvailable()) {
            trySend(SpeechEvent.Error("Speech recognition not available"))
            close()
            return@callbackFlow
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) { trySend(SpeechEvent.Ready) }
                override fun onBeginningOfSpeech() { trySend(SpeechEvent.Listening) }
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(error: Int) {
                    trySend(SpeechEvent.Error(mapError(error)))
                }
                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val text = matches?.firstOrNull().orEmpty()
                    trySend(SpeechEvent.Final(text))
                    if (continuous && text.isNotBlank()) {
                        startListening(createIntent())
                    }
                }
                override fun onPartialResults(partialResults: Bundle?) {
                    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.firstOrNull()?.let { trySend(SpeechEvent.Partial(it)) }
                }
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        speechRecognizer?.startListening(createIntent())

        awaitClose {
            speechRecognizer?.destroy()
            speechRecognizer = null
        }
    }

    fun stop() {
        speechRecognizer?.stopListening()
    }

    private fun createIntent(): Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
    }

    private fun mapError(code: Int): String = when (code) {
        SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
        SpeechRecognizer.ERROR_CLIENT -> "Client error"
        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required"
        SpeechRecognizer.ERROR_NETWORK -> "Network error"
        SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected"
        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Speech timeout"
        else -> "Recognition error ($code)"
    }
}
