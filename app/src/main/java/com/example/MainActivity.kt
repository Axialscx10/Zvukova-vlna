package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppScreen
import com.example.ui.SoundWaveUiState
import com.example.ui.SoundWaveViewModel
import com.example.ui.components.GlassMiniPlayerDock
import com.example.ui.components.QuickScreenSwitcherModal
import com.example.ui.components.SoundWaveBottomNavBar
import com.example.ui.components.SoundWaveMainTopBar
import com.example.ui.components.StackTopBar
import com.example.ui.screens.ArtistDetailScreen
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

    BackHandler(
        enabled = uiState.currentScreen != AppScreen.HOME ||
            uiState.showAutoEqModal ||
            uiState.showScreenSwitcherSheet
    ) {
        viewModel.navigateBack()
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
                        title = "Server Login",
                        showLogo = true,
                        onBackClick = { viewModel.navigateBack() },
                        onMoreClick = { viewModel.setShowScreenSwitcherSheet(true) },
                        onProfileClick = { viewModel.setShowScreenSwitcherSheet(true) }
                    )
                }
                else -> {
                    StackTopBar(
                        title = "Now Playing",
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
                                    viewModel.playTrack(track, openFullPlayer = true)
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onAlbumClick = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onPlaylistClick = { viewModel.navigateTo(AppScreen.PLAYLIST_DETAIL) },
                                onOpenEqualizer = { viewModel.navigateTo(AppScreen.EQUALIZER) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) }
                            )
                        }
                        AppScreen.EXPLORE -> {
                            ExploreScreen(
                                uiState = uiState,
                                onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                onCategorySelected = { viewModel.setExploreCategory(it) },
                                onToggleAudiophileFilter = { viewModel.toggleAudiophileFilter(it) },
                                onResetAudiophileFilters = { viewModel.resetAudiophileFilters() },
                                onOpenArtistDetail = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onToggleFollowArtist = { viewModel.toggleArtistFollow() },
                                onTrackClick = { track ->
                                    viewModel.playTrack(track, openFullPlayer = true)
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onAlbumClick = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onArtistClick = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onScanServerClick = { viewModel.triggerServerScan() },
                                onServerStatusClick = { viewModel.navigateTo(AppScreen.SERVER_LOGIN) }
                            )
                        }
                        AppScreen.LIBRARY -> {
                            LibraryScreen(
                                uiState = uiState,
                                onFilterChipSelected = { viewModel.setLibraryFilterChip(it) },
                                onToggleGridView = { viewModel.toggleLibraryGridView() },
                                onOpenPlaylistDetail = { viewModel.navigateTo(AppScreen.PLAYLIST_DETAIL) },
                                onOpenArtistDetail = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) },
                                onCreateNewPlaylist = { viewModel.createNewPlaylist() }
                            )
                        }
                        AppScreen.ARTIST_DETAIL -> {
                            ArtistDetailScreen(
                                uiState = uiState,
                                onFilterChipSelected = { viewModel.setArtistFilterChip(it) },
                                onToggleFavoriteArtist = { viewModel.toggleArtistFavorite() },
                                onToggleDownloadDiscography = { viewModel.toggleDiscographyDownload() },
                                onTrackClick = { track ->
                                    viewModel.playTrack(track, openFullPlayer = true)
                                },
                                onOpenStreamingSettings = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) }
                            )
                        }
                        AppScreen.PLAYLIST_DETAIL -> {
                            PlaylistDetailScreen(
                                uiState = uiState,
                                onTrackClick = { track ->
                                    viewModel.playTrack(track, openFullPlayer = true)
                                },
                                onTrackLikeToggle = { viewModel.toggleTrackLike(it) },
                                onOpenExplore = { viewModel.navigateTo(AppScreen.EXPLORE) },
                                onOpenStreamingSettings = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) }
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
                                onOpenArtistDetail = { viewModel.navigateTo(AppScreen.ARTIST_DETAIL) },
                                onOpenStreamingSettings = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) }
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
                                onRunDiagnostics = { viewModel.runTranscodingDiagnostics() }
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
                                onFillDemoServer = { viewModel.fillDemoServerData() }
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
            if (uiState.currentScreen != AppScreen.NOW_PLAYING && uiState.currentScreen != AppScreen.SERVER_LOGIN) {
                val miniTrack = if (uiState.currentScreen == AppScreen.EQUALIZER) {
                    uiState.currentTrack.copy(title = "Midnight Resonance", artist = "Acoustic Dimension")
                } else {
                    uiState.currentTrack
                }
                val subtitle = when (uiState.currentScreen) {
                    AppScreen.EQUALIZER -> "Acoustic Dimension • 24-bit FLAC"
                    AppScreen.ARTIST_DETAIL -> "Mr.Kitty • Time"
                    AppScreen.PLAYLIST_DETAIL -> "Mr.Kitty • FLAC 24-BIT"
                    AppScreen.UPGRADE_STREAMING -> "Mr.Kitty • FLAC 96kHz Direct"
                    else -> "Mr.Kitty • Navidrome"
                }
                val badge = when (uiState.currentScreen) {
                    AppScreen.UPGRADE_STREAMING -> "24-BIT"
                    else -> "FLAC"
                }

                GlassMiniPlayerDock(
                    track = miniTrack,
                    isPlaying = uiState.isPlaying,
                    progress = uiState.playbackProgress,
                    isLiked = uiState.currentTrack.id in uiState.likedTrackIds,
                    subtitleVariant = subtitle,
                    badgeText = badge,
                    showSkipPrevious = uiState.currentScreen == AppScreen.EQUALIZER,
                    showCastButton = uiState.currentScreen in setOf(
                        AppScreen.HOME,
                        AppScreen.LIBRARY,
                        AppScreen.PLAYLIST_DETAIL
                    ),
                    showAirPodsSubtitle = uiState.currentScreen == AppScreen.HOME,
                    onDockClick = { viewModel.navigateTo(AppScreen.NOW_PLAYING) },
                    onLikeClick = { viewModel.toggleTrackLike(uiState.currentTrack.id) },
                    onPlayPauseClick = { viewModel.togglePlayPause() },
                    onNextClick = { viewModel.skipToNextTrack() },
                    onPreviousClick = { viewModel.skipToPreviousTrack() },
                    onCastClick = { viewModel.navigateTo(AppScreen.UPGRADE_STREAMING) },
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
                onDismiss = { viewModel.setShowScreenSwitcherSheet(false) }
            )
        }
    }
}
