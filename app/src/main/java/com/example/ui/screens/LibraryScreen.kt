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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Sync
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
import com.example.data.PlaylistItem
import com.example.ui.SoundWaveUiState
import com.example.ui.components.NetImage
import com.example.ui.theme.AmberStar
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryFixedDim
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow

@Composable
fun LibraryScreen(
    uiState: SoundWaveUiState,
    onFilterChipSelected: (String) -> Unit,
    onToggleGridView: () -> Unit,
    onOpenPlaylistDetail: () -> Unit,
    onOpenArtistDetail: () -> Unit,
    onOpenExplore: () -> Unit,
    onCreateNewPlaylist: () -> Unit
) {
    val chips = listOf("Playlists", "Skladby", "Albumy", "Interpreti", "Stiahnuté")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("library_screen"),
        contentPadding = PaddingValues(bottom = 160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Filter Chips Scroll Area
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chips.forEach { chip ->
                    val isSelected = uiState.libraryFilterChip == chip
                    Row(
                        modifier = Modifier
                            .height(34.dp)
                            .then(
                                if (isSelected) {
                                    Modifier.shadow(12.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                                } else Modifier
                            )
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) PrimaryContainer else SurfaceContainerHigh)
                            .clickable { onFilterChipSelected(chip) }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (chip == "Playlists") {
                            Icon(
                                imageVector = Icons.Default.QueueMusic,
                                contentDescription = null,
                                tint = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        } else if (chip == "Stiahnuté") {
                            Icon(
                                imageVector = Icons.Default.DownloadDone,
                                contentDescription = null,
                                tint = if (isSelected) OnPrimaryContainer else SecondaryFixedDim,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = chip,
                            color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Sorting & Layout Controls Toolbar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerLow)
                        .clickable { }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Posledná aktivita",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onToggleGridView,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.libraryIsGridView) Icons.Default.FormatListBulleted else Icons.Default.GridView,
                            contentDescription = "Prepnúť zobrazenie",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = onOpenExplore,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Filtrovať v knižnici",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Pinned Priority Hubs: Obľúbené & Stiahnuté
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Liked Songs Hub Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF33201E),
                                    SurfaceContainer,
                                    SurfaceContainerLow
                                )
                            )
                        )
                        .clickable { onOpenPlaylistDetail() }
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(modifier = Modifier.size(48.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .shadow(12.dp, RoundedCornerShape(12.dp), spotColor = PrimaryContainer)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            Brush.linearGradient(
                                                listOf(PrimaryContainer, Primary)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = OnPrimary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = 4.dp, y = (-4).dp)
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Surface),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PushPin,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHighest.copy(alpha = 0.85f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Auto",
                                    color = OnSurfaceVariant,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Obľúbené",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "342 skladieb",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(OutlineVariant)
                            )
                            Text(
                                text = "Aktívne",
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Downloads Offline Hub Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF152C2E),
                                    SurfaceContainer,
                                    SurfaceContainerLow
                                )
                            )
                        )
                        .clickable { onOpenArtistDetail() }
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OfflinePin,
                                    contentDescription = null,
                                    tint = SecondaryFixed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(4.dp)
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Secondary)
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = null,
                                tint = SecondaryFixedDim,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Stiahnuté",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "18.4 GB • 1,240 skladieb",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Section Title: Kolekcia & Zoznamy
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Kolekcia & Zoznamy",
                            color = OnSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHighest)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Navidrome",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onOpenPlaylistDetail() }
                    ) {
                        Text(
                            text = "Všetko",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Library Playlists List
                uiState.libraryPlaylists.forEach { playlist ->
                    LibraryPlaylistRow(
                        playlist = playlist,
                        onClick = { onOpenPlaylistDetail() }
                    )
                }
            }
        }

        // Create Playlist Prompt Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHighest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Nový zoznam skladieb",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Vytvor alebo importuj z m3u",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh)
                        .clickable { onCreateNewPlaylist() }
                        .padding(horizontal = 16.dp, vertical = 7.dp)
                        .testTag("library_create_playlist_btn")
                ) {
                    Text(
                        text = "Vytvoriť",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun LibraryPlaylistRow(
    playlist: PlaylistItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onClick)
            .padding(8.dp)
            .testTag("library_playlist_${playlist.id}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerHighest)
            ) {
                NetImage(
                    url = playlist.imageUrl,
                    contentDescription = playlist.title,
                    modifier = Modifier.fillMaxSize()
                )
                if (playlist.isPinned) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(3.dp)
                            .size(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Surface.copy(alpha = 0.85f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PushPin,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(11.dp)
                        )
                    }
                }
                if (playlist.badge == "HQ") {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.65f))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "HQ",
                            color = SecondaryFixed,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (playlist.isOffline) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(3.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(SecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = OnSecondaryContainer,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
                if (playlist.badge == "FLAC 24b") {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "FLAC 24b",
                            color = Secondary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = playlist.title,
                        color = OnSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (playlist.badge == "OFFLINE") {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainerHighest)
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "OFFLINE",
                                color = SecondaryFixed,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (playlist.isStarred) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = AmberStar,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (playlist.isSynced) {
                        Icon(
                            imageVector = Icons.Default.CloudDone,
                            contentDescription = null,
                            tint = PrimaryContainer,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                    val subtitleColor = when {
                        playlist.isStarred -> Secondary
                        playlist.isSynced || playlist.isOffline -> OnSurface
                        else -> OnSurfaceVariant
                    }
                    Text(
                        text = playlist.subtitle,
                        color = subtitleColor,
                        fontSize = 12.sp,
                        fontWeight = if (playlist.isStarred) FontWeight.Medium else FontWeight.Normal
                    )
                    Text("•", color = OnSurfaceVariant, fontSize = 12.sp)
                    Text(
                        text = playlist.trackCount,
                        color = OnSurfaceVariant,
                        fontSize = 12.sp
                    )
                    if (playlist.durationOrExtra.isNotEmpty()) {
                        Text("•", color = OnSurfaceVariant, fontSize = 12.sp)
                        Text(
                            text = playlist.durationOrExtra,
                            color = Outline,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
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
