package com.localai.agent.corevoice.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var tts: TextToSpeech? = null
    private var isReady = false

    fun initialize(onReady: (Boolean) -> Unit = {}) {
        if (tts != null) {
            onReady(isReady)
            return
        }
        tts = TextToSpeech(context) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.language = Locale.getDefault()
            }
            onReady(isReady)
        }
    }

    fun speak(text: String): Flow<TtsEvent> = callbackFlow {
        initialize { ready ->
            if (!ready) {
                trySend(TtsEvent.Error("TTS not available"))
                close()
                return@initialize
            }
            val utteranceId = UUID.randomUUID().toString()
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String?) {
                    if (id == utteranceId) trySend(TtsEvent.Started)
                }
                override fun onDone(id: String?) {
                    if (id == utteranceId) {
                        trySend(TtsEvent.Completed)
                        close()
                    }
                }
                override fun onError(id: String?) {
                    if (id == utteranceId) {
                        trySend(TtsEvent.Error("TTS error"))
                        close()
                    }
                }
            })
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        }
        awaitClose { tts?.stop() }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.shutdown()
        tts = null
        isReady = false
    }
}

sealed class TtsEvent {
    data object Started : TtsEvent()
    data object Completed : TtsEvent()
    data class Error(val message: String) : TtsEvent()
}
