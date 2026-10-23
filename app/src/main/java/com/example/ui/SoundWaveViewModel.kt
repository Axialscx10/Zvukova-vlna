package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppScreen
import com.example.data.HeadphoneProfile
import com.example.data.PlaylistItem
import com.example.data.SoundWaveCatalog
import com.example.data.TrackItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SoundWaveUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val backStack: List<AppScreen> = emptyList(),
    val showAutoEqModal: Boolean = false,
    val showScreenSwitcherSheet: Boolean = false,

    // Playback State
    val currentTrack: TrackItem = SoundWaveCatalog.homeQuickPicks[3], // After Dark
    val isPlaying: Boolean = true,
    val playbackProgress: Float = 0.45f,
    val likedTrackIds: Set<String> = setOf("qp_after_dark", "exp_days_of_thunder", "pl_after_dark", "tm_sunset"),
    val isShuffleEnabled: Boolean = true,
    val isRepeatOne: Boolean = false,
    val nowPlayingTab: String = "LYRICS", // "UP NEXT", "LYRICS", "RELATED"
    val activeLyricIndex: Int = 1,

    // Home Filter
    val homeFilter: String = "All",

    // Explore / Navidrome Search State
    val searchQuery: String = "Synthwave",
    val exploreCategory: String = "Všetko",
    val activeAudiophileFilters: Set<String> = setOf(
        "FLAC / Bezstratové",
        "Vzorkovanie ≥ 24-bit",
        "Rok: 1980 – 1989 (Synth)"
    ),
    val isArtistFollowed: Boolean = false,
    val lastSyncTime: String = "Dnes o 03:15 · Žiadne chýbajúce tagy",
    val isScanningServer: Boolean = false,

    // Artist Detail State
    val artistFilterChip: String = "Populárne",
    val isArtistFavorited: Boolean = true,
    val isDiscographyDownloading: Boolean = false,

    // Library State
    val libraryFilterChip: String = "Playlists",
    val libraryIsGridView: Boolean = false,
    val libraryPlaylists: List<PlaylistItem> = SoundWaveCatalog.initialLibraryPlaylists,

    // Equalizer & DSP State
    val eqMasterEnabled: Boolean = true,
    val preampDb: Float = -1.5f,
    val eqBands: List<Float> = SoundWaveCatalog.eqPresets["Synthwave / Retrowave"]!!,
    val activePresetName: String = "Synthwave / Retrowave",
    val subBassBoostDb: Float = 3.5f,
    val crossfeedEnabled: Boolean = true,
    val autoGainEnabled: Boolean = true,

    // AutoEQ Headphone Calibration State
    val autoEqSearchQuery: String = "Sennheiser HD",
    val autoEqTargetCurve: String = "Harman Target 2019v2",
    val selectedHeadphone: HeadphoneProfile = SoundWaveCatalog.headphoneProfiles[0],
    val calibrationDryWetPct: Float = 100f,
    val autoPreampEnabled: Boolean = true,
    val subBassCompensationEnabled: Boolean = true,

    // Streaming Quality & ffmpeg Server Settings State
    val cellularTranscodingEnabled: Boolean = true,
    val opusBitrateTarget: String = "256k", // "Auto", "128k", "256k", "320k"
    val backendCodec: String = "Opus (libopus)",
    val maxSampleRate: String = "48 kHz",
    val hardwareAccelVaapi: Boolean = true,
    val bitPerfectExclusiveMode: Boolean = true,
    val replayGainEnabled: Boolean = true,
    val replayGainMode: String = "Album Gain (Zachová dynamiku)",
    val replayGainPreampDb: Float = -2.0f,
    val gaplessEnabled: Boolean = true,
    val crossfadeSeconds: Int = 0,
    val cacheSizeText: String = "Stream cache obsahuje 1.2 GB dát",
    val isTestingDiagnostics: Boolean = false,
    val diagnosticResultVisible: Boolean = false,

    // Navidrome Server Login State
    val serverUrl: String = "https://music.homebox.internal:4533",
    val username: String = "matus_audiophile",
    val password: String = "SuperSecretToken2024",
    val isPasswordVisible: Boolean = false,
    val showTokenHint: Boolean = false,
    val showAdvancedAudioAccordion: Boolean = false,
    val loginOfflineCacheEnabled: Boolean = true,
    val loginPreferFlacEnabled: Boolean = true,
    val loginTranscodeOption: String = "Pôvodná kvalita (Direct Stream)",
    val isConnectingServer: Boolean = false,
    val isServerConnectedSuccess: Boolean = false
)

class SoundWaveViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SoundWaveUiState())
    val uiState: StateFlow<SoundWaveUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _uiState.update { state ->
            if (state.currentScreen == screen) {
                state.copy(showScreenSwitcherSheet = false)
            } else {
                state.copy(
                    backStack = state.backStack + state.currentScreen,
                    currentScreen = screen,
                    showScreenSwitcherSheet = false
                )
            }
        }
    }

    fun navigateBack(): Boolean {
        val current = _uiState.value
        if (current.showAutoEqModal) {
            _uiState.update { it.copy(showAutoEqModal = false) }
            return true
        }
        if (current.showScreenSwitcherSheet) {
            _uiState.update { it.copy(showScreenSwitcherSheet = false) }
            return true
        }
        if (current.backStack.isNotEmpty()) {
            val prev = current.backStack.last()
            _uiState.update {
                it.copy(
                    currentScreen = prev,
                    backStack = it.backStack.dropLast(1)
                )
            }
            return true
        }
        if (current.currentScreen != AppScreen.HOME) {
            _uiState.update { it.copy(currentScreen = AppScreen.HOME) }
            return true
        }
        return false
    }

    fun setShowAutoEqModal(show: Boolean) {
        _uiState.update { it.copy(showAutoEqModal = show) }
    }

    fun setShowScreenSwitcherSheet(show: Boolean) {
        _uiState.update { it.copy(showScreenSwitcherSheet = show) }
    }

    // Playback actions
    fun togglePlayPause() {
        _uiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun playTrack(track: TrackItem, openFullPlayer: Boolean = false) {
        _uiState.update { state ->
            val nextScreen = if (openFullPlayer) AppScreen.NOW_PLAYING else state.currentScreen
            val nextStack = if (openFullPlayer && state.currentScreen != AppScreen.NOW_PLAYING) {
                state.backStack + state.currentScreen
            } else state.backStack
            state.copy(
                currentTrack = track,
                isPlaying = true,
                currentScreen = nextScreen,
                backStack = nextStack
            )
        }
    }

    fun skipToNextTrack() {
        val allTracks = SoundWaveCatalog.homeQuickPicks + SoundWaveCatalog.exploreTracks + SoundWaveCatalog.artistTopTracks
        val currentIdx = allTracks.indexOfFirst { it.id == _uiState.value.currentTrack.id }
        val nextTrack = allTracks[(currentIdx + 1).coerceAtLeast(0) % allTracks.size]
        _uiState.update { it.copy(currentTrack = nextTrack, isPlaying = true, playbackProgress = 0.15f) }
    }

    fun skipToPreviousTrack() {
        val allTracks = SoundWaveCatalog.homeQuickPicks + SoundWaveCatalog.exploreTracks + SoundWaveCatalog.artistTopTracks
        val currentIdx = allTracks.indexOfFirst { it.id == _uiState.value.currentTrack.id }
        val prevIdx = if (currentIdx <= 0) allTracks.lastIndex else currentIdx - 1
        _uiState.update { it.copy(currentTrack = allTracks[prevIdx], isPlaying = true, playbackProgress = 0.1f) }
    }

    fun setPlaybackProgress(progress: Float) {
        _uiState.update { it.copy(playbackProgress = progress.coerceIn(0f, 1f)) }
    }

    fun toggleTrackLike(trackId: String) {
        _uiState.update { state ->
            val updated = if (trackId in state.likedTrackIds) {
                state.likedTrackIds - trackId
            } else {
                state.likedTrackIds + trackId
            }
            state.copy(likedTrackIds = updated)
        }
    }

    fun toggleShuffle() {
        _uiState.update { it.copy(isShuffleEnabled = !it.isShuffleEnabled) }
    }

    fun toggleRepeatOne() {
        _uiState.update { it.copy(isRepeatOne = !it.isRepeatOne) }
    }

    fun setNowPlayingTab(tab: String) {
        _uiState.update { it.copy(nowPlayingTab = tab) }
    }

    fun setActiveLyricIndex(index: Int) {
        _uiState.update { it.copy(activeLyricIndex = index) }
    }

    // Home
    fun setHomeFilter(filter: String) {
        _uiState.update { it.copy(homeFilter = filter) }
    }

    // Explore
    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setExploreCategory(category: String) {
        _uiState.update { it.copy(exploreCategory = category) }
    }

    fun toggleAudiophileFilter(filter: String) {
        _uiState.update { state ->
            val updated = if (filter in state.activeAudiophileFilters) {
                state.activeAudiophileFilters - filter
            } else {
                state.activeAudiophileFilters + filter
            }
            state.copy(activeAudiophileFilters = updated)
        }
    }

    fun resetAudiophileFilters() {
        _uiState.update {
            it.copy(
                activeAudiophileFilters = setOf(
                    "FLAC / Bezstratové",
                    "Vzorkovanie ≥ 24-bit",
                    "Rok: 1980 – 1989 (Synth)"
                )
            )
        }
    }

    fun toggleArtistFollow() {
        _uiState.update { it.copy(isArtistFollowed = !it.isArtistFollowed) }
    }

    fun triggerServerScan() {
        if (_uiState.value.isScanningServer) return
        _uiState.update { it.copy(isScanningServer = true, lastSyncTime = "Skenujem knižnicu Navidrome...") }
        viewModelScope.launch {
            delay(800)
            _uiState.update {
                it.copy(
                    isScanningServer = false,
                    lastSyncTime = "Práve teraz · 42 850 skladieb (OK)"
                )
            }
        }
    }

    // Artist Detail
    fun setArtistFilterChip(chip: String) {
        _uiState.update { it.copy(artistFilterChip = chip) }
    }

    fun toggleArtistFavorite() {
        _uiState.update { it.copy(isArtistFavorited = !it.isArtistFavorited) }
    }

    fun toggleDiscographyDownload() {
        _uiState.update { it.copy(isDiscographyDownloading = !it.isDiscographyDownloading) }
    }

    // Library
    fun setLibraryFilterChip(chip: String) {
        _uiState.update { it.copy(libraryFilterChip = chip) }
    }

    fun toggleLibraryGridView() {
        _uiState.update { it.copy(libraryIsGridView = !it.libraryIsGridView) }
    }

    fun createNewPlaylist() {
        _uiState.update { state ->
            val count = state.libraryPlaylists.size + 1
            val newPl = PlaylistItem(
                id = "lib_custom_$count",
                title = "Nocturne Mix #$count",
                subtitle = "Navidrome Synced",
                trackCount = "18 skladieb",
                badge = "FLAC",
                imageUrl = SoundWaveCatalog.SYNTHWAVE_NIGHTS_COVER_URL,
                isSynced = true
            )
            state.copy(libraryPlaylists = listOf(newPl) + state.libraryPlaylists)
        }
    }

    // Equalizer & DSP
    fun toggleEqMaster(enabled: Boolean) {
        _uiState.update { it.copy(eqMasterEnabled = enabled) }
    }

    fun setPreampDb(value: Float) {
        val rounded = (Math.round(value * 2f) / 2f).coerceIn(-12f, 12f)
        _uiState.update { it.copy(preampDb = rounded) }
    }

    fun setEqBand(index: Int, value: Float) {
        val rounded = (Math.round(value * 2f) / 2f).coerceIn(-12f, 12f)
        _uiState.update { state ->
            val updated = state.eqBands.toMutableList()
            if (index in updated.indices) {
                updated[index] = rounded
            }
            state.copy(eqBands = updated, activePresetName = "Vlastný (Custom)")
        }
    }

    fun resetEqFlat() {
        _uiState.update {
            it.copy(
                eqBands = List(10) { 0f },
                activePresetName = "Audiofil Flat (Referenčný)"
            )
        }
    }

    fun invertEqBands() {
        _uiState.update { state ->
            state.copy(
                eqBands = state.eqBands.map { (-it).coerceIn(-12f, 12f) },
                activePresetName = "Invertovaný"
            )
        }
    }

    fun selectEqPreset(presetName: String) {
        val values = SoundWaveCatalog.eqPresets[presetName] ?: return
        _uiState.update {
            it.copy(
                eqBands = values,
                activePresetName = presetName
            )
        }
    }

    fun setSubBassBoostDb(value: Float) {
        val rounded = (Math.round(value * 2f) / 2f).coerceIn(0f, 10f)
        _uiState.update { it.copy(subBassBoostDb = rounded) }
    }

    fun toggleCrossfeed(enabled: Boolean) {
        _uiState.update { it.copy(crossfeedEnabled = enabled) }
    }

    fun toggleAutoGain(enabled: Boolean) {
        _uiState.update { it.copy(autoGainEnabled = enabled) }
    }

    // AutoEQ Modal
    fun setAutoEqSearchQuery(query: String) {
        _uiState.update { it.copy(autoEqSearchQuery = query) }
    }

    fun setAutoEqTargetCurve(target: String) {
        _uiState.update { it.copy(autoEqTargetCurve = target) }
    }

    fun selectHeadphoneProfile(profile: HeadphoneProfile) {
        _uiState.update { it.copy(selectedHeadphone = profile) }
    }

    fun setCalibrationDryWet(pct: Float) {
        _uiState.update { it.copy(calibrationDryWetPct = pct.coerceIn(0f, 100f)) }
    }

    fun toggleAutoPreamp(enabled: Boolean) {
        _uiState.update { it.copy(autoPreampEnabled = enabled) }
    }

    fun toggleSubBassCompensation(enabled: Boolean) {
        _uiState.update { it.copy(subBassCompensationEnabled = enabled) }
    }

    fun applyAutoEqProfileToDsp() {
        val profile = _uiState.value.selectedHeadphone
        val preampVal = profile.preampDb.replace("dB", "").trim().toFloatOrNull() ?: -4.8f
        _uiState.update {
            it.copy(
                preampDb = if (it.autoPreampEnabled) preampVal else it.preampDb,
                showAutoEqModal = false
            )
        }
    }

    // Streaming Quality & Server Settings
    fun toggleCellularTranscoding() {
        _uiState.update { it.copy(cellularTranscodingEnabled = !it.cellularTranscodingEnabled) }
    }

    fun setOpusBitrateTarget(target: String) {
        _uiState.update { it.copy(opusBitrateTarget = target) }
    }

    fun setBackendCodec(codec: String) {
        _uiState.update { it.copy(backendCodec = codec) }
    }

    fun cycleMaxSampleRate() {
        val rates = listOf("48 kHz", "96 kHz", "192 kHz", "44.1 kHz")
        val next = rates[(rates.indexOf(_uiState.value.maxSampleRate) + 1) % rates.size]
        _uiState.update { it.copy(maxSampleRate = next) }
    }

    fun toggleHardwareAccelVaapi() {
        _uiState.update { it.copy(hardwareAccelVaapi = !it.hardwareAccelVaapi) }
    }

    fun toggleBitPerfectExclusive() {
        _uiState.update { it.copy(bitPerfectExclusiveMode = !it.bitPerfectExclusiveMode) }
    }

    fun toggleReplayGain() {
        _uiState.update { it.copy(replayGainEnabled = !it.replayGainEnabled) }
    }

    fun setReplayGainMode(mode: String) {
        _uiState.update { it.copy(replayGainMode = mode) }
    }

    fun setReplayGainPreamp(db: Float) {
        val rounded = (Math.round(db * 2f) / 2f).coerceIn(-6f, 6f)
        _uiState.update { it.copy(replayGainPreampDb = rounded) }
    }

    fun toggleGapless() {
        _uiState.update { it.copy(gaplessEnabled = !it.gaplessEnabled) }
    }

    fun setCrossfadeSeconds(sec: Int) {
        _uiState.update { it.copy(crossfadeSeconds = sec.coerceIn(0, 12)) }
    }

    fun clearStreamCache() {
        _uiState.update { it.copy(cacheSizeText = "Vyrovnávacia pamäť vymazaná (0 MB)") }
    }

    fun runTranscodingDiagnostics() {
        if (_uiState.value.isTestingDiagnostics) return
        _uiState.update { it.copy(isTestingDiagnostics = true, diagnosticResultVisible = false) }
        viewModelScope.launch {
            delay(850)
            _uiState.update {
                it.copy(
                    isTestingDiagnostics = false,
                    diagnosticResultVisible = true
                )
            }
        }
    }

    // Server Login
    fun setServerUrl(url: String) {
        _uiState.update { it.copy(serverUrl = url) }
    }

    fun setUsername(user: String) {
        _uiState.update { it.copy(username = user) }
    }

    fun setPassword(pwd: String) {
        _uiState.update { it.copy(password = pwd) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleTokenHint() {
        _uiState.update { it.copy(showTokenHint = !it.showTokenHint) }
    }

    fun toggleAdvancedAudioAccordion() {
        _uiState.update { it.copy(showAdvancedAudioAccordion = !it.showAdvancedAudioAccordion) }
    }

    fun toggleLoginOfflineCache() {
        _uiState.update { it.copy(loginOfflineCacheEnabled = !it.loginOfflineCacheEnabled) }
    }

    fun toggleLoginPreferFlac() {
        _uiState.update { it.copy(loginPreferFlacEnabled = !it.loginPreferFlacEnabled) }
    }

    fun cycleLoginTranscodeOption() {
        val options = listOf(
            "Pôvodná kvalita (Direct Stream)",
            "Vysoká (MP3 320 kbps)",
            "Mobilné dáta (Opus 192 kbps)",
            "Úspora dát (Opus 128 kbps)"
        )
        val next = options[(options.indexOf(_uiState.value.loginTranscodeOption) + 1) % options.size]
        _uiState.update { it.copy(loginTranscodeOption = next) }
    }

    fun fillDemoServerData() {
        _uiState.update {
            it.copy(
                serverUrl = "https://demo.navidrome.org",
                username = "demo",
                password = "demo",
                isServerConnectedSuccess = false
            )
        }
    }

    fun connectToServer() {
        if (_uiState.value.isConnectingServer) return
        _uiState.update { it.copy(isConnectingServer = true, isServerConnectedSuccess = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update {
                it.copy(
                    isConnectingServer = false,
                    isServerConnectedSuccess = true
                )
            }
        }
    }
}
