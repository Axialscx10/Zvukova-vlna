package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlbumItem
import com.example.data.PlaylistItem
import com.example.data.SoundWaveCatalog
import com.example.data.TrackItem
import com.example.ui.SoundWaveUiState
import com.example.ui.components.AnimatedEqualizerBars
import com.example.ui.components.NetImage
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary

@Composable
fun HomeScreen(
    uiState: SoundWaveUiState,
    onFilterSelected: (String) -> Unit,
    onTrackClick: (TrackItem) -> Unit,
    onTrackLikeToggle: (String) -> Unit,
    onAlbumClick: (AlbumItem) -> Unit,
    onPlaylistClick: (PlaylistItem) -> Unit,
    onOpenEqualizer: () -> Unit,
    onOpenExplore: () -> Unit
) {
    val filters = listOf("All", "Energize", "Workout", "Relax", "Commute", "Focus")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 160.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Filter Chips Carousel
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = uiState.homeFilter == filter
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) SurfaceBright else SurfaceContainerHigh)
                            .clickable { onFilterSelected(filter) }
                            .padding(horizontal = 18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = OnSurface,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // SECTION 1: START RADIO FROM A SONG / Quick picks
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "START RADIO FROM A SONG",
                            color = Primary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Quick picks",
                            color = OnSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.4).sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh)
                            .clickable {
                                onTrackClick(SoundWaveCatalog.homeQuickPicks.first())
                            }
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("home_play_all_btn")
                    ) {
                        Text(
                            text = "Play all",
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SoundWaveCatalog.homeQuickPicks.forEach { track ->
                        val isActive = uiState.currentTrack.title == track.title
                        val isLiked = track.id in uiState.likedTrackIds
                        QuickPickTrackRow(
                            track = track,
                            isActive = isActive,
                            isLiked = isLiked,
                            onClick = { onTrackClick(track) },
                            onLikeClick = { onTrackLikeToggle(track.id) }
                        )
                    }
                }
            }
        }

        // SECTION 2: DISCOVER MORE / Listen again
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DISCOVER MORE",
                            color = Secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Listen again",
                            color = OnSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.4).sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainer)
                            .clickable { onOpenExplore() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Explore",
                            tint = OnSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SoundWaveCatalog.homeListenAgain, key = { it.id }) { album ->
                        ListenAgainAlbumCard(
                            album = album,
                            onClick = { onAlbumClick(album) }
                        )
                    }
                }
            }
        }

        // SECTION 3: CURATED JUST FOR YOU / Mixed for you
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CURATED JUST FOR YOU",
                            color = Tertiary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Mixed for you",
                            color = OnSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.4).sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainer)
                            .clickable { onOpenEqualizer() }
                            .testTag("home_tune_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Equalizer",
                            tint = OnSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SoundWaveCatalog.homeMixedForYou, key = { it.id }) { mix ->
                        MixedForYouCard(
                            playlist = mix,
                            onClick = { onPlaylistClick(mix) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickPickTrackRow(
    track: TrackItem,
    isActive: Boolean,
    isLiked: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) SurfaceContainer.copy(alpha = 0.75f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLow)
            ) {
                NetImage(
                    url = track.imageUrl,
                    contentDescription = track.title,
                    modifier = Modifier.fillMaxSize()
                )
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SurfaceContainerLowest.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedEqualizerBars(
                            barColor = Primary,
                            secondaryBarColor = Secondary,
                            barWidth = 3.dp,
                            maxHeight = 18.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    color = if (isActive) Primary else OnSurface,
                    fontSize = 16.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = track.artist,
                        color = if (isActive) Secondary else OnSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text("•", color = OnSurfaceVariant.copy(alpha = 0.45f), fontSize = 11.sp)
                    Text(
                        text = track.duration,
                        color = OnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            IconButton(
                onClick = onLikeClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    tint = if (isLiked) Primary else OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ListenAgainAlbumCard(
    album: AlbumItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(152.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainer)
        ) {
            NetImage(
                url = album.imageUrl,
                contentDescription = album.title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(40.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play ${album.title}",
                    tint = OnPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = album.title,
            color = OnSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = album.subtitle,
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MixedForYouCard(
    playlist: PlaylistItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(168.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(SurfaceContainer)
        ) {
            if (playlist.collageUrls.size == 4) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.weight(1f)) {
                        NetImage(
                            url = playlist.collageUrls[0],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        NetImage(
                            url = playlist.collageUrls[1],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        NetImage(
                            url = playlist.collageUrls[2],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        NetImage(
                            url = playlist.collageUrls[3],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            } else {
                NetImage(
                    url = playlist.imageUrl,
                    contentDescription = playlist.title,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Bottom dark gradient overlay with title & subtitle
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                SurfaceContainerLowest.copy(alpha = 0.92f)
                            )
                        )
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = playlist.title,
                        color = OnSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    val subColor = when (playlist.id) {
                        "mix_supermix" -> Secondary
                        "mix_night_drive" -> Primary
                        else -> Tertiary
                    }
                    Text(
                        text = playlist.subtitle,
                        color = subColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Top right icon pill
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(SurfaceBright.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                val badgeIcon = when (playlist.badge) {
                    "graphic_eq" -> Icons.Default.GraphicEq
                    "radio" -> Icons.Default.Radio
                    else -> Icons.Default.Headset
                }
                Icon(
                    imageVector = badgeIcon,
                    contentDescription = null,
                    tint = OnSurface,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = playlist.trackCount,
            color = OnSurfaceVariant,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
