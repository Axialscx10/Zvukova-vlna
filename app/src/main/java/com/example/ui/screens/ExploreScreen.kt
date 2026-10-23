package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlbumItem
import com.example.data.ArtistItem
import com.example.data.SoundWaveCatalog
import com.example.data.TrackItem
import com.example.ui.SoundWaveUiState
import com.example.ui.components.AnimatedEqualizerBars
import com.example.ui.components.NetImage
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.PlusJakartaSans
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryFixed

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreScreen(
    uiState: SoundWaveUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onToggleAudiophileFilter: (String) -> Unit,
    onResetAudiophileFilters: () -> Unit,
    onOpenArtistDetail: () -> Unit,
    onToggleFollowArtist: () -> Unit,
    onTrackClick: (TrackItem) -> Unit,
    onTrackLikeToggle: (String) -> Unit,
    onAlbumClick: (AlbumItem) -> Unit,
    onArtistClick: (ArtistItem) -> Unit,
    onScanServerClick: () -> Unit,
    onServerStatusClick: () -> Unit
) {
    val categories = listOf("Všetko", "Skladby", "Interpreti", "Albumy", "Playlisty", "Žánre")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("explore_screen"),
        contentPadding = PaddingValues(bottom = 160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Server Status Badge & Active Search Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Server Status Pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerLow)
                        .clickable { onServerStatusClick() }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                        .testTag("explore_server_status_pill"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SecondaryContainer)
                        )
                        Icon(
                            imageVector = Icons.Default.Dns,
                            contentDescription = null,
                            tint = SecondaryFixed,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = OnSurface, fontWeight = FontWeight.SemiBold)) {
                                    append("music.homebox")
                                }
                                withStyle(SpanStyle(color = Outline)) {
                                    append(" · ")
                                }
                                withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                    append("Navidrome (42 850 skladieb)")
                                }
                            },
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "ONLINE",
                            color = SecondaryFixed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    }
                }

                // Active Search Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Hľadať",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchQueryChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = PlusJakartaSans,
                            color = OnSurface,
                            fontSize = 16.sp
                        ),
                        cursorBrush = SolidColor(Primary),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("explore_search_input"),
                        decorationBox = { innerTextField ->
                            if (uiState.searchQuery.isEmpty()) {
                                Text(
                                    text = "Hľadaj v knižnici Navidrome...",
                                    color = Outline,
                                    fontSize = 15.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { onSearchQueryChange("") },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Vymazať hľadanie",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = { onSearchQueryChange("Synthwave") },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Hlasové vyhľadávanie",
                            tint = SecondaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Horizontal Category Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = uiState.exploreCategory == category
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .then(
                                if (isSelected) {
                                    Modifier.shadow(10.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                                } else Modifier
                            )
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) PrimaryContainer else SurfaceContainerHigh)
                            .clickable { onCategorySelected(category) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Audiophile / Advanced Navidrome Filter Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLow)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(SecondaryContainer.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = SecondaryContainer,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = "Filtre (${uiState.activeAudiophileFilters.size} aktívne)",
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onResetAudiophileFilters() }
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Resetovať",
                            color = OnSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    AudiophileTagPill(
                        icon = Icons.Default.CheckCircle,
                        text = "FLAC / Bezstratové",
                        active = "FLAC / Bezstratové" in uiState.activeAudiophileFilters,
                        tint = SecondaryFixed,
                        iconTint = SecondaryContainer,
                        onClick = { onToggleAudiophileFilter("FLAC / Bezstratové") }
                    )
                    AudiophileTagPill(
                        icon = Icons.Default.GraphicEq,
                        text = "Vzorkovanie ≥ 24-bit",
                        active = "Vzorkovanie ≥ 24-bit" in uiState.activeAudiophileFilters,
                        tint = SecondaryFixed,
                        iconTint = SecondaryContainer,
                        onClick = { onToggleAudiophileFilter("Vzorkovanie ≥ 24-bit") }
                    )
                    AudiophileTagPill(
                        icon = Icons.Default.CalendarToday,
                        text = "Rok: 1980 – 1989 (Synth)",
                        active = "Rok: 1980 – 1989 (Synth)" in uiState.activeAudiophileFilters,
                        tint = TertiaryFixed,
                        iconTint = Tertiary,
                        onClick = { onToggleAudiophileFilter("Rok: 1980 – 1989 (Synth)") }
                    )
                    AudiophileTagPill(
                        icon = null,
                        text = "Bitrate ≥ 320 kbps",
                        active = "Bitrate ≥ 320 kbps" in uiState.activeAudiophileFilters,
                        tint = OnSurfaceVariant,
                        iconTint = OnSurfaceVariant,
                        isAddable = true,
                        onClick = { onToggleAudiophileFilter("Bitrate ≥ 320 kbps") }
                    )
                }
            }
        }

        // Top Result (Najlepšia zhoda) Card -> The Midnight
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
                    Text(
                        text = "NAJLEPŠIA ZHODA",
                        color = Primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = "Navidrome ID: #art-409",
                        color = Outline,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(SurfaceContainer, Color(0xFF2B1B19))
                            )
                        )
                        .clickable { onOpenArtistDetail() }
                        .padding(16.dp)
                        .testTag("explore_top_match_card"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHigh)
                    ) {
                        NetImage(
                            url = SoundWaveCatalog.EXPLORE_TOP_MATCH_IMAGE,
                            contentDescription = "The Midnight",
                            modifier = Modifier.fillMaxSize()
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = 0.75f))
                                .padding(horizontal = 5.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = SecondaryFixed,
                                modifier = Modifier.size(11.dp)
                            )
                            Text(
                                text = "FLAC",
                                color = SecondaryFixed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "INTERPRET",
                                    color = SecondaryFixed,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Los Angeles, CA",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "The Midnight",
                            color = OnSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "24 albumov · 186 skladieb na serveri",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .shadow(8.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                                    .clip(RoundedCornerShape(50))
                                    .background(PrimaryContainer)
                                    .clickable { onOpenArtistDetail() }
                                    .padding(horizontal = 14.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = OnPrimaryContainer,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Prehrať",
                                    color = OnPrimaryContainer,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHighest)
                                    .clickable { onToggleFollowArtist() }
                                    .padding(horizontal = 12.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (uiState.isArtistFollowed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = if (uiState.isArtistFollowed) PrimaryContainer else OnSurface,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (uiState.isArtistFollowed) "Sledované" else "Sledovať",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tracks Section (Skladby 28)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
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
                                text = "28",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "Zobraziť všetky",
                        color = Primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onOpenArtistDetail() }
                    )
                }

                SoundWaveCatalog.exploreTracks.forEachIndexed { index, track ->
                    val isLiked = track.id in uiState.likedTrackIds
                    val isFirstActive = index == 0 || uiState.currentTrack.id == track.id
                    ExploreTrackRow(
                        track = track,
                        isPlayingHighlight = isFirstActive,
                        isLiked = isLiked,
                        onClick = { onTrackClick(track) },
                        onLikeClick = { onTrackLikeToggle(track.id) }
                    )
                }
            }
        }

        // Albums Grid Section (Albumy na serveri)
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
                    Text(
                        text = "Albumy na serveri",
                        color = OnSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Všetky (14)",
                        color = Primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onOpenArtistDetail() }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SoundWaveCatalog.exploreAlbums.forEach { album ->
                        ExploreAlbumGridCard(
                            album = album,
                            onClick = { onAlbumClick(album) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Artists Circular Shelves (Interpreti v žánri)
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
                    Text(
                        text = "Interpreti v žánri",
                        color = OnSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Všetci",
                        color = Primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onOpenArtistDetail() }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SoundWaveCatalog.exploreArtists.forEach { artist ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceContainerLow)
                                .clickable { onArtistClick(artist) }
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerHigh)
                            ) {
                                NetImage(
                                    url = artist.imageUrl,
                                    contentDescription = artist.name,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Text(
                                text = artist.name,
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = artist.trackCount,
                                color = OnSurfaceVariant,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        // Navidrome Server Fast-Sync Micro Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainer)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SecondaryFixed.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            tint = SecondaryFixed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Posledná synchronizácia servera",
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = uiState.lastSyncTime,
                            color = OnSurfaceVariant,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHighest)
                        .clickable { onScanServerClick() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("explore_scan_server_btn")
                ) {
                    Text(
                        text = if (uiState.isScanningServer) "Skenujem..." else "Skenovať",
                        color = OnSurface,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun AudiophileTagPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    text: String,
    active: Boolean,
    tint: Color,
    iconTint: Color,
    isAddable: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (active) SurfaceContainerHighest else SurfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 9.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(13.dp)
            )
        }
        Text(
            text = text,
            color = if (active) tint else OnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            imageVector = if (isAddable && !active) Icons.Default.Add else Icons.Default.Close,
            contentDescription = null,
            tint = if (active) tint else OnSurfaceVariant,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun ExploreTrackRow(
    track: TrackItem,
    isPlayingHighlight: Boolean,
    isLiked: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    .background(SurfaceContainerHigh)
            ) {
                NetImage(
                    url = track.imageUrl,
                    contentDescription = track.title,
                    modifier = Modifier.fillMaxSize()
                )
                if (isPlayingHighlight) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryContainer.copy(alpha = 0.22f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedEqualizerBars(
                            barColor = PrimaryContainer,
                            secondaryBarColor = PrimaryContainer,
                            barWidth = 3.dp,
                            maxHeight = 16.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = track.title,
                        color = if (isPlayingHighlight) Primary else OnSurface,
                        fontSize = 14.sp,
                        fontWeight = if (isPlayingHighlight) FontWeight.Bold else FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (track.isHiResBadgeCyan) SecondaryFixed.copy(alpha = 0.2f)
                                else SurfaceVariant
                            )
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = track.badge,
                            color = if (track.isHiResBadgeCyan) SecondaryFixed else OnSurfaceVariant,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${track.artist} · ${track.album}",
                    color = OnSurfaceVariant,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = track.duration,
                color = OnSurfaceVariant,
                fontSize = 12.sp
            )
            IconButton(
                onClick = onLikeClick,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Obľúbené",
                    tint = if (isLiked) PrimaryContainer else OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Možnosti",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ExploreAlbumGridCard(
    album: AlbumItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainer)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerHighest)
        ) {
            NetImage(
                url = album.imageUrl,
                contentDescription = album.title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = album.badge,
                    color = SecondaryFixed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column {
            Text(
                text = album.title,
                color = OnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
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
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = album.techSpec,
                color = Outline,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
