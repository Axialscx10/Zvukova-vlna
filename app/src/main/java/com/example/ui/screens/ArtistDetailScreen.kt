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
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlbumItem
import com.example.data.SoundWaveCatalog
import com.example.data.TrackItem
import com.example.ui.SoundWaveUiState
import com.example.ui.components.AnimatedEqualizerBars
import com.example.ui.components.NetImage
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryFixedDim
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest

@Composable
fun ArtistDetailScreen(
    uiState: SoundWaveUiState,
    onFilterChipSelected: (String) -> Unit,
    onToggleFavoriteArtist: () -> Unit,
    onToggleDownloadDiscography: () -> Unit,
    onTrackClick: (TrackItem) -> Unit,
    onOpenStreamingSettings: () -> Unit,
    onOpenExplore: () -> Unit
) {
    val chips = listOf(
        "Populárne",
        "Albumy (8)",
        "EP a Single (12)",
        "Živé vystúpenia & Remixy (4)",
        "Podobní interpreti"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("artist_detail_screen"),
        contentPadding = PaddingValues(bottom = 140.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top Utility Bar inside Content
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLowest.copy(alpha = 0.85f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.clickable { onOpenStreamingSettings() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SecondaryFixedDim)
                    )
                    Text(
                        text = "MUSIC.HOMEBOX (SUBSONIC V1.16)",
                        color = SecondaryFixed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onOpenStreamingSettings, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Cast, contentDescription = "Cast", tint = OnSurface, modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = onOpenExplore, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = OnSurface, modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = onOpenStreamingSettings, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Tune, contentDescription = "Server options", tint = OnSurface, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        // Hero Section: Synthwave Visual Backdrop + Action Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(SurfaceContainerLow)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(276.dp)
                    ) {
                        NetImage(
                            url = SoundWaveCatalog.THE_MIDNIGHT_HERO_URL,
                            contentDescription = "The Midnight",
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            SurfaceContainerLowest.copy(alpha = 0.55f),
                                            SurfaceContainerLowest
                                        )
                                    )
                                )
                        )

                        // Top Left FLAC Studio Master Badge
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(14.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerLowest.copy(alpha = 0.82f))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = null,
                                tint = SecondaryFixed,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "FLAC STUDIO MASTER",
                                color = SecondaryFixed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        }

                        // Top Right Navidrome Synced Pill
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(14.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerLowest.copy(alpha = 0.82f))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Navidrome Synced",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Hero Bottom Content Overlay
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(PrimaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "TOP ARTIST",
                                        color = OnPrimaryContainer,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.6.sp
                                    )
                                }
                                Text(
                                    text = "Los Angeles, USA",
                                    color = OnSurfaceVariant,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Text(
                                text = "The Midnight",
                                color = OnSurface,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.5).sp
                            )
                            Text(
                                text = "Synthwave / Retrowave • Tim McEwan & Tyler Lyle",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Server Stats Bar (Navidrome Telemetry)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerHigh.copy(alpha = 0.9f))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FolderSpecial,
                                contentDescription = null,
                                tint = SecondaryFixedDim,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.Bold)) {
                                        append("24")
                                    }
                                    withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                        append(" albumov  •  ")
                                    }
                                    withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.Bold)) {
                                        append("186")
                                    }
                                    withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                        append(" skladieb")
                                    }
                                },
                                fontSize = 12.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = SecondaryFixed,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "100% FLAC",
                                color = SecondaryFixed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Quick Action Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .shadow(12.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                            .clip(RoundedCornerShape(50))
                            .background(PrimaryContainer)
                            .clickable { onTrackClick(SoundWaveCatalog.artistTopTracks.first()) }
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = OnPrimaryContainer,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Rádio interpreta",
                            color = OnPrimaryContainer,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh)
                            .clickable { onTrackClick(SoundWaveCatalog.artistTopTracks.random()) }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Náhodne",
                            color = OnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh)
                            .clickable { onToggleFavoriteArtist() }
                            .testTag("artist_favorite_btn"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (uiState.isArtistFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Pridať do obľúbených",
                            tint = if (uiState.isArtistFavorited) PrimaryContainer else OnSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Download Offline Discography Pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .clickable { onToggleDownloadDiscography() }
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isDiscographyDownloading) Icons.Default.DownloadDone else Icons.Default.DownloadForOffline,
                            contentDescription = null,
                            tint = SecondaryFixed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (uiState.isDiscographyDownloading) "Diskografia pripravená offline" else "Stiahnuť diskografiu offline",
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHighest)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "8.4 GB FLAC",
                            color = SecondaryFixed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Horizontal Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chips.forEach { chip ->
                    val isSelected = uiState.artistFilterChip == chip
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) OnSurface else SurfaceContainerHigh)
                            .clickable { onFilterChipSelected(chip) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chip,
                            color = if (isSelected) Surface else OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Section: Najhranejšie na serveri
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Najhranejšie na serveri",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Metriky prehrávania z music.homebox",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = PrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }

                SoundWaveCatalog.artistTopTracks.forEachIndexed { index, track ->
                    val isActive = index == 0
                    ArtistTopTrackRow(
                        index = index + 1,
                        track = track,
                        isActive = isActive,
                        onClick = { onTrackClick(track) }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .clickable { onTrackClick(SoundWaveCatalog.artistTopTracks.first()) }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Zobraziť všetkých 20 najhranejších",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = OnSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Section: Štúdiové albumy
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Štúdiové albumy",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "8 kompletných master vydaní na úložisku",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Všetko",
                            color = Primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Row 1: Endless Summer & Monsters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ArtistStudioAlbumCard(
                        album = SoundWaveCatalog.artistStudioAlbums[0],
                        onClick = { onTrackClick(SoundWaveCatalog.artistTopTracks[0]) },
                        modifier = Modifier.weight(1f)
                    )
                    ArtistStudioAlbumCard(
                        album = SoundWaveCatalog.artistStudioAlbums[1],
                        onClick = { onTrackClick(SoundWaveCatalog.artistTopTracks[1]) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: Heroes & Kids
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ArtistStudioAlbumCard(
                        album = SoundWaveCatalog.artistStudioAlbums[2],
                        onClick = { onTrackClick(SoundWaveCatalog.artistTopTracks[2]) },
                        modifier = Modifier.weight(1f)
                    )
                    ArtistStudioAlbumCard(
                        album = SoundWaveCatalog.artistStudioAlbums[3],
                        onClick = { onTrackClick(SoundWaveCatalog.artistTopTracks[3]) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Full-width Album 5: Nocturnal
                val nocturnal = SoundWaveCatalog.artistNocturnalAlbum
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .clickable { onTrackClick(SoundWaveCatalog.artistTopTracks[4]) }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerHighest)
                    ) {
                        NetImage(
                            url = nocturnal.imageUrl,
                            contentDescription = nocturnal.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = nocturnal.badge,
                                color = SecondaryFixed,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = nocturnal.title,
                            color = OnSurface,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = nocturnal.subtitle,
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = nocturnal.techSpec,
                            color = SecondaryFixed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Section: EP, Single & Remixy (Horizontal Carousel)
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                            text = "EP, Single & Remixy",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rarity a špeciálne verzie na serveri",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "12 položiek",
                            color = OnSurfaceVariant,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(SoundWaveCatalog.artistSinglesAndEps, key = { it.id }) { ep ->
                        Column(
                            modifier = Modifier
                                .width(144.dp)
                                .clickable { onTrackClick(SoundWaveCatalog.artistTopTracks.first()) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(144.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHighest)
                            ) {
                                NetImage(
                                    url = ep.imageUrl,
                                    contentDescription = ep.title,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SurfaceContainerLowest.copy(alpha = 0.85f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = ep.badge,
                                        color = if (ep.isOutlineBadge) Outline else SecondaryFixed,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = ep.title,
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = ep.subtitle,
                                color = OnSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Section: O interpretovi & Navidrome Server Tag
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLow)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "O interpretovi",
                        color = OnSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.Bold)) {
                            append("The Midnight")
                        }
                        withStyle(SpanStyle(color = OnSurfaceVariant)) {
                            append(" je synthwave/retrowave duo zložené zo speváka a skladateľa Tylera Lylea z Atlanty a dánskeho producenta Tima McEwana z Los Angeles. Projekt vznikol v roku 2014 a spája melancholické texty, saxofónové sóla a mohutné analógové basové linky.")
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )

                // Technical Server Telemetry Panel
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.75f))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Dns,
                                contentDescription = null,
                                tint = SecondaryFixed,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "NAVIDROME SERVER TAG",
                                color = SecondaryFixed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "music.homebox",
                                color = SecondaryFixed,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    TelemetryRow("Subsonic Artist ID:", "#art-409", OnSurface, isMono = true)
                    TelemetryRow("Umiestnenie na disku:", "/mnt/storage/music/The Midnight/", OnSurface, isMono = true)
                    TelemetryRow("MusicBrainz MBID:", "d8b67f34-3151-4034...", SecondaryFixedDim, isMono = true)
                    TelemetryRow("Posledná synchronizácia:", "Dnes, 03:15 (Auto-Scan OK)", OnSurface, isMono = false)
                }
            }
        }
    }
}

@Composable
private fun TelemetryRow(
    label: String,
    value: String,
    valueColor: Color,
    isMono: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = OnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 10.sp,
            fontFamily = if (isMono) FontFamily.Monospace else null,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun ArtistTopTrackRow(
    index: Int,
    track: TrackItem,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) SurfaceContainerHigh.copy(alpha = 0.65f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(8.dp),
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
                        barColor = PrimaryContainer,
                        secondaryBarColor = SecondaryFixed,
                        barWidth = 2.5.dp,
                        maxHeight = 14.dp
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

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
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
                    fontSize = 14.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = track.album,
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Text("•", color = OnSurfaceVariant, fontSize = 11.sp)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainerHighest)
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = track.badge,
                            color = if (track.isHiResBadgeCyan) SecondaryFixed else Outline,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = track.playCount ?: "",
                    color = OnSurfaceVariant,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = track.duration,
                    color = OnSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ArtistStudioAlbumCard(
    album: AlbumItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHighest)
        ) {
            NetImage(
                url = album.imageUrl,
                contentDescription = album.title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = album.badge,
                    color = if (album.isOutlineBadge) Outline else SecondaryFixed,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (album.isDownloaded) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(SecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DownloadDone,
                        contentDescription = null,
                        tint = OnSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Column {
            Text(
                text = album.title,
                color = OnSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = album.subtitle,
                color = OnSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = album.techSpec,
                color = if (album.isDownloaded) SecondaryFixedDim else OnSurfaceVariant,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
