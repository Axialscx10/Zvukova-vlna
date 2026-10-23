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
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
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
import com.example.data.SoundWaveCatalog
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
    onOpenStreamingSettings: () -> Unit
) {
    val isLiked = uiState.currentTrack.id in uiState.likedTrackIds
    val waveformHeights = remember {
        listOf(
            0.25f, 0.45f, 0.62f, 0.50f, 0.80f, 1.0f, 0.62f, 0.88f, 0.75f, 0.55f,
            0.88f, 1.0f, 0.68f, 0.75f, 0.92f, 0.75f, 0.50f, 0.75f, 0.92f, 0.62f,
            0.80f, 0.50f, 0.88f, 1.0f, 0.75f, 0.62f, 0.45f, 0.68f, 0.88f, 0.80f,
            0.55f, 0.38f, 0.62f, 0.75f, 0.50f, 0.32f
        )
    }

    val lyricsLines = listOf(
        "I see you, you see me",
        "How pleasant, this feeling",
        "The whisperings, the dancing",
        "So sweet, so soft..."
    )

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
                                append("Playing from ")
                            }
                            withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.SemiBold)) {
                                append("Quick picks")
                            }
                        },
                        fontSize = 12.sp
                    )
                }

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
                        text = "LOSSLESS 24-bit / 96kHz",
                        color = Secondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.4.sp
                    )
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
                    val artworkUrl = if (uiState.currentTrack.title == "After Dark") {
                        SoundWaveCatalog.NOW_PLAYING_ARTWORK_URL
                    } else {
                        uiState.currentTrack.imageUrl
                    }
                    NetImage(
                        url = artworkUrl,
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

                    // Floating Dolby Atmos Chip
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
                        AnimatedEqualizerBars(
                            barColor = Primary,
                            secondaryBarColor = SecondaryFixedDim,
                            barWidth = 2.dp,
                            maxHeight = 12.dp
                        )
                        Text(
                            text = "DOLBY ATMOS",
                            color = OnSurface,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
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
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = SecondaryFixedDim,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(
                            onClick = onToggleLike,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("now_playing_like_btn")
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (isLiked) PrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ThumbDown,
                                contentDescription = "Dislike",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        IconButton(
                            onClick = onOpenEqualizer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                                contentDescription = "Add to playlist",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(21.dp)
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
                            text = "1:42",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "-2:36",
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
                            contentDescription = "Shuffle",
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
                            contentDescription = "Previous",
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
                            contentDescription = if (uiState.isPlaying) "Pause" else "Play",
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
                            contentDescription = "Next",
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
                            contentDescription = "Repeat One",
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
                            .clickable { onOpenEqualizer() }
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
                            text = "Living Room Beats (Hi-Fi)",
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
                            .clickable { onOpenEqualizer() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Queue / DSP",
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
                    listOf("UP NEXT", "LYRICS", "RELATED").forEach { tab ->
                        val isSelected = uiState.nowPlayingTab == tab
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) SurfaceContainerHighest else Color.Transparent)
                                .clickable { onTabSelected(tab) }
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (tab == "LYRICS") {
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

                    lyricsLines.forEachIndexed { idx, line ->
                        val isCurrentLine = uiState.activeLyricIndex == idx
                        Text(
                            text = line,
                            color = when {
                                isCurrentLine -> Color.White
                                idx < uiState.activeLyricIndex -> OnSurfaceVariant.copy(alpha = 0.42f)
                                idx == uiState.activeLyricIndex + 1 -> OnSurfaceVariant.copy(alpha = 0.65f)
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable {
                            onLyricLineClick((uiState.activeLyricIndex + 1) % lyricsLines.size)
                        }
                    ) {
                        Text(
                            text = "SWIPE UP FOR FULL SCREEN LYRICS",
                            color = OnSurfaceVariant.copy(alpha = 0.75f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = OnSurfaceVariant.copy(alpha = 0.75f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
