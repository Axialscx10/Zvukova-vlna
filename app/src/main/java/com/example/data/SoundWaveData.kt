package com.example.data

enum class AppScreen(val title: String) {
    HOME("Domov"),
    EXPLORE("Objavuj"),
    LIBRARY("Knižnica"),
    UPGRADE_STREAMING("Kvalita streamovania"),
    NOW_PLAYING("Práve hrá"),
    PLAYLIST_DETAIL("Detail"),
    EQUALIZER("Ekvalizér a zvukové profily"),
    SERVER_LOGIN("Prihlásenie na server")
}

data class TrackItem(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: String,
    val badge: String,
    val imageUrl: String,
    val playCount: String? = null,
    val isDownloaded: Boolean = false,
    val isLiked: Boolean = false,
    val isHiResBadgeCyan: Boolean = true,
    // Real metadata coming from the Subsonic / Navidrome server
    val albumId: String = "",
    val artistId: String = "",
    val durationSec: Int = 0,
    val suffix: String = "",
    val bitRate: Int = 0,
    val bitDepth: Int = 0,
    val samplingRate: Int = 0,
    val year: Int = 0,
    val coverArtId: String = "",
    val techLine: String = ""
)

/** Placeholder used when nothing is playing yet. */
val NoTrack = TrackItem(
    id = "",
    title = "Nič nehrá",
    artist = "Vyber skladbu z knižnice",
    album = "",
    duration = "0:00",
    badge = "",
    imageUrl = ""
)

data class AlbumItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val techSpec: String,
    val badge: String,
    val imageUrl: String,
    val isDownloaded: Boolean = false,
    val isOutlineBadge: Boolean = false
)

data class ArtistItem(
    val id: String,
    val name: String,
    val trackCount: String,
    val imageUrl: String
)

/**
 * Row model used for playlists, but also for albums / artists shown in lists.
 * The [id] carries a prefix so the UI can route a click: "pl:" playlist, "al:" album,
 * "ar:" artist, "ge:" genre.
 */
data class PlaylistItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val trackCount: String,
    val durationOrExtra: String = "",
    val badge: String = "",
    val imageUrl: String,
    val collageUrls: List<String> = emptyList(),
    val isPinned: Boolean = false,
    val isOffline: Boolean = false,
    val isStarred: Boolean = false,
    val isSynced: Boolean = false
)

data class GenreItem(
    val name: String,
    val songCount: Int
)

data class LyricLine(
    val startMs: Long,
    val text: String
)

/** Generic "detail" page content: an album, a playlist, an artist, a genre or the favourites. */
data class CollectionDetail(
    val id: String,
    val kind: String,
    val title: String,
    val subtitle: String,
    val coverUrl: String,
    val metaLine: String,
    val tracks: List<TrackItem>,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class EqBandInfo(
    val index: Int,
    val freqLabel: String,
    val subLabel: String,
    val fullFreq: String
)

data class HeadphoneProfile(
    val id: String,
    val name: String,
    val targetInfo: String,
    val preampDb: String,
    val filtersSpec: String,
    val impedanceSpec: String
)

/** Static reference data for the equalizer / AutoEQ screens (these screens are still UI only). */
object SoundWaveCatalog {
    // 10-Band EQ Metadata
    val eqBands = listOf(
        EqBandInfo(0, "31", "Sub", "31 Hz"),
        EqBandInfo(1, "62", "Bas", "62 Hz"),
        EqBandInfo(2, "125", "Punch", "125 Hz"),
        EqBandInfo(3, "250", "Warmth", "250 Hz"),
        EqBandInfo(4, "500", "Body", "500 Hz"),
        EqBandInfo(5, "1k", "Vocal", "1 kHz"),
        EqBandInfo(6, "2k", "Clarity", "2 kHz"),
        EqBandInfo(7, "4k", "Presence", "4 kHz"),
        EqBandInfo(8, "8k", "Shimmer", "8 kHz"),
        EqBandInfo(9, "16k", "Air", "16 kHz")
    )

    val eqPresets: Map<String, List<Float>> = linkedMapOf(
        "Synthwave / Retrowave" to listOf(4.5f, 3.0f, 1.5f, 0.0f, -1.5f, -1.0f, 1.0f, 2.5f, 4.0f, 5.0f),
        "Elektronika & Bass Boost" to listOf(6.0f, 5.0f, 3.5f, 1.0f, -0.5f, 0.0f, 1.5f, 3.0f, 4.5f, 4.0f),
        "Audiofil Flat (Referenčný)" to listOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
        "Vokály & Akustika" to listOf(-2.0f, -1.0f, 0.0f, 1.5f, 3.0f, 3.5f, 2.5f, 1.0f, 0.5f, 1.0f),
        "Nočné počúvanie (Loudness)" to listOf(4.0f, 2.5f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.5f, 3.0f, 4.0f)
    )

    // AutoEQ Headphone Profiles
    val headphoneProfiles = listOf(
        HeadphoneProfile(
            id = "hd600",
            name = "Sennheiser HD 600",
            targetInfo = "Harman Target v2 • Oratory1990 meranie",
            preampDb = "-4.8 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd650",
            name = "Sennheiser HD 650 / 6XX",
            targetInfo = "Harman Target v2 • Crinacle meranie",
            preampDb = "-5.2 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd800s",
            name = "Sennheiser HD 800 S",
            targetInfo = "Harman Target • Oratory1990 meranie",
            preampDb = "-6.1 dB",
            filtersSpec = "High-End Open Back",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd560s",
            name = "Sennheiser HD 560S",
            targetInfo = "Harman Over-Ear Target • Referenčný profil",
            preampDb = "-3.5 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "120 Ω"
        ),
        HeadphoneProfile(
            id = "wh1000xm5",
            name = "Sony WH-1000XM5",
            targetInfo = "Bluetooth ANC • Harman Target (Oratory1990)",
            preampDb = "-4.0 dB",
            filtersSpec = "Vypnutý interný EQ",
            impedanceSpec = "48 Ω"
        )
    )
}

enum class SleepTimerOption(val label: String, val minutes: Int) {
    OFF("Vypnuté", 0),
    MIN_15("15 minút", 15),
    MIN_30("30 minút", 30),
    MIN_45("45 minút", 45),
    MIN_60("60 minút", 60),
    END_OF_TRACK("Po dohraní aktuálnej skladby", -1)
}

/**
 * Intelligently cleans raw folder names, file names, or unformatted titles returned by Subsonic / Navidrome.
 * Strips leading track/folder prefixes like "00 - ", "00-", "01. ", "311. ",
 * and formats underscore-separated filenames like "dio-evil_or_divine..." into readable titles.
 */
fun cleanDisplayTitle(raw: String): String {
    if (raw.isBlank()) return raw
    var s = raw.trim()
    // Strip leading track or album numbers like "00 - ", "00-", "01. ", "02 - ", "311. "
    s = s.replace(Regex("""^\d{1,4}\s*[-._]\s*"""), "")
    // If it's a raw snake_case or kebab-case filename like "dio-evil_or_divine_live_in_new_york"
    if (s.contains('_') || (s.contains('-') && !s.contains(" - ") && !s.contains(' '))) {
        s = s.replace('_', ' ').replace('-', ' ')
        s = s.split(Regex("""\s+""")).joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.ROOT) else it.toString() }
        }
    }
    return s.ifBlank { raw }
}

