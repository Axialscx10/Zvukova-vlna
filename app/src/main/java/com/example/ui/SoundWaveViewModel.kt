package com.example.ui

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.data.AlbumItem
import com.example.data.AppScreen
import com.example.data.ArtistItem
import com.example.data.CollectionDetail
import com.example.data.GenreItem
import com.example.data.HeadphoneProfile
import com.example.data.LyricLine
import com.example.data.NoTrack
import com.example.data.OfflineManager
import com.example.data.PlaylistItem
import com.example.data.SavedCredentials
import com.example.data.SearchResult
import com.example.data.SettingsStore
import com.example.data.SleepTimerOption
import com.example.data.SoundWaveCatalog
import com.example.data.SubsonicApi
import com.example.data.SubsonicException
import com.example.data.TrackItem
import com.example.playback.AudioEffectsManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale

data class SoundWaveUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val backStack: List<AppScreen> = emptyList(),
    val showAutoEqModal: Boolean = false,
    val showScreenSwitcherSheet: Boolean = false,
    val showSleepTimerModal: Boolean = false,
    val sleepTimerRemainingSec: Int = 0,
    val sleepTimerOption: SleepTimerOption = SleepTimerOption.OFF,
    val downloadedTrackIds: Set<String> = emptySet(),
    val downloadingTrackIds: Set<String> = emptySet(),
    val downloadedTracks: List<TrackItem> = emptyList(),
    val downloadedSizeText: String = "0.0 MB",
    val trackMenuTarget: TrackItem? = null,
    /** One-shot text for a Toast (errors, confirmations). Cleared by consumeMessage(). */
    val message: String? = null,

    // Playback State
    val currentTrack: TrackItem = NoTrack,
    val isPlaying: Boolean = false,
    val playbackProgress: Float = 0f,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val isSeeking: Boolean = false,
    val queue: List<TrackItem> = emptyList(),
    val queueIndex: Int = -1,
    val playingFrom: String = "",
    val likedTrackIds: Set<String> = emptySet(),
    val isShuffleEnabled: Boolean = false,
    val isRepeatOne: Boolean = false,
    val nowPlayingTab: String = "TEXT", // "NASLEDUJÚCE", "TEXT", "PODOBNÉ"
    val lyrics: List<LyricLine> = emptyList(),
    val lyricsLoading: Boolean = false,
    val activeLyricIndex: Int = -1,
    val relatedTracks: List<TrackItem> = emptyList(),
    val relatedLoading: Boolean = false,
    val historyTracks: List<TrackItem> = emptyList(),

    // Home
    val homeFilter: String = "Všetko",
    val homeGenres: List<String> = emptyList(),
    val homeQuickPicks: List<TrackItem> = emptyList(),
    val homeListenAgain: List<AlbumItem> = emptyList(),
    val homeNewest: List<AlbumItem> = emptyList(),
    val homeLoading: Boolean = false,
    val homeError: String? = null,

    // Explore / Navidrome Search State
    val searchQuery: String = "",
    val exploreCategory: String = "Všetko",
    val activeAudiophileFilters: Set<String> = emptySet(),
    val exploreDefault: SearchResult = SearchResult(emptyList(), emptyList(), emptyList()),
    val searchResult: SearchResult = SearchResult(emptyList(), emptyList(), emptyList()),
    val exploreLoading: Boolean = false,
    val exploreError: String? = null,
    val genres: List<GenreItem> = emptyList(),
    val lastSyncTime: String = "Knižnica sa načíta zo servera",
    val isScanningServer: Boolean = false,

    // Detail page (album / playlist / artist / favourites / genre)
    val detail: CollectionDetail? = null,

    // Library State
    val libraryFilterChip: String = "Playlisty",
    val libraryIsGridView: Boolean = false,
    val libraryPlaylists: List<PlaylistItem> = emptyList(),
    val libraryAlbums: List<AlbumItem> = emptyList(),
    val libraryArtists: List<ArtistItem> = emptyList(),
    val favoriteTracks: List<TrackItem> = emptyList(),
    val libraryLoading: Boolean = false,
    val libraryError: String? = null,

    // Equalizer & DSP State (UI only for now, audio is not processed yet)
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
    val cacheSizeText: String = "Počítam veľkosť pamäte…",
    val isTestingDiagnostics: Boolean = false,
    val diagnosticResultVisible: Boolean = false,
    val diagnosticResultText: String = "",

    // Navidrome Server Login State
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val showTokenHint: Boolean = false,
    val showAdvancedAudioAccordion: Boolean = false,
    val loginOfflineCacheEnabled: Boolean = true,
    val loginPreferFlacEnabled: Boolean = true,
    val loginTranscodeOption: String = "Pôvodná kvalita (Direct Stream)",
    val isConnectingServer: Boolean = false,
    val isServerConnectedSuccess: Boolean = false,
    val isLoggedIn: Boolean = false,
    val connectionError: String? = null,
    val serverInfoText: String = "",
    val pingMs: Int = -1
) {
    /** "192.168.1.5:4533" – the server address without the scheme. */
    val serverHost: String
        get() = serverUrl.removePrefix("https://").removePrefix("http://").trimEnd('/')
}

/** What the Explore screen should show: server search results, or the default "discover" content. */
fun SoundWaveUiState.exploreContent(): SearchResult =
    if (searchQuery.isBlank()) exploreDefault else searchResult

private data class StreamProfile(val format: String?, val maxBitRate: Int?, val label: String)

class SoundWaveViewModel(app: Application) : AndroidViewModel(app) {

    private val settings = SettingsStore(app)
    private val offlineManager = OfflineManager(app)
    private var api: SubsonicApi? = null

    private val _uiState = MutableStateFlow(buildState(settings.loadCredentials()))
    val uiState: StateFlow<SoundWaveUiState> = _uiState.asStateFlow()

    private var controller: MediaController? = null
    private val pendingForController = mutableListOf<(MediaController) -> Unit>()

    private var searchJob: Job? = null
    private var seekJob: Job? = null
    private var lyricsJob: Job? = null
    private var relatedJob: Job? = null
    private var sleepTimerJob: Job? = null
    private var relatedLoadedFor: String = ""
    private var scrobbled = false

    init {
        connectController()
        startProgressLoop()
        refreshCacheSize()
        refreshDownloadedTracks()
        AudioEffectsManager.setBands(
            SoundWaveCatalog.eqPresets["Synthwave / Retrowave"]!!,
            -1.5f
        )
        AudioEffectsManager.setSubBassBoost(3.5f)
        AudioEffectsManager.setMasterEnabled(true)
        settings.loadCredentials()?.let { c ->
            api = SubsonicApi(c.url, c.username, c.token, c.salt)
            viewModelScope.launch { verifyAndLoad() }
        }
    }

