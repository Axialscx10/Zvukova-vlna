package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SoundWave", appName)
  }

  @Test
  fun `playback position and queue are saved and restored properly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val store = com.example.data.SettingsStore(context)
    val track = com.example.data.TrackItem(
      id = "track_123",
      title = "Midnight City",
      artist = "M83",
      album = "Hurry Up, We're Dreaming",
      duration = "4:03",
      badge = "FLAC 24-bit",
      imageUrl = "https://example.com/cover.jpg",
      durationSec = 243
    )

    store.savePlaybackState(track, 125000L, listOf(track), 0)

    val restoredTrack = store.loadLastTrack()
    assertEquals("track_123", restoredTrack?.id)
    assertEquals("Midnight City", restoredTrack?.title)
    assertEquals(125000L, store.loadLastPositionMs())

    store.savePositionOnly(130000L)
    assertEquals(130000L, store.loadLastPositionMs())
  }

  @Test
  fun `history persistence tracks played songs and allows clearing`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val store = com.example.data.SettingsStore(context)
    store.clearHistory()

    val t1 = com.example.data.TrackItem(id = "1", title = "Song 1", artist = "Artist 1", album = "A1", duration = "3:00", badge = "MP3", imageUrl = "")
    val t2 = com.example.data.TrackItem(id = "2", title = "Song 2", artist = "Artist 2", album = "A2", duration = "3:30", badge = "FLAC", imageUrl = "")

    store.addTrackToHistory(t1)
    store.addTrackToHistory(t2)

    val history = store.loadHistory()
    assertEquals(2, history.size)
    assertEquals("2", history[0].id)
    assertEquals("1", history[1].id)

    store.clearHistory()
    assertEquals(0, store.loadHistory().size)
  }
}
