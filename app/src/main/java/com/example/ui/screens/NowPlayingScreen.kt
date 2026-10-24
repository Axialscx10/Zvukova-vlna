package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import com.example.data.SleepTimerOption
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TrackItem
import com.example.data.formatMs
import com.example.ui.SoundWaveUiState
import com.example.ui.components.AnimatedEqualizerBars
import com.example.ui.components.NetImage
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryFixedDim
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@Composable
fun NowPlayingScreen(
    uiState: SoundWaveUiState,
    onTogglePlayPause: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onToggleLike: () -> Unit,
    onToggleShuffle: () -> Unit,
    onToggleRepeat: () -> Unit,
    onScrubProgress: (Float) -> Unit,
    onTabSelected: (String) -> Unit,
    onLyricLineClick: (Int) -> Unit,
    onOpenEqualizer: () -> Unit,
    onOpenArtistDetail: () -> Unit,
    onOpenStreamingSettings: () -> Unit,
    onQueueItemClick: (Int) -> Unit,
    onRelatedClick: (TrackItem) -> Unit,
    onOpenSleepTimer: () -> Unit = {},
    onDownloadCurrentTrack: () -> Unit = {},
    onShareTrack: (TrackItem) -> Unit = {},
    onOpenTrackMenu: (TrackItem) -> Unit = {},
    onRemoveFromQueue: (Int) -> Unit = {},
    onClearUpcomingQueue: () -> Unit = {}
) {
    val isLiked = uiState.currentTrack.id in uiState.likedTrackIds
    val isDownloaded = uiState.currentTrack.id in uiState.downloadedTrackIds
    val isDownloading = uiState.currentTrack.id in uiState.downloadingTrackIds
    val isTimerActive = uiState.sleepTimerOption != SleepTimerOption.OFF
    val waveformHeights = remember {
        listOf(
            0.25f, 0.45f, 0.62f, 0.50f, 0.80f, 1.0f, 0.62f, 0.88f, 0.75f, 0.55f,
            0.88f, 1.0f, 0.68f, 0.75f, 0.92f, 0.75f, 0.50f, 0.75f, 0.92f, 0.62f,
            0.80f, 0.50f, 0.88f, 1.0f, 0.75f, 0.62f, 0.45f, 0.68f, 0.88f, 0.80f,
            0.55f, 0.38f, 0.62f, 0.75f, 0.50f, 0.32f
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF281113),
                        Surface,
                        Surface
                    )
                )
            )
            .testTag("now_playing_screen"),
        contentPadding = PaddingValues(bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sub-Header Context Pill & Lossless Badge
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainer.copy(alpha = 0.75f))
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = SecondaryFixed,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                append("Prehráva sa z: ")
                            }
                            withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.SemiBold)) {
                                append(uiState.playingFrom.ifBlank { "Navidrome" })
                            }
                        },
                        fontSize = 12.sp
                    )
                }

                if (uiState.currentTrack.techLine.isNotBlank()) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh.copy(alpha = 0.85f))
                            .clickable { onOpenStreamingSettings() }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SecondaryFixedDim)
                        )
                        Text(
                            text = uiState.currentTrack.techLine.uppercase(),
                            color = Secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.4.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Main Player Display Area: Artwork, Metadata, Waveform, Controls
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Album Artwork Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .shadow(28.dp, RoundedCornerShape(20.dp), ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceContainerLowest)
                ) {
                    NetImage(
                        url = uiState.currentTrack.imageUrl,
                        contentDescription = uiState.currentTrack.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SurfaceContainerLowest.copy(alpha = 0.65f)
                                    )
                                )
                            )
                    )

                    // Floating format chip (real file format of the playing track)
                    if (uiState.currentTrack.badge.isNotBlank()) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (uiState.isPlaying) {
                                AnimatedEqualizerBars(
                                    barColor = Primary,
                                    secondaryBarColor = SecondaryFixedDim,
                                    barWidth = 2.dp,
                                    maxHeight = 12.dp
                                )
                            }
                            Text(
                                text = uiState.currentTrack.badge,
                                color = OnSurface,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Metadata & Primary Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onOpenArtistDetail() }
                    ) {
                        Text(
                            text = uiState.currentTrack.title,
                            color = OnSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = uiState.currentTrack.artist,
                                color = OnSurfaceVariant,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        IconButton(
                            onClick = onToggleLike,
                            modifier = Modifier
                                .size(38.dp)
                                .testTag("now_playing_like_btn")
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Páči sa mi to",
                                tint = if (isLiked) PrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            onClick = onOpenSleepTimer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bedtime,
                                contentDescription = "Časovač spánku",
                                tint = if (isTimerActive) PrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            onClick = onDownloadCurrentTrack,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DownloadDone else if (isDownloading) Icons.Default.DownloadDone else Icons.Default.Download,
                                contentDescription = if (isDownloaded) "Stiahnuté" else "Stiahnuť",
                                tint = if (isDownloaded) SecondaryFixed else if (isDownloading) PrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            onClick = onOpenEqualizer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Ekvalizér",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            onClick = { onOpenTrackMenu(uiState.currentTrack) },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Viac možností",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Waveform & Progress Scrubber
                Column(modifier = Modifier.fillMaxWidth()) {
                    val activeBarCount = (uiState.playbackProgress * waveformHeights.size).toInt().coerceIn(1, waveformHeights.size)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    val pct = (offset.x / size.width).coerceIn(0f, 1f)
                                    onScrubProgress(pct)
                                }
                            }
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { change, _ ->
                                    change.consume()
                                    val pct = (change.position.x / size.width).coerceIn(0f, 1f)
                                    onScrubProgress(pct)
                                }
                            }
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        waveformHeights.forEachIndexed { idx, fraction ->
                            if (idx == activeBarCount) {
                                // Playhead Scrub Pin
                                Box(
                                    modifier = Modifier.height(34.dp),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(50))
                                            .background(Color.White)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .offset(y = (-3).dp)
                                            .size(12.dp)
                                            .shadow(8.dp, CircleShape, spotColor = PrimaryContainer)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    )
                                }
                            } else {
                                val isPlayed = idx < activeBarCount
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height((32.dp * fraction).coerceAtLeast(6.dp))
                                        .clip(RoundedCornerShape(50))
                                        .background(if (isPlayed) PrimaryContainer else SurfaceContainerHighest)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatMs(uiState.positionMs),
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "-" + formatMs((uiState.durationMs - uiState.positionMs).coerceAtLeast(0L)),
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Playback Transport Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shuffle Toggle
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .clickable { onToggleShuffle() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Náhodné prehrávanie",
                            tint = if (uiState.isShuffleEnabled) PrimaryContainer else OnSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                        if (uiState.isShuffleEnabled) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 5.dp)
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryContainer)
                            )
                        }
                    }

                    // Previous Track
                    IconButton(
                        onClick = onPreviousClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Predchádzajúca skladba",
                            tint = OnSurface,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    // Main Hero Play / Pause Button
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .shadow(24.dp, CircleShape, ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                            .clip(CircleShape)
                            .background(PrimaryContainer)
                            .clickable { onTogglePlayPause() }
                            .testTag("now_playing_main_play_pause"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (uiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (uiState.isPlaying) "Pozastaviť" else "Prehrať",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Next Track
                    IconButton(
                        onClick = onNextClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Ďalšia skladba",
                            tint = OnSurface,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    // Repeat One Toggle
                    IconButton(
                        onClick = onToggleRepeat,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RepeatOne,
                            contentDescription = "Opakovať skladbu",
                            tint = if (uiState.isRepeatOne) PrimaryContainer else OnSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Secondary Device / Queue Route Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainer.copy(alpha = 0.75f))
                            .clickable { onOpenStreamingSettings() }
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Devices,
                            contentDescription = null,
                            tint = SecondaryFixed,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (uiState.isLoggedIn) uiState.serverHost else "Server nepripojený",
                            color = OnSurface,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainer.copy(alpha = 0.75f))
                            .clickable { onTabSelected("NASLEDUJÚCE") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Fronta",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Bottom Interactive Drawer & Synced Lyrics Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Segmented Tab Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerLow.copy(alpha = 0.9f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("NASLEDUJÚCE", "TEXT", "PODOBNÉ").forEach { tab ->
                        val isSelected = uiState.nowPlayingTab == tab ||
                            (tab == "NASLEDUJÚCE" && uiState.nowPlayingTab == "UP NEXT") ||
                            (tab == "TEXT" && uiState.nowPlayingTab == "LYRICS") ||
                            (tab == "PODOBNÉ" && uiState.nowPlayingTab == "RELATED")
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) SurfaceContainerHighest else Color.Transparent)
                                .clickable { onTabSelected(tab) }
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (tab == "TEXT") {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryContainer)
                                )
                            }
                            Text(
                                text = tab,
                                color = if (isSelected) OnSurface else OnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Synchronized Live Karaoke Lyrics Card Drawer
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(SurfaceContainer.copy(alpha = 0.85f))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHighest)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NowPlayingDrawerContent(
                        uiState = uiState,
                        onLyricLineClick = onLyricLineClick,
                        onQueueItemClick = onQueueItemClick,
                        onRelatedClick = onRelatedClick,
                        onRemoveFromQueue = onRemoveFromQueue,
                        onClearUpcomingQueue = onClearUpcomingQueue,
                        onOpenTrackMenu = onOpenTrackMenu
                    )
                }
            }
        }
    }
}

