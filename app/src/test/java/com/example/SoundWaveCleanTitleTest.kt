package com.example

import com.example.data.cleanDisplayTitle
import org.junit.Assert.assertEquals
import org.junit.Test

class SoundWaveCleanTitleTest {

    @Test
    fun testCleanTrackAndAlbumTitles() {
        assertEquals("Deftones - Back To School", cleanDisplayTitle("00 - Deftones - Back To School"))
        assertEquals("Guns N' Roses - Use Your Illusion I", cleanDisplayTitle("00 - Guns N' Roses - Use Your Illusion I"))
        assertEquals("Dio Evil Or Divine Live In New York", cleanDisplayTitle("00-dio-evil_or_divine_live_in_new_york"))
        assertEquals("Hammerfall (r)evolution Live", cleanDisplayTitle("00-hammerfall-(r)evolution-live"))
        assertEquals("Mick Gordon Doom Original Game Soundtrack", cleanDisplayTitle("00-mick_gordon-doom_original_game_soundtrack"))
        assertEquals("Perut", cleanDisplayTitle("311. Perut"))
        assertEquals("Na Slamníku", cleanDisplayTitle("Na Slamníku"))
        assertEquals("Diamond Eyes", cleanDisplayTitle("Diamond Eyes"))
    }
}
