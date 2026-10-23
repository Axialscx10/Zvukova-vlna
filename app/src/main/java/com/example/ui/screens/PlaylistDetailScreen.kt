package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@Composable
fun PlaylistDetailScreen(
    uiState: SoundWaveUiState,
    onTrackClick: (TrackItem) -> Unit,
    onTrackLikeToggle: (String) -> Unit,
    onOpenExplore: () -> Unit,
    onOpenStreamingSettings: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("playlist_detail_screen"),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        // In-Page Utility Sub-bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = null,
                        tint = Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "SYNCHRONIZOVANÉ",
                        color = Secondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UtilityCircleButton(icon = Icons.Default.Search, onClick = onOpenExplore)
                    UtilityCircleButton(icon = Icons.Default.Cast, onClick = onOpenStreamingSettings)
                    UtilityCircleButton(icon = Icons.Default.Share, onClick = {})
                }
            }
        }

        // Hero Section: Synthwave Nights Cover + Badges + Actions
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Album Artwork with Depth Framing
                Box(
                    modifier = Modifier
                        .size(224.dp)
                        .shadow(28.dp, RoundedCornerShape(16.dp), ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerHigh)
                ) {
                    NetImage(
                        url = SoundWaveCatalog.SYNTHWAVE_NIGHTS_COVER_URL,
                        contentDescription = "Synthwave Nights",
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SurfaceContainerLowest.copy(alpha = 0.75f)
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Secondary)
                        )
                        Text(
                            text = "HI-RES LOSSLESS",
                            color = Secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Synthwave Nights",
                    color = OnSurface,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = OnSurfaceVariant)) {
                            append("Vytvoril ")
                        }
                        withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.SemiBold)) {
                            append("Alex • Nočný Jazdec")
                        }
                    },
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Navidrome & Audiophile Server Badges
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh)
                            .clickable { onOpenStreamingSettings() }
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Dns,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "music.homebox (Navidrome)",
                            color = OnSurface,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "FLAC Lossless • 24-bit / 44.1 kHz",
                            color = Secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "64 skladieb • 3 hod 48 min • Aktualizované pred 2 dňami",
                    color = OnSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Main Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .shadow(14.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                            .clip(RoundedCornerShape(50))
                            .background(PrimaryContainer)
                            .clickable { onTrackClick(SoundWaveCatalog.playlistTracks.first()) }
                            .testTag("playlist_play_btn"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Prehrať",
                            color = OnPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHighest)
                            .clickable { onTrackClick(SoundWaveCatalog.playlistTracks.random()) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Náhodne",
                            color = OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DownloadDone,
                            contentDescription = "Stiahnuté do offline",
                            tint = Secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // Tracklist Header with Controls
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Skladby",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "64",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainer)
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Vlastné poradie",
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                HorizontalDivider(color = SurfaceContainerHigh.copy(alpha = 0.45f))
                Spacer(modifier = Modifier.height(6.dp))

                SoundWaveCatalog.playlistTracks.forEachIndexed { index, track ->
                    val isActive = index == 0
                    val isLiked = track.id in uiState.likedTrackIds
                    PlaylistTrackItemRow(
                        index = index + 1,
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
}

@Composable
private fun UtilityCircleButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(SurfaceContainer)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OnSurface,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun PlaylistTrackItemRow(
    index: Int,
    track: TrackItem,
    isActive: Boolean,
    isLiked: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) SurfaceContainerLow else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier.width(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isActive) {
                    AnimatedEqualizerBars(
                        barColor = Primary,
                        secondaryBarColor = Primary,
                        barWidth = 3.dp,
                        maxHeight = 16.dp
                    )
                } else {
                    Text(
                        text = index.toString(),
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

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

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    color = if (isActive) Primary else OnSurface,
                    fontSize = 15.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (isActive) Primary.copy(alpha = 0.2f)
                                else SurfaceContainerHighest
                            )
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = track.badge,
                            color = when {
                                isActive -> Primary
                                track.isHiResBadgeCyan -> Secondary
                                else -> OnSurfaceVariant
                            },
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${track.artist} • ${track.album}",
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (track.isDownloaded) {
                Icon(
                    imageVector = Icons.Default.DownloadDone,
                    contentDescription = "Stiahnuté",
                    tint = Secondary,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(16.dp)
                )
            }
            Text(
                text = track.duration,
                color = if (isActive) Primary else OnSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
            IconButton(
                onClick = onLikeClick,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Obľúbené",
                    tint = if (isLiked) Primary else OnSurfaceVariant,
                    modifier = Modifier.size(19.dp)
                )
            }
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Možnosti",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}
