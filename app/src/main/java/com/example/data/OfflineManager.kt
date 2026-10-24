package com.example.data

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Handles downloading tracks for offline listening, storing audio files and metadata locally,
 * and providing offline playback URIs.
 */
class OfflineManager(private val context: Context) {

    private val baseDir: File by lazy {
        val ext = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        if (ext != null && ext.exists()) ext else File(context.filesDir, "offline_music").apply { mkdirs() }
    }

    private val tracksDir: File
        get() = File(baseDir, "tracks").apply { mkdirs() }

    private val metaDir: File
        get() = File(baseDir, "metadata").apply { mkdirs() }

    fun isDownloaded(trackId: String): Boolean {
        if (trackId.isBlank()) return false
        val file = getAudioFile(trackId)
        return file.exists() && file.length() > 1024
    }

    fun getAudioFile(trackId: String): File {
        return File(tracksDir, "track_${trackId}.bin")
    }

    fun getLocalFileUri(trackId: String): String? {
        val file = getAudioFile(trackId)
        return if (file.exists() && file.length() > 1024) file.absolutePath else null
    }

    /**
     * Downloads the audio file from the server and saves the track metadata.
     */
    suspend fun downloadTrack(
        downloadUrl: String,
        track: TrackItem,
        onProgress: (Float) -> Unit = {}
    ): Boolean = withContext(Dispatchers.IO) {
        val targetFile = getAudioFile(track.id)
        val tempFile = File(tracksDir, "track_${track.id}.tmp")

        var conn: HttpURLConnection? = null
        try {
            conn = URL(downloadUrl).openConnection() as HttpURLConnection
            conn.connectTimeout = 15_000
            conn.readTimeout = 40_000
            conn.requestMethod = "GET"
            conn.connect()

            val code = conn.responseCode
            if (code !in 200..299) {
                return@withContext false
            }

            val totalBytes = conn.contentLength.coerceAtLeast(1)
            var bytesReadTotal = 0L

            conn.inputStream.use { input ->
                FileOutputStream(tempFile).use { output ->
                    val buffer = ByteArray(16 * 1024)
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                        bytesReadTotal += read
                        if (totalBytes > 0) {
                            onProgress(bytesReadTotal.toFloat() / totalBytes)
                        }
                    }
                }
            }

            if (tempFile.length() > 1024) {
                if (targetFile.exists()) targetFile.delete()
                tempFile.renameTo(targetFile)
                saveMetadata(track)
                true
            } else {
                tempFile.delete()
                false
            }
        } catch (e: Exception) {
            tempFile.delete()
            false
        } finally {
            conn?.disconnect()
        }
    }

    private fun saveMetadata(track: TrackItem) {
        try {
            val metaFile = File(metaDir, "meta_${track.id}.json")
            val json = JSONObject().apply {
                put("id", track.id)
                put("title", track.title)
                put("artist", track.artist)
                put("album", track.album)
                put("duration", track.duration)
                put("badge", track.badge)
                put("imageUrl", track.imageUrl)
                put("albumId", track.albumId)
                put("artistId", track.artistId)
                put("durationSec", track.durationSec)
                put("suffix", track.suffix)
                put("bitRate", track.bitRate)
                put("bitDepth", track.bitDepth)
                put("samplingRate", track.samplingRate)
                put("year", track.year)
                put("coverArtId", track.coverArtId)
                put("techLine", track.techLine)
                put("isHiResBadgeCyan", track.isHiResBadgeCyan)
            }
            metaFile.writeText(json.toString(), Charsets.UTF_8)
        } catch (_: Exception) {}
    }

    fun deleteDownloadedTrack(trackId: String): Boolean {
        val audio = getAudioFile(trackId)
        val meta = File(metaDir, "meta_${trackId}.json")
        val deletedAudio = if (audio.exists()) audio.delete() else false
        if (meta.exists()) meta.delete()
        return deletedAudio
    }

    fun getAllDownloadedTracks(): List<TrackItem> {
        val result = mutableListOf<TrackItem>()
        val files = metaDir.listFiles() ?: return emptyList()
        for (f in files) {
            if (f.name.startsWith("meta_") && f.name.endsWith(".json")) {
                try {
                    val json = JSONObject(f.readText(Charsets.UTF_8))
                    val id = json.optString("id")
                    if (isDownloaded(id)) {
                        result.add(
                            TrackItem(
                                id = id,
                                title = json.optString("title", "Neznáma skladba"),
                                artist = json.optString("artist", "Neznámy interpret"),
                                album = json.optString("album", ""),
                                duration = json.optString("duration", "0:00"),
                                badge = json.optString("badge", "OFFLINE"),
                                imageUrl = json.optString("imageUrl", ""),
                                isDownloaded = true,
                                isHiResBadgeCyan = json.optBoolean("isHiResBadgeCyan", true),
                                albumId = json.optString("albumId", ""),
                                artistId = json.optString("artistId", ""),
                                durationSec = json.optInt("durationSec", 0),
                                suffix = json.optString("suffix", ""),
                                bitRate = json.optInt("bitRate", 0),
                                bitDepth = json.optInt("bitDepth", 0),
                                samplingRate = json.optInt("samplingRate", 0),
                                year = json.optInt("year", 0),
                                coverArtId = json.optString("coverArtId", ""),
                                techLine = json.optString("techLine", "OFFLINE SÚBOR")
                            )
                        )
                    }
                } catch (_: Exception) {}
            }
        }
        return result
    }

    fun getDownloadedTotalSizeBytes(): Long {
        var size = 0L
        tracksDir.listFiles()?.forEach { if (it.isFile) size += it.length() }
        return size
    }

    fun clearAllDownloads(): Int {
        var count = 0
        tracksDir.listFiles()?.forEach {
            if (it.delete()) count++
        }
        metaDir.listFiles()?.forEach { it.delete() }
        return count
    }
}
