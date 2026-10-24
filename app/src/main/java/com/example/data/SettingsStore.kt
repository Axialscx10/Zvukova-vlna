package com.example.data

import android.content.Context

import org.json.JSONArray
import org.json.JSONObject

/**
 * Saved server login. The password itself is never stored: Subsonic authentication only needs
 * the token = md5(password + salt) together with the salt, which is what we keep.
 */
data class SavedCredentials(
    val url: String,
    val username: String,
    val token: String,
    val salt: String
)

/** Small SharedPreferences wrapper (excluded from cloud backup, see res/xml/backup_rules.xml). */
class SettingsStore(context: Context) {
    private val prefs = context.applicationContext
        .getSharedPreferences("soundwave_prefs", Context.MODE_PRIVATE)

    fun loadCredentials(): SavedCredentials? {
        val url = prefs.getString(KEY_URL, null)
        val user = prefs.getString(KEY_USER, null)
        val token = prefs.getString(KEY_TOKEN, null)
        val salt = prefs.getString(KEY_SALT, null)
        if (url.isNullOrBlank() || user.isNullOrBlank() || token.isNullOrBlank() || salt.isNullOrBlank()) {
            return null
        }
        return SavedCredentials(url, user, token, salt)
    }

    fun saveCredentials(credentials: SavedCredentials) {
        prefs.edit()
            .putString(KEY_URL, credentials.url)
            .putString(KEY_USER, credentials.username)
            .putString(KEY_TOKEN, credentials.token)
            .putString(KEY_SALT, credentials.salt)
            .apply()
    }

    fun clearCredentials() {
        prefs.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_SALT)
            .apply()
    }

    /** Last typed server URL / user name, so the login form is pre-filled. */
    fun lastServerUrl(): String = prefs.getString(KEY_URL, "") ?: ""

    fun lastUsername(): String = prefs.getString(KEY_USER, "") ?: ""

    fun getString(key: String, default: String): String = prefs.getString(key, default) ?: default

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean): Boolean = prefs.getBoolean(key, default)

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    // ---------------------------------------------------------------------------------------------
    // Playback state & History persistence (survives phone reboot & app restarts)
    // ---------------------------------------------------------------------------------------------

    private fun trackToJson(t: TrackItem): JSONObject = JSONObject().apply {
        put("id", t.id)
        put("title", t.title)
        put("artist", t.artist)
        put("album", t.album)
        put("duration", t.duration)
        put("badge", t.badge)
        put("imageUrl", t.imageUrl)
        put("albumId", t.albumId)
        put("artistId", t.artistId)
        put("durationSec", t.durationSec)
        put("suffix", t.suffix)
        put("bitRate", t.bitRate)
        put("bitDepth", t.bitDepth)
        put("samplingRate", t.samplingRate)
        put("year", t.year)
        put("coverArtId", t.coverArtId)
        put("techLine", t.techLine)
        put("isHiResBadgeCyan", t.isHiResBadgeCyan)
    }

    private fun trackFromJson(obj: JSONObject): TrackItem = TrackItem(
        id = obj.optString("id", ""),
        title = obj.optString("title", ""),
        artist = obj.optString("artist", ""),
        album = obj.optString("album", ""),
        duration = obj.optString("duration", "0:00"),
        badge = obj.optString("badge", ""),
        imageUrl = obj.optString("imageUrl", ""),
        albumId = obj.optString("albumId", ""),
        artistId = obj.optString("artistId", ""),
        durationSec = obj.optInt("durationSec", 0),
        suffix = obj.optString("suffix", ""),
        bitRate = obj.optInt("bitRate", 0),
        bitDepth = obj.optInt("bitDepth", 0),
        samplingRate = obj.optInt("samplingRate", 0),
        year = obj.optInt("year", 0),
        coverArtId = obj.optString("coverArtId", ""),
        techLine = obj.optString("techLine", ""),
        isHiResBadgeCyan = obj.optBoolean("isHiResBadgeCyan", true)
    )

    fun savePlaybackState(track: TrackItem, positionMs: Long, queue: List<TrackItem>, queueIndex: Int) {
        if (track.id.isBlank()) return
        val queueArray = JSONArray()
        queue.take(150).forEach { queueArray.put(trackToJson(it)) }
        prefs.edit()
            .putString(KEY_LAST_TRACK, trackToJson(track).toString())
            .putLong(KEY_LAST_POSITION_MS, positionMs)
            .putString(KEY_LAST_QUEUE, queueArray.toString())
            .putInt(KEY_LAST_QUEUE_INDEX, queueIndex)
            .apply()
    }

    fun savePositionOnly(positionMs: Long) {
        prefs.edit().putLong(KEY_LAST_POSITION_MS, positionMs).apply()
    }

    fun loadLastTrack(): TrackItem? {
        val str = prefs.getString(KEY_LAST_TRACK, null) ?: return null
        return try {
            val obj = JSONObject(str)
            trackFromJson(obj).takeIf { it.id.isNotBlank() }
        } catch (_: Exception) {
            null
        }
    }

    fun loadLastPositionMs(): Long = prefs.getLong(KEY_LAST_POSITION_MS, 0L)

    fun loadLastQueue(): List<TrackItem> {
        val str = prefs.getString(KEY_LAST_QUEUE, null) ?: return emptyList()
        return try {
            val arr = JSONArray(str)
            val list = mutableListOf<TrackItem>()
            for (i in 0 until arr.length()) {
                list.add(trackFromJson(arr.getJSONObject(i)))
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun loadLastQueueIndex(): Int = prefs.getInt(KEY_LAST_QUEUE_INDEX, 0)

    fun addTrackToHistory(track: TrackItem) {
        if (track.id.isBlank()) return
        val current = loadHistory().toMutableList()
        if (current.firstOrNull()?.id == track.id) return
        current.removeAll { it.id == track.id }
        current.add(0, track)
        val trimmed = current.take(60)
        val arr = JSONArray()
        trimmed.forEach { arr.put(trackToJson(it)) }
        prefs.edit().putString(KEY_HISTORY, arr.toString()).apply()
    }

    fun loadHistory(): List<TrackItem> {
        val str = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        return try {
            val arr = JSONArray(str)
            val list = mutableListOf<TrackItem>()
            for (i in 0 until arr.length()) {
                list.add(trackFromJson(arr.getJSONObject(i)))
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun clearHistory() {
        prefs.edit().remove(KEY_HISTORY).apply()
    }

    companion object {
        private const val KEY_URL = "server_url"
        private const val KEY_USER = "username"
        private const val KEY_TOKEN = "token"
        private const val KEY_SALT = "salt"

        const val KEY_CELLULAR_TRANSCODING = "cellular_transcoding"
        const val KEY_OPUS_BITRATE = "opus_bitrate"
        const val KEY_BACKEND_CODEC = "backend_codec"
        const val KEY_LOGIN_TRANSCODE = "login_transcode_option"

        private const val KEY_LAST_TRACK = "last_track_state"
        private const val KEY_LAST_POSITION_MS = "last_position_ms"
        private const val KEY_LAST_QUEUE = "last_queue_state"
        private const val KEY_LAST_QUEUE_INDEX = "last_queue_index"
        private const val KEY_HISTORY = "playback_history"
    }
}