    private fun buildState(creds: SavedCredentials?): SoundWaveUiState {
        val history = settings.loadHistory()
        val lastTrack = settings.loadLastTrack()
        val lastPos = settings.loadLastPositionMs()
        val lastQueue = settings.loadLastQueue()
        val lastQueueIdx = settings.loadLastQueueIndex()
        val hasSavedTrack = lastTrack != null && lastTrack.id.isNotBlank()
        val restoredQueue = if (lastQueue.isNotEmpty()) lastQueue else if (hasSavedTrack) listOf(lastTrack!!) else emptyList()
        val restoredTrack = if (hasSavedTrack) lastTrack!! else NoTrack
        val restoredDur = if (hasSavedTrack) restoredTrack.durationSec * 1000L else 0L
        val restoredProgress = if (restoredDur > 0L) (lastPos.toFloat() / restoredDur).coerceIn(0f, 1f) else 0f

        return SoundWaveUiState(
            currentScreen = if (creds == null) AppScreen.SERVER_LOGIN else AppScreen.HOME,
            serverUrl = creds?.url ?: settings.lastServerUrl(),
            username = creds?.username ?: settings.lastUsername(),
            isLoggedIn = creds != null,
            isServerConnectedSuccess = creds != null,
            serverInfoText = if (creds != null) "Pripájam sa…" else "",
            cellularTranscodingEnabled = settings.getBoolean(SettingsStore.KEY_CELLULAR_TRANSCODING, true),
            opusBitrateTarget = settings.getString(SettingsStore.KEY_OPUS_BITRATE, "256k"),
            backendCodec = settings.getString(SettingsStore.KEY_BACKEND_CODEC, "Opus (libopus)"),
            loginTranscodeOption = settings.getString(
                SettingsStore.KEY_LOGIN_TRANSCODE,
                "Pôvodná kvalita (Direct Stream)"
            ),
            currentTrack = restoredTrack,
            queue = restoredQueue,
            queueIndex = if (restoredQueue.isNotEmpty()) lastQueueIdx.coerceIn(0, restoredQueue.lastIndex) else -1,
            positionMs = lastPos,
            durationMs = restoredDur,
            playbackProgress = restoredProgress,
            historyTracks = history
        )
    }

    // ---------------------------------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------------------------------

    fun showMessage(text: String) {
        _uiState.update { it.copy(message = text) }
    }

    fun consumeMessage() {
        _uiState.update { it.copy(message = null) }
    }

    private fun launchApi(
        onError: (String) -> Unit = { showMessage(it) },
        block: suspend (SubsonicApi) -> Unit
    ): Job {
        val a = api
        if (a == null) {
            onError("Najprv sa prihlás k serveru.")
            return viewModelScope.launch { }
        }
        return viewModelScope.launch {
            try {
                block(a)
            } catch (e: CancellationException) {
                throw e
            } catch (e: SubsonicException) {
                onError(e.message ?: "Chyba servera.")
            } catch (e: Exception) {
                onError("Neočakávaná chyba: ${e.message ?: e.javaClass.simpleName}")
            }
        }
    }

    private suspend fun <T> safe(default: T, block: suspend () -> T): T =
        try {
            block()
        } catch (e: SubsonicException) {
            default
        }

