package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppScreen
import com.example.ui.SoundWaveViewModel
import com.example.ui.components.GlassMiniPlayerDock
import com.example.ui.components.QuickScreenSwitcherModal
import com.example.ui.components.SleepTimerBottomSheet
import com.example.ui.components.SoundWaveBottomNavBar
import com.example.ui.components.SoundWaveMainTopBar
import com.example.ui.components.StackTopBar
import com.example.ui.components.TrackActionsBottomSheet
import com.example.ui.screens.EqualizerScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.NowPlayingScreen
import com.example.ui.screens.PlaylistDetailScreen
import com.example.ui.screens.ServerLoginScreen
import com.example.ui.screens.StreamingSettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            MyApplicationTheme {
                SoundWaveApp()
            }
        }
    }
}

@Composable
fun SoundWaveApp(
    viewModel: SoundWaveViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Android 13+ needs a runtime permission for the playback notification (lock screen controls).
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // One-shot messages (errors, confirmations) from the ViewModel
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.consumeMessage()
        }
    }

    val hasTrack = uiState.currentTrack.id.isNotBlank()

    BackHandler(
        enabled = (uiState.currentScreen != AppScreen.HOME &&
            !(uiState.currentScreen == AppScreen.SERVER_LOGIN && !uiState.isLoggedIn)) ||
            uiState.showAutoEqModal ||
            uiState.showScreenSwitcherSheet ||
            uiState.showSleepTimerModal ||
            uiState.trackMenuTarget != null
    ) {
        when {
            uiState.showSleepTimerModal -> viewModel.setShowSleepTimerModal(false)
            uiState.trackMenuTarget != null -> viewModel.setTrackMenuTarget(null)
            uiState.showScreenSwitcherSheet -> viewModel.setShowScreenSwitcherSheet(false)
            uiState.showAutoEqModal -> viewModel.setShowAutoEqModal(false)
            else -> viewModel.navigateBack()
        }
    }

    val isMainTabScreen = uiState.currentScreen in setOf(
        AppScreen.HOME,
        AppScreen.EXPLORE,
        AppScreen.LIBRARY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar based on active screen
            when (uiState.currentScreen) {
                AppScreen.HOME, AppScreen.LIBRARY -> {
                    SoundWaveMainTopBar(
                        useGraphicEqLogo = false,
                        showSearchIcon = true,
                        onCastClick = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                        onSearchClick = { viewModel.navigateTo(AppScreen.EXPLORE) },
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onEqualizerQuickClick = { viewModel.navigateTo(AppScreen.EQUALIZER) }
                    )
                }
                AppScreen.EXPLORE -> {
                    SoundWaveMainTopBar(
                        useGraphicEqLogo = true,
                        showSearchIcon = false,
                        onCastClick = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                        onSearchClick = {},
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onEqualizerQuickClick = { viewModel.navigateTo(AppScreen.EQUALIZER) }
                    )
                }
                AppScreen.EQUALIZER -> {
                    StackTopBar(
                        title = "Ekvalizér A Zvukové Profily",
                        showLogo = false,
                        masterSwitchChecked = uiState.eqMasterEnabled,
                        onMasterSwitchChange = { viewModel.toggleEqMaster(it) },
                        onBackClick = { viewModel.navigateBack() },
                        onMoreClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) }
                    )
                }
                AppScreen.SERVER_LOGIN -> {
                    StackTopBar(
                        title = "Prihlásenie na server",
                        showLogo = true,
                        onBackClick = { viewModel.navigateBack() },
                        onMoreClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) }
                    )
                }
                else -> {
                    StackTopBar(
                        title = when (uiState.currentScreen) {
                            AppScreen.PLAYLIST_DETAIL -> uiState.detail?.kind ?: "Detail"
                            AppScreen.UPGRADE_STREAMING -> "Kvalita streamovania"
                            else -> "Práve hrá"
                        },
                        showLogo = true,
                        onBackClick = { viewModel.navigateBack() },
                        onMoreClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) }
                    )
                }
            }

            // Main Content Area
            Box(modifier = Modifier.weight(1f)) {
                Crossfade(targetState = uiState.currentScreen, label = "screen_crossfade") { screen ->
                    when (screen) {
                        AppScreen.HOME -> {
                            HomeScreen(
                                uiState = uiState,
                                onFilterSelected = { viewModel.setHomeFilter(it) },
                                onTrackClick = { track ->
                                    viewModel.playTrack(
                                        track,
                                        openFullPlayer = true,
                                        context = uiState.homeQuickPicks,
                                        from = "Rýchly výber"
                                    )
                                },
                                onPlayAll = {
                                    viewModel.playAll(uiState.homeQuickPicks, shuffle = false, from = "Rýchly výber")
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onAlbumClick = { viewModel.openCollection(it.id) },
                                onPlaylistClick = { viewModel.openCollection(it.id) },
                                onOpenEqualizer = { viewModel.navigateTo(AppScreen.EQUALIZER) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) },
                                onOpenServerLogin = { viewModel.navigateTo(AppScreen.SERVER_LOGIN) },
                                onRetry = { viewModel.loadHome() },
                                onTrackMenu = { viewModel.setTrackMenuTarget(it) }
                            )
                        }
                        AppScreen.EXPLORE -> {
                            ExploreScreen(
                                uiState = uiState,
                                onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                onCategorySelected = { viewModel.setExploreCategory(it) },
                                onToggleAudiophileFilter = { viewModel.toggleAudiophileFilter(it) },
                                onResetAudiophileFilters = { viewModel.resetAudiophileFilters() },
                                onTrackClick = { track, list ->
                                    viewModel.playTrack(
                                        track,
                                        openFullPlayer = true,
                                        context = list,
                                        from = if (uiState.searchQuery.isBlank()) "Objavuj" else "Hľadanie: ${uiState.searchQuery}"
                                    )
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onAlbumClick = { viewModel.openCollection(it.id) },
                                onArtistClick = { viewModel.openCollection(it.id) },
                                onPlaylistClick = { viewModel.openCollection(it.id) },
                                onGenreClick = { viewModel.openCollection("ge:" + it.name) },
                                onScanServerClick = { viewModel.triggerServerScan() },
                                onServerStatusClick = { viewModel.navigateTo(AppScreen.SERVER_LOGIN) },
                                onTrackMenu = { viewModel.setTrackMenuTarget(it) }
                            )
                        }
                        AppScreen.LIBRARY -> {
                            LibraryScreen(
                                uiState = uiState,
                                onFilterChipSelected = { viewModel.setLibraryFilterChip(it) },
                                onToggleGridView = { viewModel.toggleLibraryGridView() },
                                onOpenCollection = { viewModel.openCollection(it) },
                                onTrackClick = { track, list ->
                                    viewModel.playTrack(
                                        track,
                                        openFullPlayer = true,
                                        context = list,
                                        from = when (uiState.libraryFilterChip) {
                                            "Stiahnuté" -> "Stiahnuté skladby"
                                            "História" -> "História prehrávania"
                                            else -> "Obľúbené"
                                        }
                                    )
                                },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) },
                                onCreateNewPlaylist = { viewModel.createNewPlaylist(it) },
                                onPlayRandomMix = { viewModel.playRandomMix() },
                                onOpenServerLogin = { viewModel.navigateTo(AppScreen.SERVER_LOGIN) },
                                onRetry = { viewModel.loadLibrary() },
                                onPlayAll = { list, shuffle ->
                                    viewModel.playAll(
                                        list,
                                        shuffle = shuffle,
                                        from = if (uiState.libraryFilterChip == "História") "História prehrávania" else "Stiahnuté skladby"
                                    )
                                },
                                onOpenTrackMenu = { viewModel.setTrackMenuTarget(it) },
                                onClearDownloads = { viewModel.clearAllDownloads() },
                                onClearHistory = { viewModel.clearHistory() }
                            )
                        }
                        AppScreen.PLAYLIST_DETAIL -> {
                            PlaylistDetailScreen(
                                uiState = uiState,
                                onTrackClick = { track, list ->
                                    viewModel.playTrack(
                                        track,
                                        openFullPlayer = true,
                                        context = list,
                                        from = uiState.detail?.title.orEmpty()
                                    )
                                },
                                onPlayAll = { shuffle ->
                                    viewModel.playAll(
                                        uiState.detail?.tracks.orEmpty(),
                                        shuffle = shuffle,
                                        from = uiState.detail?.title.orEmpty()
                                    )
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) },
                                onOpenStreamingSettings = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                                onOpenTrackMenu = { viewModel.setTrackMenuTarget(it) }
                            )
                        }
                        AppScreen.NOW_PLAYING -> {
                            NowPlayingScreen(
                                uiState = uiState,
                                onTogglePlayPause = { viewModel.togglePlayPause() },
                                onPreviousClick = { viewModel.skipToPreviousTrack() },
                                onNextClick = { viewModel.skipToNextTrack() },
                                onToggleLike = { viewModel.toggleTrackLike(uiState.currentTrack.id) },
                                onToggleShuffle = { viewModel.toggleShuffle() },
                                onToggleRepeat = { viewModel.toggleRepeatOne() },
                                onScrubProgress = { viewModel.setPlaybackProgress(it) },
                                onTabSelected = { viewModel.setNowPlayingTab(it) },
                                onLyricLineClick = { viewModel.setActiveLyricIndex(it) },
                                onOpenEqualizer = { viewModel.navigateTo(AppScreen.EQUALIZER) },
                                onOpenArtistDetail = {
                                    val artistId = uiState.currentTrack.artistId
                                    if (artistId.isNotBlank()) viewModel.openCollection("ar:$artistId")
                                },
                                onOpenStreamingSettings = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                                onQueueItemClick = { viewModel.playQueueIndex(it) },
                                onRelatedClick = { track ->
                                    viewModel.playTrack(track, from = "Podobné skladby")
                                },
                                onOpenSleepTimer = { viewModel.setShowSleepTimerModal(true) },
                                onDownloadCurrentTrack = { viewModel.downloadTrack(uiState.currentTrack) },
                                onShareTrack = { track ->
                                    val sendIntent = android.content.Intent().apply {
                                        action = android.content.Intent.ACTION_SEND
                                        putExtra(android.content.Intent.EXTRA_TEXT, "Počúvam skladbu „${track.title}“ od ${track.artist} cez SoundWave!")
                                        type = "text/plain"
                                    }
                                    context.startActivity(android.content.Intent.createChooser(sendIntent, "Zdieľať skladbu"))
                                },
                                onOpenTrackMenu = { viewModel.setTrackMenuTarget(it) },
                                onRemoveFromQueue = { viewModel.removeFromQueue(it) },
                                onClearUpcomingQueue = { viewModel.clearUpcomingQueue() }
                            )
                        }
                        AppScreen.EQUALIZER -> {
                            EqualizerScreen(
                                uiState = uiState,
                                onPreampChange = { viewModel.setPreampDb(it) },
                                onBandChange = { idx, db -> viewModel.setEqBand(idx, db) },
                                onResetFlat = { viewModel.resetEqFlat() },
                                onInvertBands = { viewModel.invertEqBands() },
                                onSelectPreset = { viewModel.selectEqPreset(it) },
                                onOpenAutoEqModal = { viewModel.setShowAutoEqModal(true) },
                                onCloseAutoEqModal = { viewModel.setShowAutoEqModal(false) },
                                onSubBassBoostChange = { viewModel.setSubBassBoostDb(it) },
                                onToggleCrossfeed = { viewModel.toggleCrossfeed(it) },
                                onToggleAutoGain = { viewModel.toggleAutoGain(it) },
                                onAutoEqSearchChange = { viewModel.setAutoEqSearchQuery(it) },
                                onAutoEqTargetCurveSelect = { viewModel.setAutoEqTargetCurve(it) },
                                onSelectHeadphoneProfile = { viewModel.selectHeadphoneProfile(it) },
                                onCalibrationDryWetChange = { viewModel.setCalibrationDryWet(it) },
                                onToggleAutoPreamp = { viewModel.toggleAutoPreamp(it) },
                                onToggleSubBassCompensation = { viewModel.toggleSubBassCompensation(it) },
                                onApplyAutoEqToDsp = { viewModel.applyAutoEqProfileToDsp() }
                            )
                        }
                        AppScreen.UPGRADE_STREAMING -> {
                            StreamingSettingsScreen(
                                uiState = uiState,
                                onOpenServerLogin = { viewModel.navigateTo(AppScreen.SERVER_LOGIN) },
                                onToggleCellularTranscoding = { viewModel.toggleCellularTranscoding() },
                                onSelectOpusBitrate = { viewModel.setOpusBitrateTarget(it) },
                                onSelectBackendCodec = { viewModel.setBackendCodec(it) },
                                onCycleSampleRate = { viewModel.cycleMaxSampleRate() },
                                onToggleHardwareAccel = { viewModel.toggleHardwareAccelVaapi() },
                                onToggleBitPerfect = { viewModel.toggleBitPerfectExclusive() },
                                onToggleReplayGain = { viewModel.toggleReplayGain() },
                                onSelectReplayGainMode = { viewModel.setReplayGainMode(it) },
                                onReplayGainPreampChange = { viewModel.setReplayGainPreamp(it) },
                                onToggleGapless = { viewModel.toggleGapless() },
                                onCrossfadeChange = { viewModel.setCrossfadeSeconds(it) },
                                onClearCache = { viewModel.clearStreamCache() },
                                onRunDiagnostics = { viewModel.runTranscodingDiagnostics() },
                                onClearDownloads = { viewModel.clearAllDownloads() }
                            )
                        }
                        AppScreen.SERVER_LOGIN -> {
                            ServerLoginScreen(
                                uiState = uiState,
                                onServerUrlChange = { viewModel.setServerUrl(it) },
                                onUsernameChange = { viewModel.setUsername(it) },
                                onPasswordChange = { viewModel.setPassword(it) },
                                onTogglePasswordVisibility = { viewModel.togglePasswordVisibility() },
                                onToggleTokenHint = { viewModel.toggleTokenHint() },
                                onToggleAdvancedAccordion = { viewModel.toggleAdvancedAudioAccordion() },
                                onToggleOfflineCache = { viewModel.toggleLoginOfflineCache() },
                                onTogglePreferFlac = { viewModel.toggleLoginPreferFlac() },
                                onCycleTranscodeOption = { viewModel.cycleLoginTranscodeOption() },
                                onConnectServer = { viewModel.connectToServer() },
                                onFillDemoServer = { viewModel.fillDemoServerData() },
                                onLogout = { viewModel.logout() }
                            )
                        }
                    }
                }
            }
        }

        // Persistent Bottom Dock Area (Mini-Player + Optional Bottom Navigation Bar)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            if (hasTrack &&
                !uiState.isMiniPlayerDismissed &&
                uiState.currentScreen != AppScreen.NOW_PLAYING &&
                uiState.currentScreen != AppScreen.SERVER_LOGIN
            ) {
                val track = uiState.currentTrack
                val subtitle = listOf(track.artist, track.album)
                    .filter { it.isNotBlank() }
                    .joinToString(" • ")

                GlassMiniPlayerDock(
                    track = track,
                    isPlaying = uiState.isPlaying,
                    progress = uiState.playbackProgress,
                    isLiked = track.id in uiState.likedTrackIds,
                    subtitleVariant = subtitle,
                    badgeText = track.badge,
                    showSkipPrevious = uiState.currentScreen == AppScreen.EQUALIZER,
                    showCastButton = false,
                    showAirPodsSubtitle = false,
                    onDockClick = { viewModel.navigateTo(AppScreen.NOW_PLAYING) },
                    onLikeClick = { viewModel.toggleTrackLike(track.id) },
                    onPlayPauseClick = { viewModel.togglePlayPause() },
                    onNextClick = { viewModel.skipToNextTrack() },
                    onPreviousClick = { viewModel.skipToPreviousTrack() },
                    onCastClick = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                    onDismiss = { viewModel.dismissMiniPlayer() },
                    modifier = if (!isMainTabScreen) {
                        Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                    } else {
                        Modifier
                    }
                )
            }

            if (isMainTabScreen) {
                SoundWaveBottomNavBar(
                    currentScreen = uiState.currentScreen,
                    onTabSelected = { viewModel.navigateTo(it) }
                )
            }
        }

        // Quick Screen Switcher Sheet
        if (uiState.showScreenSwitcherSheet) {
            QuickScreenSwitcherModal(
                currentScreen = uiState.currentScreen,
                onSelectScreen = { screen ->
                    viewModel.setShowAutoEqModal(false)
                    viewModel.navigateTo(screen)
                },
                onOpenAutoEq = {
                    viewModel.navigateTo(AppScreen.EQUALIZER)
                    viewModel.setShowAutoEqModal(true)
                },
                onDismiss = { viewModel.setShowScreenSwitcherSheet(false) },
                onLogout = if (uiState.isLoggedIn) {
                    {
                        viewModel.setShowScreenSwitcherSheet(false)
                        viewModel.logout()
                    }
                } else null
            )
        }

        // Sleep Timer Sheet
        if (uiState.showSleepTimerModal) {
            SleepTimerBottomSheet(
                currentOption = uiState.sleepTimerOption,
                remainingSeconds = uiState.sleepTimerRemainingSec,
                onDismiss = { viewModel.setShowSleepTimerModal(false) },
                onSelectOption = { viewModel.setSleepTimer(it) }
            )
        }

        // Track Actions Sheet
        uiState.trackMenuTarget?.let { track ->
            val isDownloaded = track.id in uiState.downloadedTrackIds
            val isDownloading = track.id in uiState.downloadingTrackIds
            val isLiked = track.id in uiState.likedTrackIds
            TrackActionsBottomSheet(
                track = track,
                isDownloaded = isDownloaded,
                isDownloading = isDownloading,
                isLiked = isLiked,
                onDismiss = { viewModel.setTrackMenuTarget(null) },
                onPlayNow = { viewModel.playTrack(track, openFullPlayer = true) },
                onPlayNext = { viewModel.playNext(track) },
                onAddToQueue = { viewModel.addToQueue(track) },
                onDownload = { viewModel.downloadTrack(track) },
                onDeleteDownload = { viewModel.deleteDownloadedTrack(track.id) },
                onToggleLike = { viewModel.toggleTrackLike(track.id) },
                onShare = {
                    val sendIntent = android.content.Intent().apply {
                        action = android.content.Intent.ACTION_SEND
                        putExtra(
                            android.content.Intent.EXTRA_TEXT,
                            "Počúvam skladbu „${track.title}“ od ${track.artist} cez SoundWave!"
                        )
                        type = "text/plain"
                    }
                    context.startActivity(
                        android.content.Intent.createChooser(sendIntent, "Zdieľať skladbu")
                    )
                }
            )
        }
    }
}
