package com.example.playback

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import androidx.media3.common.C
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Manages the real Android hardware Equalizer and BassBoost AudioEffects
 * attached directly to ExoPlayer's audio session ID.
 */
object AudioEffectsManager {
    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var currentAudioSessionId: Int = C.AUDIO_SESSION_ID_UNSET

    private var masterEnabled: Boolean = true
    private var bandLevels: List<Float> = List(10) { 0f }
    private var preampDb: Float = 0f
    private var subBassBoostDb: Float = 3.5f

    @Synchronized
    fun attachAudioSession(audioSessionId: Int) {
        if (audioSessionId == C.AUDIO_SESSION_ID_UNSET || audioSessionId == 0) return
        if (currentAudioSessionId == audioSessionId && equalizer != null) return

        currentAudioSessionId = audioSessionId
        releaseEffects()

        try {
            equalizer = Equalizer(0, audioSessionId).apply {
                enabled = masterEnabled
            }
            applyEqualizerSettings()
        } catch (_: Exception) {
            equalizer = null
        }

        try {
            bassBoost = BassBoost(0, audioSessionId).apply {
                enabled = masterEnabled && (subBassBoostDb > 0.1f)
            }
            applyBassBoostSettings()
        } catch (_: Exception) {
            bassBoost = null
        }
    }

    @Synchronized
    fun setMasterEnabled(enabled: Boolean) {
        masterEnabled = enabled
        try {
            equalizer?.enabled = enabled
            bassBoost?.enabled = enabled && (subBassBoostDb > 0.1f)
        } catch (_: Exception) {}
    }

    @Synchronized
    fun setBands(bands: List<Float>, preamp: Float) {
        bandLevels = bands
        preampDb = preamp
        applyEqualizerSettings()
    }

    @Synchronized
    fun setSubBassBoost(db: Float) {
        subBassBoostDb = db
        applyBassBoostSettings()
    }

    private fun applyEqualizerSettings() {
        val eq = equalizer ?: return
        try {
            eq.enabled = masterEnabled
            val numBands = eq.numberOfBands.toInt()
            val range = eq.bandLevelRange
            val minMb = range[0].toInt()
            val maxMb = range[1].toInt()

            val targetFreqs = listOf(31, 62, 125, 250, 500, 1000, 2000, 4000, 8000, 16000)
            for (i in 0 until numBands) {
                val hwCenterFreqHz = eq.getCenterFreq(i.toShort()) / 1000

                var closestIdx = 0
                var minDiff = Int.MAX_VALUE
                for ((idx, f) in targetFreqs.withIndex()) {
                    val diff = abs(f - hwCenterFreqHz)
                    if (diff < minDiff) {
                        minDiff = diff
                        closestIdx = idx
                    }
                }

                val dbVal = (bandLevels.getOrElse(closestIdx) { 0f } + preampDb)
                val millibels = (dbVal * 100).roundToInt().coerceIn(minMb, maxMb).toShort()
                eq.setBandLevel(i.toShort(), millibels)
            }
        } catch (_: Exception) {}
    }

    private fun applyBassBoostSettings() {
        val bb = bassBoost ?: return
        try {
            bb.enabled = masterEnabled && (subBassBoostDb > 0.1f)
            if (bb.strengthSupported) {
                val strength = ((subBassBoostDb / 10f) * 1000).roundToInt().coerceIn(0, 1000).toShort()
                bb.setStrength(strength)
            }
        } catch (_: Exception) {}
    }

    @Synchronized
    fun releaseEffects() {
        try {
            equalizer?.release()
        } catch (_: Exception) {}
        equalizer = null
        try {
            bassBoost?.release()
        } catch (_: Exception) {}
        bassBoost = null
    }
}