@Composable
private fun NowPlayingDrawerContent(
    uiState: SoundWaveUiState,
    onLyricLineClick: (Int) -> Unit,
    onQueueItemClick: (Int) -> Unit,
    onRelatedClick: (TrackItem) -> Unit,
    onRemoveFromQueue: (Int) -> Unit,
    onClearUpcomingQueue: () -> Unit,
    onOpenTrackMenu: (TrackItem) -> Unit
) {
    when (uiState.nowPlayingTab) {
        "NASLEDUJÚCE", "UP NEXT" -> {
            val startIdx = (uiState.queueIndex + 1).coerceAtLeast(0)
            val upcoming = uiState.queue.drop(startIdx).take(50)
            if (upcoming.isEmpty()) {
                DrawerHint("Fronta je prázdna. Spusti album alebo playlist a ďalšie skladby sa zobrazia tu.")
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nasledujúce v poradí (${upcoming.size})",
                        color = OnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHighest)
                            .clickable { onClearUpcomingQueue() }
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ClearAll,
                            contentDescription = "Vyčistiť",
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Vyčistiť",
                            color = Primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                upcoming.forEachIndexed { offset, track ->
                    val queueIndex = startIdx + offset
                    DrawerTrackRow(
                        track = track,
                        onClick = { onQueueItemClick(queueIndex) },
                        onRemove = { onRemoveFromQueue(queueIndex) },
                        onMenuClick = { onOpenTrackMenu(track) }
                    )
                }
                if (uiState.isShuffleEnabled) {
                    DrawerHint("Náhodné poradie je zapnuté – ďalšia skladba sa môže líšiť od poradia vo fronte.")
                }
            }
        }
        "PODOBNÉ", "RELATED" -> {
            when {
                uiState.relatedLoading -> DrawerHint("Hľadám podobné skladby…")
                uiState.relatedTracks.isEmpty() -> DrawerHint("Server nenašiel podobné skladby.")
                else -> uiState.relatedTracks.forEach { track ->
                    DrawerTrackRow(
                        track = track,
                        onClick = { onRelatedClick(track) },
                        onRemove = null,
                        onMenuClick = { onOpenTrackMenu(track) }
                    )
                }
            }
        }
        else -> {
            val lines = uiState.lyrics
            when {
                uiState.lyricsLoading -> DrawerHint("Načítavam text…")
                lines.isEmpty() -> DrawerHint("K tejto skladbe nie je na serveri dostupný text.")
                lines.first().startMs < 0 -> {
                    // Plain (unsynced) lyrics
                    lines.take(120).forEach { line ->
                        Text(
                            text = line.text,
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        )
                    }
                }
                else -> {
                    // Synced lyrics: show a window around the active line
                    val active = uiState.activeLyricIndex
                    val from = (active - 2).coerceAtLeast(0)
                    val to = (maxOf(active, 0) + 4).coerceAtMost(lines.lastIndex)
                    for (idx in from..to) {
                        val isCurrentLine = active == idx
                        Text(
                            text = lines[idx].text,
                            color = when {
                                isCurrentLine -> Color.White
                                idx < active -> OnSurfaceVariant.copy(alpha = 0.42f)
                                idx == active + 1 -> OnSurfaceVariant.copy(alpha = 0.65f)
                                else -> OnSurfaceVariant.copy(alpha = 0.32f)
                            },
                            fontSize = if (isCurrentLine) 20.sp else 16.sp,
                            fontWeight = if (isCurrentLine) FontWeight.ExtraBold else FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLyricLineClick(idx) }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerHint(text: String) {
    Text(
        text = text,
        color = OnSurfaceVariant,
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    )
}

@Composable
private fun DrawerTrackRow(
    track: TrackItem,
    onClick: () -> Unit,
    onRemove: (() -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerHighest)
        ) {
            NetImage(
                url = track.imageUrl,
                contentDescription = track.title,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.title,
                color = OnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = track.artist,
                color = OnSurfaceVariant,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(text = track.duration, color = OnSurfaceVariant, fontSize = 12.sp)

        if (onMenuClick != null) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Možnosti",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (onRemove != null) {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Odstrániť z fronty",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