    /** Keeps the heart icons in sync with what the server says about the loaded tracks. */
    private fun syncLikes(tracks: List<TrackItem>) {
        if (tracks.isEmpty()) return
        _uiState.update { state ->
            val ids = tracks.map { it.id }.toSet()
            val liked = tracks.filter { it.isLiked }.map { it.id }
            state.copy(likedTrackIds = (state.likedTrackIds - ids) + liked)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Navigation
    // ---------------------------------------------------------------------------------------------

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
        if (current.currentScreen != AppScreen.HOME && current.isLoggedIn) {
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

    // ---------------------------------------------------------------------------------------------
    // Server login / session
    // ---------------------------------------------------------------------------------------------

    private suspend fun verifyAndLoad() {
        val a = api ?: return
        try {
            val t0 = SystemClock.elapsedRealtime()
            val info = a.ping()
            val ms = (SystemClock.elapsedRealtime() - t0).toInt()
            _uiState.update {
                it.copy(serverInfoText = describeServer(info.serverType, info.serverVersion, info.apiVersion, info.openSubsonic), pingMs = ms)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: SubsonicException) {
            _uiState.update { it.copy(serverInfoText = "Server nedostupný", pingMs = -1) }
            if (e.code == 40) {
                // The saved token no longer works (password changed on the server).
                settings.clearCredentials()
                api = null
                _uiState.update {
                    it.copy(
                        currentScreen = AppScreen.SERVER_LOGIN,
                        isLoggedIn = false,
                        isServerConnectedSuccess = false,
                        connectionError = "Prihlásenie vypršalo, zadaj heslo znova."
                    )
                }
                return
            }
            showMessage(e.message ?: "Server nedostupný.")
        }
        loadAll()
    }

    private fun describeServer(type: String, version: String, apiVersion: String, openSubsonic: Boolean): String {
        val name = type.replaceFirstChar { c -> c.titlecase(Locale.ROOT) }
        return buildString {
            append(name)
            if (version.isNotBlank()) append(" ").append(version.substringBefore(" "))
            if (apiVersion.isNotBlank()) append(" • Subsonic API ").append(apiVersion)
            if (openSubsonic) append(" • OpenSubsonic")
        }
    }

    private fun loadAll() {
        loadHome()
        loadLibrary()
        loadExplore()
    }

    fun connectToServer() {
        val s = _uiState.value
        if (s.isConnectingServer) return
        val url = SubsonicApi.normalizeUrl(s.serverUrl)
        val user = s.username.trim()
        if (url.isBlank() || user.isBlank()) {
            _uiState.update { it.copy(connectionError = "Zadaj adresu servera a používateľské meno.") }
            return
        }
        _uiState.update {
            it.copy(isConnectingServer = true, isServerConnectedSuccess = false, connectionError = null)
        }
        viewModelScope.launch {
            try {
                val saved = settings.loadCredentials()
                val token: String
                val salt: String
                if (s.password.isNotEmpty()) {
                    salt = SubsonicApi.newSalt()
                    token = SubsonicApi.tokenFor(s.password, salt)
                } else if (saved != null && saved.url == url && saved.username == user) {
                    token = saved.token
                    salt = saved.salt
                } else {
                    throw SubsonicException("Zadaj heslo.")
                }
                val candidate = SubsonicApi(url, user, token, salt)
                val t0 = SystemClock.elapsedRealtime()
                val info = candidate.ping()
                val ms = (SystemClock.elapsedRealtime() - t0).toInt()

                settings.saveCredentials(SavedCredentials(url, user, token, salt))
                api = candidate
                _uiState.update {
                    it.copy(
                        isConnectingServer = false,
                        isServerConnectedSuccess = true,
                        isLoggedIn = true,
                        serverUrl = url,
                        username = user,
                        password = "",
                        connectionError = null,
                        pingMs = ms,
                        serverInfoText = describeServer(info.serverType, info.serverVersion, info.apiVersion, info.openSubsonic)
                    )
                }
                loadAll()
                delay(700)
                _uiState.update {
                    if (it.currentScreen == AppScreen.SERVER_LOGIN) {
                        it.copy(currentScreen = AppScreen.HOME, backStack = emptyList())
                    } else it
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: SubsonicException) {
                _uiState.update {
                    it.copy(isConnectingServer = false, isServerConnectedSuccess = false, connectionError = e.message)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isConnectingServer = false,
                        isServerConnectedSuccess = false,
                        connectionError = "Neočakávaná chyba: ${e.message ?: e.javaClass.simpleName}"
                    )
                }
            }
        }
    }

    fun logout() {
        settings.clearCredentials()
        api = null
        withController {
            it.stop()
            it.clearMediaItems()
        }
        val prev = _uiState.value
        _uiState.value = buildState(null).copy(serverUrl = prev.serverUrl, username = prev.username)
    }

    // ---------------------------------------------------------------------------------------------
    // Loading content
    // ---------------------------------------------------------------------------------------------

    fun loadHome() {
        val genre = _uiState.value.homeFilter.takeIf { it != "Všetko" && it != "All" }
        _uiState.update { it.copy(homeLoading = true, homeError = null) }
        launchApi(onError = { msg -> _uiState.update { it.copy(homeLoading = false, homeError = msg) } }) { a ->
            coroutineScope {
                val picks = async { a.randomSongs(12, genre) }
                val again = async {
                    if (genre == null) a.albumList("recent", 14) else a.albumList("byGenre", 14, genre = genre)
                }
                val fresh = async { a.albumList("newest", 14) }
                val genreList = async { safe(emptyList<GenreItem>()) { a.genres() } }
                val pls = async { safe<List<PlaylistItem>?>(null) { a.playlists() } }

                val pickList = picks.await()
                val againList = again.await().ifEmpty { safe(emptyList<AlbumItem>()) { a.albumList("random", 14) } }
                val freshList = fresh.await()
                val genresLoaded = genreList.await()
                val plsLoaded = pls.await()
                _uiState.update { st ->
                    st.copy(
                        homeLoading = false,
                        homeError = null,
                        homeQuickPicks = pickList,
                        homeListenAgain = againList,
                        homeNewest = freshList,
                        genres = if (genresLoaded.isNotEmpty()) genresLoaded else st.genres,
                        homeGenres = if (genresLoaded.isNotEmpty()) genresLoaded.take(8).map { it.name } else st.homeGenres,
                        libraryPlaylists = plsLoaded ?: st.libraryPlaylists
                    )
                }
                syncLikes(pickList)
            }
        }
    }

    fun loadLibrary() {
        _uiState.update { it.copy(libraryLoading = true, libraryError = null) }
        launchApi(onError = { msg -> _uiState.update { it.copy(libraryLoading = false, libraryError = msg) } }) { a ->
            coroutineScope {
                val pls = async { a.playlists() }
                val artists = async { a.artists() }
                val fav = async { safe(emptyList<TrackItem>()) { a.starredTracks() } }
                val albums = async {
                    val all = mutableListOf<AlbumItem>()
                    var offset = 0
                    while (offset < 4000) {
                        val page = a.albumList("alphabeticalByName", 500, offset)
                        all += page
                        if (page.size < 500) break
                        offset += 500
                    }
                    all.toList()
                }
                val plsLoaded = pls.await()
                val artistsLoaded = artists.await()
                val favLoaded = fav.await()
                val albumsLoaded = albums.await()
                _uiState.update {
                    it.copy(
                        libraryLoading = false,
                        libraryError = null,
                        libraryPlaylists = plsLoaded,
                        libraryArtists = artistsLoaded,
                        libraryAlbums = albumsLoaded,
                        favoriteTracks = favLoaded
                    )
                }
                syncLikes(favLoaded)
            }
        }
    }

    fun loadExplore() {
        launchApi(onError = { msg -> _uiState.update { it.copy(exploreError = msg) } }) { a ->
            coroutineScope {
                val tracks = async { a.randomSongs(30) }
                val albums = async { a.albumList("newest", 12) }
                val artists = async { safe(emptyList<ArtistItem>()) { a.artists() } }
                val genreList = async { safe(emptyList<GenreItem>()) { a.genres() } }
                val t = tracks.await()
                val al = albums.await()
                val ar = artists.await()
                val ge = genreList.await()
                _uiState.update { st ->
                    st.copy(
                        exploreError = null,
                        exploreDefault = SearchResult(ar.shuffled().take(12), al, t),
                        genres = if (ge.isNotEmpty()) ge else st.genres
                    )
                }
                syncLikes(t)
            }
        }
    }

    fun openCollection(id: String) {
        val kind = when {
            id.startsWith("pl:") -> "Playlist"
            id.startsWith("al:") -> "Album"
            id.startsWith("ar:") -> "Interpret"
            id.startsWith("ge:") -> "Žáner"
            else -> "Zbierka"
        }
        _uiState.update { st ->
            val stack = if (st.currentScreen == AppScreen.PLAYLIST_DETAIL) st.backStack else st.backStack + st.currentScreen
            st.copy(
                detail = CollectionDetail(
                    id = id, kind = kind, title = "Načítavam…", subtitle = "", coverUrl = "",
                    metaLine = "", tracks = emptyList(), isLoading = true
                ),
                currentScreen = AppScreen.PLAYLIST_DETAIL,
                backStack = stack,
                showScreenSwitcherSheet = false
            )
        }
        launchApi(onError = { msg ->
            _uiState.update { st ->
                st.copy(detail = st.detail?.copy(isLoading = false, title = "Nepodarilo sa načítať", error = msg))
            }
        }) { a ->
            val result = when {
                id.startsWith("pl:") -> a.playlistDetail(id.removePrefix("pl:"))
                id.startsWith("al:") -> a.albumDetail(id.removePrefix("al:"))
                id.startsWith("ar:") -> a.artistDetail(id.removePrefix("ar:"))
                id.startsWith("ge:") -> a.genreDetail(id.removePrefix("ge:"))
                else -> a.favoritesDetail()
            }
            _uiState.update { st ->
                // Ignore a late answer if the user already opened something else.
                if (st.detail?.id == id) st.copy(detail = result) else st
            }
            syncLikes(result.tracks)
        }
    }

    fun openFavorites() = openCollection("fav")

    // ---------------------------------------------------------------------------------------------
    // Playback
    // ---------------------------------------------------------------------------------------------

    private fun connectController() {
        val ctx = getApplication<Application>()
        val token = SessionToken(ctx, ComponentName(ctx, com.example.playback.PlaybackService::class.java))
        val future = MediaController.Builder(ctx, token).buildAsync()
        future.addListener({
            try {
                val c = future.get()
                controller = c
                c.addListener(playerListener)
                if (c.mediaItemCount > 0) {
                    syncFromController(c)
                } else {
                    // Controller is empty (e.g. freshly started after phone restart).
                    // Restore the saved queue and position into ExoPlayer so user can resume immediately!
                    val state = _uiState.value
                    if (state.currentTrack.id.isNotBlank() && state.queue.isNotEmpty()) {
                        val profile = currentStreamProfile()
                        val items = state.queue.map { mediaItemFor(api, it, profile) }
                        val idx = state.queueIndex.coerceIn(0, items.lastIndex)
                        val pos = state.positionMs
                        c.setMediaItems(items, idx, pos)
                        c.prepare()
                        c.pause()
                    }
                }
                val pending = pendingForController.toList()
                pendingForController.clear()
                pending.forEach { it(c) }
            } catch (e: Exception) {
                showMessage("Prehrávač sa nepodarilo spustiť.")
            }
        }, ContextCompat.getMainExecutor(ctx))
    }

    private fun withController(action: (MediaController) -> Unit) {
        val c = controller
        if (c != null) action(c) else pendingForController += action
    }

    /** The service can outlive the Activity; pick up whatever it is playing. */
    private fun syncFromController(c: MediaController) {
        if (c.mediaItemCount == 0) return
        val items = (0 until c.mediaItemCount).map { c.getMediaItemAt(it) }
        val queue = items.map { trackFromMediaItem(it) }
        val idx = c.currentMediaItemIndex.coerceIn(0, queue.lastIndex)
        _uiState.update {
            it.copy(
                queue = queue,
                queueIndex = idx,
                currentTrack = queue[idx],
                isPlaying = c.isPlaying,
                isShuffleEnabled = c.shuffleModeEnabled,
                isRepeatOne = c.repeatMode == Player.REPEAT_MODE_ONE,
                durationMs = queue[idx].durationSec * 1000L
            )
        }
        loadLyrics(queue[idx])
    }

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _uiState.update { it.copy(isPlaying = isPlaying) }
            if (!isPlaying) {
                // When paused, immediately save position to disk so phone shutdown/reboot retains it
                controller?.let { c ->
                    val pos = c.currentPosition
                    val s = _uiState.value
                    if (s.currentTrack.id.isNotBlank()) {
                        settings.savePlaybackState(s.currentTrack, pos, s.queue, s.queueIndex)
                    }
                }
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            onCurrentItemChanged()
            if (_uiState.value.sleepTimerOption == SleepTimerOption.END_OF_TRACK &&
                reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO
            ) {
                withController { it.pause() }
                _uiState.update { it.copy(sleepTimerOption = SleepTimerOption.OFF, sleepTimerRemainingSec = 0) }
                showMessage("Časovač spánku: Prehrávanie bolo pozastavené po skončení skladby.")
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            val c = controller ?: return
            if (playbackState == Player.STATE_READY && c.duration != C.TIME_UNSET && c.duration > 0) {
                _uiState.update { it.copy(durationMs = c.duration) }
            }
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _uiState.update { it.copy(isShuffleEnabled = shuffleModeEnabled) }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            _uiState.update { it.copy(isRepeatOne = repeatMode == Player.REPEAT_MODE_ONE) }
        }

        override fun onPlayerError(error: PlaybackException) {
            showMessage("Chyba prehrávania (${error.errorCodeName}). Skontroluj spojenie so serverom.")
        }
    }

    private fun onCurrentItemChanged() {
        val c = controller ?: return
        val item = c.currentMediaItem
        if (item == null) {
            _uiState.update { it.copy(currentTrack = NoTrack, queueIndex = -1, isPlaying = false) }
            return
        }
        val idx = c.currentMediaItemIndex
        val track = _uiState.value.queue.getOrNull(idx)?.takeIf { it.id == item.mediaId }
            ?: trackFromMediaItem(item)
        scrobbled = false
        if (track.id.isNotBlank()) {
            settings.addTrackToHistory(track)
            val hist = settings.loadHistory()
            settings.savePlaybackState(track, 0L, _uiState.value.queue, idx)
            _uiState.update {
                it.copy(
                    currentTrack = track,
                    queueIndex = idx,
                    historyTracks = hist,
                    playbackProgress = 0f,
                    positionMs = 0L,
                    durationMs = track.durationSec * 1000L,
                    lyrics = emptyList(),
                    activeLyricIndex = -1,
                    relatedTracks = emptyList()
                )
            }
            launchApi(onError = { }) { it.scrobble(track.id, submission = false) }
        } else {
            _uiState.update {
                it.copy(
                    currentTrack = track,
                    queueIndex = idx,
                    playbackProgress = 0f,
                    positionMs = 0L,
                    durationMs = track.durationSec * 1000L,
                    lyrics = emptyList(),
                    activeLyricIndex = -1,
                    relatedTracks = emptyList()
                )
            }
        }
        loadLyrics(track)
        if (_uiState.value.nowPlayingTab in setOf("PODOBNÉ", "RELATED")) loadRelated(track)
    }

    private fun startProgressLoop() {
        viewModelScope.launch {
            var loopTicks = 0
            while (isActive) {
                val c = controller
                val isPlaying = c?.isPlaying == true
                // Battery Optimization: When paused or idle, sleep longer (1200ms) to reduce CPU wakeups.
                // When actively playing audio, tick every 500ms for smooth progress bar and lyrics sync.
                delay(if (isPlaying) 500L else 1200L)
                if (c == null || c.mediaItemCount == 0 || !isPlaying) continue
                val s = _uiState.value
                if (s.isSeeking) continue
                val pos = c.currentPosition
                val dur = if (c.duration != C.TIME_UNSET && c.duration > 0) c.duration else s.currentTrack.durationSec * 1000L
                val progress = if (dur > 0) (pos.toFloat() / dur).coerceIn(0f, 1f) else 0f
                _uiState.update {
                    it.copy(
                        positionMs = pos,
                        durationMs = dur,
                        playbackProgress = progress,
                        activeLyricIndex = lyricIndexAt(it.lyrics, pos)
                    )
                }
                maybeScrobble(s.currentTrack, pos, dur)

                // Periodically persist playback position every ~5 seconds (10 ticks) so reboot preserves exact spot
                loopTicks++
                if (loopTicks % 10 == 0) {
                    settings.savePositionOnly(pos)
                }
            }
        }
    }

    private fun lyricIndexAt(lines: List<LyricLine>, posMs: Long): Int {
        if (lines.isEmpty() || lines.first().startMs < 0) return -1
        return lines.indexOfLast { it.startMs <= posMs }
    }

    private fun maybeScrobble(track: TrackItem, pos: Long, dur: Long) {
        if (scrobbled || track.id.isBlank() || dur < 30_000L) return
        if (pos >= minOf(dur / 2, 240_000L)) {
            scrobbled = true
            launchApi(onError = { }) { it.scrobble(track.id, submission = true) }
        }
    }

    private fun loadLyrics(track: TrackItem) {
        lyricsJob?.cancel()
        if (track.id.isBlank()) return
        _uiState.update { it.copy(lyricsLoading = true) }
        lyricsJob = launchApi(onError = { _uiState.update { s -> s.copy(lyricsLoading = false) } }) { a ->
            val lines = a.lyrics(track)
            _uiState.update { st ->
                if (st.currentTrack.id == track.id) {
                    st.copy(lyrics = lines, lyricsLoading = false, activeLyricIndex = lyricIndexAt(lines, st.positionMs))
                } else st
            }
        }
    }

    private fun loadRelated(track: TrackItem) {
        if (track.id.isBlank() || relatedLoadedFor == track.id) return
        relatedLoadedFor = track.id
        relatedJob?.cancel()
        _uiState.update { it.copy(relatedLoading = true) }
        relatedJob = launchApi(onError = { _uiState.update { s -> s.copy(relatedLoading = false) } }) { a ->
            val similar = a.similarSongs(track.id, 20)
            _uiState.update { st ->
                if (st.currentTrack.id == track.id) st.copy(relatedTracks = similar, relatedLoading = false) else st
            }
            syncLikes(similar)
        }
    }

    private fun isOnCellular(): Boolean {
        val cm = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) &&
            !caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) &&
            !caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    /** Which codec / bitrate to request from the server, based on the network and the user's settings. */
    private fun currentStreamProfile(): StreamProfile {
        val s = _uiState.value
        if (isOnCellular() && s.cellularTranscodingEnabled) {
            val rate = when (s.opusBitrateTarget) {
                "128k" -> 128
                "256k" -> 256
                "320k" -> 320
                else -> 192
            }
            return when (s.backendCodec) {
                "FLAC Stream" -> StreamProfile("flac", null, "Mobilné dáta • FLAC")
                "MP3 (LAME)" -> StreamProfile("mp3", minOf(rate, 320), "Mobilné dáta • MP3 ${minOf(rate, 320)} kbps")
                "Ogg Vorbis" -> StreamProfile("ogg", rate, "Mobilné dáta • Ogg Vorbis $rate kbps")
                else -> StreamProfile("opus", rate, "Mobilné dáta • Opus $rate kbps")
            }
        }
        return when (s.loginTranscodeOption) {
            "Vysoká (MP3 320 kbps)" -> StreamProfile("mp3", 320, "Wi‑Fi • MP3 320 kbps")
            "Mobilné dáta (Opus 192 kbps)" -> StreamProfile("opus", 192, "Wi‑Fi • Opus 192 kbps")
            "Úspora dát (Opus 128 kbps)" -> StreamProfile("opus", 128, "Wi‑Fi • Opus 128 kbps")
            else -> StreamProfile("raw", null, "Pôvodná kvalita (bez prekódovania)")
        }
    }

    private fun mediaItemFor(a: SubsonicApi?, t: TrackItem, profile: StreamProfile): MediaItem {
        val extras = Bundle().apply {
            putString("badge", t.badge)
            putString("techLine", t.techLine)
            putString("albumId", t.albumId)
            putString("artistId", t.artistId)
            putString("coverArtId", t.coverArtId)
            putString("suffix", t.suffix)
            putInt("durationSec", t.durationSec)
            putBoolean("hiRes", t.isHiResBadgeCyan)
        }
        val metadata = MediaMetadata.Builder()
            .setTitle(t.title)
            .setArtist(t.artist)
            .setAlbumTitle(t.album)
            .setArtworkUri(if (t.imageUrl.isBlank()) null else Uri.parse(t.imageUrl))
            .setExtras(extras)
            .build()

        val localPath = offlineManager.getLocalFileUri(t.id)
        val uri = if (localPath != null) {
            Uri.fromFile(File(localPath))
        } else if (a != null) {
            Uri.parse(a.streamUrl(t.id, profile.format, profile.maxBitRate))
        } else {
            Uri.EMPTY
        }

        return MediaItem.Builder()
            .setMediaId(t.id)
            .setUri(uri)
            .setMediaMetadata(metadata)
            .build()
    }

    private fun trackFromMediaItem(item: MediaItem): TrackItem {
        val md = item.mediaMetadata
        val ex = md.extras
        val durationSec = ex?.getInt("durationSec") ?: 0
        return TrackItem(
            id = item.mediaId,
            title = md.title?.toString().orEmpty(),
            artist = md.artist?.toString().orEmpty(),
            album = md.albumTitle?.toString().orEmpty(),
            duration = com.example.data.formatDuration(durationSec),
            badge = ex?.getString("badge").orEmpty(),
            imageUrl = md.artworkUri?.toString().orEmpty(),
            isHiResBadgeCyan = ex?.getBoolean("hiRes") ?: true,
            albumId = ex?.getString("albumId").orEmpty(),
            artistId = ex?.getString("artistId").orEmpty(),
            durationSec = durationSec,
            suffix = ex?.getString("suffix").orEmpty(),
            coverArtId = ex?.getString("coverArtId").orEmpty(),
            techLine = ex?.getString("techLine").orEmpty()
        )
    }

    private fun startQueue(list: List<TrackItem>, startIndex: Int, from: String, shuffle: Boolean?) {
        val a = api
        val isOfflinePlayback = list.all { offlineManager.isDownloaded(it.id) }
        if (a == null && !isOfflinePlayback) {
            showMessage("Najprv sa prihlás k serveru alebo prehraj stiahnuté skladby.")
            return
        }
        if (list.isEmpty() || startIndex !in list.indices) return
        // Keep the queue a sane size for very long lists.
        val window = list.drop(startIndex).take(500)
        val profile = currentStreamProfile()
        val items = window.map { mediaItemFor(a, it, profile) }
        scrobbled = false
        relatedLoadedFor = ""
        val track = window[0]
        settings.addTrackToHistory(track)
        val hist = settings.loadHistory()
        settings.savePlaybackState(track, 0L, window, 0)
        _uiState.update {
            it.copy(
                queue = window,
                queueIndex = 0,
                currentTrack = track,
                historyTracks = hist,
                playingFrom = from,
                isPlaying = true,
                playbackProgress = 0f,
                positionMs = 0L,
                durationMs = track.durationSec * 1000L,
                lyrics = emptyList(),
                activeLyricIndex = -1,
                relatedTracks = emptyList()
            )
        }
        withController { c ->
            if (shuffle != null) c.shuffleModeEnabled = shuffle
            c.setMediaItems(items, 0, 0L)
            c.prepare()
            c.play()
        }
    }

    /**
     * Plays [track]. When [context] (the list the user tapped it in) contains the track, the whole
     * list becomes the queue so next/previous work as expected.
     */
    fun playTrack(
        track: TrackItem,
        openFullPlayer: Boolean = false,
        context: List<TrackItem> = emptyList(),
        from: String = ""
    ) {
        val list = if (context.any { it.id == track.id }) context else listOf(track)
        val idx = list.indexOfFirst { it.id == track.id }
        startQueue(list, idx, from, null)
        if (openFullPlayer) navigateTo(AppScreen.NOW_PLAYING)
    }

    fun playAll(list: List<TrackItem>, shuffle: Boolean = false, from: String = "") {
        if (list.isEmpty()) return
        val start = if (shuffle) list.indices.random() else 0
        // With shuffle on, the chosen start track plays first and the rest follows in random order.
        if (shuffle) {
            startQueue(list, 0, from, true)
            withController { c ->
                if (start > 0) c.seekToDefaultPosition(start.coerceAtMost(c.mediaItemCount - 1))
            }
        } else {
            startQueue(list, 0, from, false)
        }
    }

    fun playQueueIndex(index: Int) {
        withController { c ->
            if (index in 0 until c.mediaItemCount) {
                c.seekToDefaultPosition(index)
                c.play()
            }
        }
    }

    fun togglePlayPause() {
        withController { c ->
            if (c.mediaItemCount == 0) return@withController
            if (c.isPlaying) {
                c.pause()
                val pos = c.currentPosition
                settings.savePositionOnly(pos)
            } else {
                if (c.playbackState == Player.STATE_IDLE) c.prepare()
                c.play()
            }
        }
    }

    fun skipToNextTrack() {
        withController { c -> if (c.hasNextMediaItem()) c.seekToNextMediaItem() }
    }

    fun skipToPreviousTrack() {
        withController { c -> c.seekToPrevious() }
    }

    /** Called while the user drags the progress bar; the actual seek happens when they pause for a moment. */
    fun setPlaybackProgress(progress: Float) {
        val p = progress.coerceIn(0f, 1f)
        val dur = _uiState.value.durationMs
        val pos = (dur * p).toLong()
        _uiState.update {
            it.copy(isSeeking = true, playbackProgress = p, positionMs = pos, activeLyricIndex = lyricIndexAt(it.lyrics, pos))
        }
        seekJob?.cancel()
        seekJob = viewModelScope.launch {
            delay(250)
            withController { c ->
                c.seekTo(pos)
                settings.savePositionOnly(pos)
            }
            _uiState.update { it.copy(isSeeking = false) }
        }
    }

    fun toggleTrackLike(trackId: String) {
        if (trackId.isBlank()) return
        val wasLiked = trackId in _uiState.value.likedTrackIds
        _uiState.update {
            it.copy(likedTrackIds = if (wasLiked) it.likedTrackIds - trackId else it.likedTrackIds + trackId)
        }
        launchApi(onError = { msg ->
            _uiState.update {
                it.copy(likedTrackIds = if (wasLiked) it.likedTrackIds + trackId else it.likedTrackIds - trackId)
            }
            showMessage(msg)
        }) { a ->
            if (wasLiked) a.unstar(trackId) else a.star(trackId)
            val fav = safe<List<TrackItem>?>(null) { a.starredTracks() }
            if (fav != null) _uiState.update { it.copy(favoriteTracks = fav) }
        }
    }

    fun toggleShuffle() {
        withController { c -> c.shuffleModeEnabled = !c.shuffleModeEnabled }
    }

    fun toggleRepeatOne() {
        withController { c ->
            c.repeatMode = if (c.repeatMode == Player.REPEAT_MODE_ONE) Player.REPEAT_MODE_OFF else Player.REPEAT_MODE_ONE
        }
    }

    fun setNowPlayingTab(tab: String) {
        _uiState.update { it.copy(nowPlayingTab = tab) }
        if (tab in setOf("PODOBNÉ", "RELATED")) loadRelated(_uiState.value.currentTrack)
    }

    /** Tapping a synced lyric line jumps to that line. */
    fun setActiveLyricIndex(index: Int) {
        val line = _uiState.value.lyrics.getOrNull(index) ?: return
        if (line.startMs < 0) return
        withController { it.seekTo(line.startMs) }
        _uiState.update { it.copy(activeLyricIndex = index, positionMs = line.startMs) }
    }

    /** "Náhodný mix" on the Library screen: random songs from the whole server library. */
    fun playRandomMix() {
        launchApi { a ->
            val songs = a.randomSongs(50)
            if (songs.isEmpty()) {
                showMessage("Na serveri sa nenašli žiadne skladby.")
            } else {
                syncLikes(songs)
                playAll(songs, shuffle = false, from = "Náhodný mix")
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Home / Explore / Library UI actions
    // ---------------------------------------------------------------------------------------------

    fun setHomeFilter(filter: String) {
        _uiState.update { it.copy(homeFilter = filter) }
        loadHome()
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query, exploreError = null) }
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(exploreLoading = false) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(350)
            val a = api ?: return@launch
            _uiState.update { it.copy(exploreLoading = true) }
            try {
                val result = a.search(query.trim())
                _uiState.update { it.copy(searchResult = result, exploreLoading = false, exploreError = null) }
                syncLikes(result.tracks)
            } catch (e: CancellationException) {
                throw e
            } catch (e: SubsonicException) {
                _uiState.update { it.copy(exploreLoading = false, exploreError = e.message) }
            }
        }
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
        _uiState.update { it.copy(activeAudiophileFilters = emptySet()) }
    }

    fun triggerServerScan() {
        if (_uiState.value.isScanningServer) return
        _uiState.update { it.copy(isScanningServer = true, lastSyncTime = "Spúšťam skenovanie knižnice…") }
        launchApi(onError = { msg ->
            _uiState.update { it.copy(isScanningServer = false, lastSyncTime = "Skenovanie zlyhalo: $msg") }
        }) { a ->
            var st = a.startScan()
            var guard = 0
            while (st.scanning && guard < 300) {
                _uiState.update { it.copy(lastSyncTime = "Skenujem knižnicu… ${st.count} súborov") }
                delay(1000)
                st = a.scanStatus()
                guard++
            }
            _uiState.update {
                it.copy(isScanningServer = false, lastSyncTime = "Skenovanie dokončené · ${st.count} súborov")
            }
            loadAll()
        }
    }

    fun setLibraryFilterChip(chip: String) {
        _uiState.update { it.copy(libraryFilterChip = chip) }
    }

    fun toggleLibraryGridView() {
        _uiState.update { it.copy(libraryIsGridView = !it.libraryIsGridView) }
    }

    fun createNewPlaylist(name: String) {
        val clean = name.trim()
        if (clean.isEmpty()) {
            showMessage("Zadaj názov playlistu.")
            return
        }
        launchApi { a ->
            a.createPlaylist(clean)
            val pls = a.playlists()
            _uiState.update { it.copy(libraryPlaylists = pls) }
            showMessage("Playlist „$clean“ bol vytvorený.")
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Equalizer & DSP (Hardware AudioEffects)
    // ---------------------------------------------------------------------------------------------

    fun toggleEqMaster(enabled: Boolean) {
        _uiState.update { it.copy(eqMasterEnabled = enabled) }
        AudioEffectsManager.setMasterEnabled(enabled)
    }

    fun setPreampDb(value: Float) {
        val rounded = (Math.round(value * 2f) / 2f).coerceIn(-12f, 12f)
        _uiState.update { it.copy(preampDb = rounded) }
        AudioEffectsManager.setBands(_uiState.value.eqBands, rounded)
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
        AudioEffectsManager.setBands(_uiState.value.eqBands, _uiState.value.preampDb)
    }

    fun resetEqFlat() {
        val flat = List(10) { 0f }
        _uiState.update {
            it.copy(
                eqBands = flat,
                activePresetName = "Audiofil Flat (Referenčný)"
            )
        }
        AudioEffectsManager.setBands(flat, _uiState.value.preampDb)
    }

    fun invertEqBands() {
        val inverted = _uiState.value.eqBands.map { (-it).coerceIn(-12f, 12f) }
        _uiState.update { state ->
            state.copy(
                eqBands = inverted,
                activePresetName = "Invertovaný"
            )
        }
        AudioEffectsManager.setBands(inverted, _uiState.value.preampDb)
    }

    fun selectEqPreset(presetName: String) {
        val values = SoundWaveCatalog.eqPresets[presetName] ?: return
        _uiState.update {
            it.copy(
                eqBands = values,
                activePresetName = presetName
            )
        }
        AudioEffectsManager.setBands(values, _uiState.value.preampDb)
    }

    fun setSubBassBoostDb(value: Float) {
        val rounded = (Math.round(value * 2f) / 2f).coerceIn(0f, 10f)
        _uiState.update { it.copy(subBassBoostDb = rounded) }
        AudioEffectsManager.setSubBassBoost(rounded)
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
        AudioEffectsManager.setBands(_uiState.value.eqBands, if (_uiState.value.autoPreampEnabled) preampVal else _uiState.value.preampDb)
    }

    // ---------------------------------------------------------------------------------------------
    // Sleep Timer
    // ---------------------------------------------------------------------------------------------

    fun setShowSleepTimerModal(show: Boolean) {
        _uiState.update { it.copy(showSleepTimerModal = show) }
    }

    fun setSleepTimer(option: SleepTimerOption) {
        sleepTimerJob?.cancel()
        _uiState.update { it.copy(sleepTimerOption = option, showSleepTimerModal = false) }

        if (option == SleepTimerOption.OFF) {
            _uiState.update { it.copy(sleepTimerRemainingSec = 0) }
            showMessage("Časovač spánku bol vypnutý.")
            return
        }

        if (option == SleepTimerOption.END_OF_TRACK) {
            _uiState.update { it.copy(sleepTimerRemainingSec = -1) }
            showMessage("Časovač spánku: Prehrávanie sa zastaví na konci tejto skladby.")
            return
        }

        val totalSec = option.minutes * 60
        _uiState.update { it.copy(sleepTimerRemainingSec = totalSec) }
        showMessage("Časovač spánku nastavený na ${option.label}.")

        sleepTimerJob = viewModelScope.launch {
            var rem = totalSec
            while (rem > 0 && isActive) {
                delay(1000)
                rem--
                _uiState.update { it.copy(sleepTimerRemainingSec = rem) }
            }
            if (rem <= 0) {
                withController { it.pause() }
                _uiState.update { it.copy(sleepTimerOption = SleepTimerOption.OFF, sleepTimerRemainingSec = 0) }
                showMessage("Časovač spánku: Čas vypršal, prehrávanie bolo pozastavené.")
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Queue Management
    // ---------------------------------------------------------------------------------------------

    fun playNext(track: TrackItem) {
        val a = api
        val profile = currentStreamProfile()
        val mediaItem = mediaItemFor(a, track, profile)
        withController { c ->
            val insertIdx = if (c.mediaItemCount == 0) 0 else (c.currentMediaItemIndex + 1).coerceAtMost(c.mediaItemCount)
            c.addMediaItem(insertIdx, mediaItem)
            _uiState.update { state ->
                val q = state.queue.toMutableList()
                if (insertIdx in 0..q.size) {
                    q.add(insertIdx, track)
                } else {
                    q.add(track)
                }
                state.copy(queue = q)
            }
            showMessage("Skladba „${track.title}“ bude prehraná ako nasledujúca.")
        }
    }

    fun addToQueue(track: TrackItem) {
        val a = api
        val profile = currentStreamProfile()
        val mediaItem = mediaItemFor(a, track, profile)
        withController { c ->
            c.addMediaItem(mediaItem)
            _uiState.update { state ->
                state.copy(queue = state.queue + track)
            }
            showMessage("Skladba „${track.title}“ bola pridaná do poradia.")
        }
    }

    fun removeFromQueue(index: Int) {
        withController { c ->
            if (index in 0 until c.mediaItemCount) {
                c.removeMediaItem(index)
                _uiState.update { state ->
                    val q = state.queue.toMutableList()
                    if (index in q.indices) {
                        q.removeAt(index)
                    }
                    val newIdx = c.currentMediaItemIndex
                    state.copy(queue = q, queueIndex = newIdx)
                }
            }
        }
    }

    fun clearUpcomingQueue() {
        withController { c ->
            val curr = c.currentMediaItemIndex
            val count = c.mediaItemCount
            if (count > curr + 1) {
                for (i in (count - 1) downTo (curr + 1)) {
                    c.removeMediaItem(i)
                }
                _uiState.update { state ->
                    state.copy(queue = state.queue.take(curr + 1))
                }
                showMessage("Nasledujúce skladby v poradí boli vymazané.")
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Track Actions Sheet
    // ---------------------------------------------------------------------------------------------

    fun setTrackMenuTarget(track: TrackItem?) {
        _uiState.update { it.copy(trackMenuTarget = track) }
    }

    // ---------------------------------------------------------------------------------------------
    // Offline Storage & Downloads
    // ---------------------------------------------------------------------------------------------

    fun refreshDownloadedTracks() {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) { offlineManager.getAllDownloadedTracks() }
            val bytes = withContext(Dispatchers.IO) { offlineManager.getDownloadedTotalSizeBytes() }
            val formatted = formatMb(bytes)
            _uiState.update {
                it.copy(
                    downloadedTracks = list,
                    downloadedTrackIds = list.map { t -> t.id }.toSet(),
                    downloadedSizeText = formatted
                )
            }
        }
    }

    fun downloadTrack(track: TrackItem) {
        if (offlineManager.isDownloaded(track.id)) {
            showMessage("Skladba „${track.title}“ už je stiahnutá.")
            return
        }
        val a = api
        if (a == null) {
            showMessage("Pre stiahnutie skladby sa najprv prihlás k serveru.")
            return
        }
        _uiState.update { it.copy(downloadingTrackIds = it.downloadingTrackIds + track.id) }
        viewModelScope.launch {
            val url = a.downloadUrl(track.id)
            val success = offlineManager.downloadTrack(url, track)
            _uiState.update { it.copy(downloadingTrackIds = it.downloadingTrackIds - track.id) }
            if (success) {
                showMessage("Skladba „${track.title}“ bola stiahnutá na offline počúvanie.")
                refreshDownloadedTracks()
            } else {
                showMessage("Sťahovanie skladby „${track.title}“ zlyhalo. Skontroluj pripojenie.")
            }
        }
    }

    fun deleteDownloadedTrack(trackId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { offlineManager.deleteDownloadedTrack(trackId) }
            refreshDownloadedTracks()
            showMessage("Stiahnutá skladba bola vymazaná z úložiska.")
        }
    }

    fun clearAllDownloads() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { offlineManager.clearAllDownloads() }
            refreshDownloadedTracks()
            showMessage("Všetky stiahnuté skladby boli vymazané z úložiska.")
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Streaming Quality & Server Settings
    // ---------------------------------------------------------------------------------------------

    fun toggleCellularTranscoding() {
        val next = !_uiState.value.cellularTranscodingEnabled
        settings.putBoolean(SettingsStore.KEY_CELLULAR_TRANSCODING, next)
        _uiState.update { it.copy(cellularTranscodingEnabled = next) }
    }

    fun setOpusBitrateTarget(target: String) {
        settings.putString(SettingsStore.KEY_OPUS_BITRATE, target)
        _uiState.update { it.copy(opusBitrateTarget = target) }
    }

    fun setBackendCodec(codec: String) {
        settings.putString(SettingsStore.KEY_BACKEND_CODEC, codec)
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

    private fun dirSize(f: File): Long =
        if (f.isFile) f.length() else (f.listFiles()?.sumOf { dirSize(it) } ?: 0L)

    private fun formatMb(bytes: Long): String =
        String.format(Locale.US, "%.1f MB", bytes / (1024.0 * 1024.0))

    private fun refreshCacheSize() {
        viewModelScope.launch {
            val size = withContext(Dispatchers.IO) { dirSize(getApplication<Application>().cacheDir) }
            _uiState.update { it.copy(cacheSizeText = "Vyrovnávacia pamäť (obaly albumov) zaberá ${formatMb(size)}") }
        }
    }

    fun clearStreamCache() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getApplication<Application>().cacheDir.listFiles()?.forEach { it.deleteRecursively() }
            }
            refreshCacheSize()
            showMessage("Vyrovnávacia pamäť bola vymazaná.")
        }
    }

    fun runTranscodingDiagnostics() {
        if (_uiState.value.isTestingDiagnostics) return
        _uiState.update { it.copy(isTestingDiagnostics = true, diagnosticResultVisible = false) }
        launchApi(onError = { msg ->
            _uiState.update {
                it.copy(isTestingDiagnostics = false, diagnosticResultVisible = true, diagnosticResultText = "Test zlyhal: $msg")
            }
        }) { a ->
            val latencies = (1..3).map {
                val t0 = SystemClock.elapsedRealtime()
                a.ping()
                (SystemClock.elapsedRealtime() - t0).toInt()
            }
            val avg = latencies.average().toInt()
            val s = _uiState.value
            val profile = currentStreamProfile()
            val sample = s.currentTrack.takeIf { it.id.isNotBlank() }
                ?: s.homeQuickPicks.firstOrNull()
                ?: a.randomSongs(1).firstOrNull()

            val text = StringBuilder("Latencia servera: $avg ms")
            if (sample != null) {
                val probe = a.probeStream(a.streamUrl(sample.id, profile.format, profile.maxBitRate))
                val kbps = if (probe.totalMs > 0) probe.bytes * 8L / probe.totalMs else 0L
                text.append("\nProfil: ${profile.label}")
                text.append("\nFormát odpovede: ${probe.contentType.ifBlank { "neznámy" }}")
                text.append("\nPrvý bajt za ${probe.firstByteMs} ms • ${probe.bytes / 1024} kB za ${probe.totalMs} ms (~$kbps kbps)")
            } else {
                text.append("\nKnižnica je prázdna, test streamu preskočený.")
            }
            _uiState.update {
                it.copy(
                    isTestingDiagnostics = false,
                    diagnosticResultVisible = true,
                    diagnosticResultText = text.toString(),
                    pingMs = avg
                )
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Server Login form
    // ---------------------------------------------------------------------------------------------

    fun setServerUrl(url: String) {
        _uiState.update { it.copy(serverUrl = url, isServerConnectedSuccess = false, connectionError = null) }
    }

    fun setUsername(user: String) {
        _uiState.update { it.copy(username = user, isServerConnectedSuccess = false, connectionError = null) }
    }

    fun setPassword(pwd: String) {
        _uiState.update { it.copy(password = pwd, isServerConnectedSuccess = false, connectionError = null) }
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
        settings.putString(SettingsStore.KEY_LOGIN_TRANSCODE, next)
        _uiState.update { it.copy(loginTranscodeOption = next) }
    }

    /** Public Navidrome demo server – handy for trying the app without your own server. */
    fun fillDemoServerData() {
        _uiState.update {
            it.copy(
                serverUrl = "https://demo.navidrome.org",
                username = "demo",
                password = "demo",
                isServerConnectedSuccess = false,
                connectionError = null
            )
        }
    }

    fun clearHistory() {
        settings.clearHistory()
        _uiState.update { it.copy(historyTracks = emptyList()) }
        showMessage("História počúvania bola vymazaná.")
    }

    override fun onCleared() {
        controller?.let { c ->
            if (c.mediaItemCount > 0) {
                val pos = c.currentPosition
                val s = _uiState.value
                if (s.currentTrack.id.isNotBlank()) {
                    settings.savePlaybackState(s.currentTrack, pos, s.queue, s.queueIndex)
                }
            }
        }
        controller?.removeListener(playerListener)
        controller?.release()
        controller = null
        super.onCleared()
    }
}
