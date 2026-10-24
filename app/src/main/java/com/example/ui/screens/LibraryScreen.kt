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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.TrackItem
import com.example.data.plural
import com.example.data.toRow
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
    onOpenCollection: (String) -> Unit,
    onTrackClick: (TrackItem, List<TrackItem>) -> Unit,
    onOpenExplore: () -> Unit,
    onCreateNewPlaylist: (String) -> Unit,
    onPlayRandomMix: () -> Unit,
    onOpenServerLogin: () -> Unit,
    onRetry: () -> Unit,
    onPlayAll: (List<TrackItem>, Boolean) -> Unit = { _, _ -> },
    onOpenTrackMenu: (TrackItem) -> Unit = {},
    onClearDownloads: () -> Unit = {},
    onClearHistory: () -> Unit = {}
) {
    val chips = listOf("Playlisty", "História", "Obľúbené", "Stiahnuté", "Albumy", "Interpreti")

    var showCreateDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }
    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Nový playlist") },
            text = {
                OutlinedTextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    singleLine = true,
                    label = { Text("Názov") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onCreateNewPlaylist(newPlaylistName)
                    newPlaylistName = ""
                    showCreateDialog = false
                }) { Text("Vytvoriť") }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) { Text("Zrušiť") }
            }
        )
    }

    val isHistory = uiState.libraryFilterChip == "História"
    val isFavorites = uiState.libraryFilterChip == "Obľúbené"
    val isDownloaded = uiState.libraryFilterChip == "Stiahnuté"

    // Rows for the currently selected chip (albums / artists / playlists share the same row layout).
    val rows: List<PlaylistItem> = when (uiState.libraryFilterChip) {
        "Albumy" -> uiState.libraryAlbums.map { it.toRow() }
        "Interpreti" -> uiState.libraryArtists.map { it.toRow() }
        "Playlisty", "Playlists" -> uiState.libraryPlaylists
        else -> emptyList()
    }
    val itemCount = when {
        isHistory -> uiState.historyTracks.size
        isFavorites -> uiState.favoriteTracks.size
        isDownloaded -> uiState.downloadedTracks.size
        else -> rows.size
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("library_screen"),
        contentPadding = PaddingValues(bottom = 160.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        if (chip == "Playlisty" || chip == "Playlists") {
                            Icon(
                                imageVector = Icons.Default.QueueMusic,
                                contentDescription = null,
                                tint = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
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

        // Layout Controls Toolbar
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
                        text = "${uiState.libraryFilterChip} · $itemCount",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
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
                            contentDescription = "Hľadať",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Priority hubs: Favourites & Random mix
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
                        .clickable { onOpenCollection("fav") }
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
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

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Obľúbené",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${uiState.favoriteTracks.size} ${plural(uiState.favoriteTracks.size, "skladba", "skladby", "skladieb")}",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }

                // Random mix hub card
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
                        .clickable { onPlayRandomMix() }
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = null,
                                tint = SecondaryFixed,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Náhodný mix",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Náhodné skladby zo servera",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Empty / loading / error states
        if (itemCount == 0) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val msg = when {
                        isHistory -> "Zatiaľ žiadna história prehrávania. Skladby, ktoré si pustíš, sa zobrazia tu."
                        isDownloaded -> "Nemáš žiadne stiahnuté skladby. Skladby si môžeš stiahnuť v prehrávači alebo cez ponuku skladby."
                        !uiState.isLoggedIn -> "Najprv sa pripoj k svojmu Navidrome serveru."
                        uiState.libraryLoading -> "Načítavam knižnicu…"
                        uiState.libraryError != null -> uiState.libraryError ?: ""
                        isFavorites -> "Zatiaľ nemáš žiadne obľúbené skladby. Označ ich srdiečkom."
                        else -> "V tejto sekcii zatiaľ nič nie je."
                    }
                    Text(text = msg, color = OnSurfaceVariant, fontSize = 14.sp)
                    if (!uiState.isLoggedIn) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(PrimaryContainer)
                                .clickable { onOpenServerLogin() }
                                .padding(horizontal = 22.dp, vertical = 10.dp)
                        ) {
                            Text("Pripojiť k Navidrome", color = OnPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    } else if (!uiState.libraryLoading && uiState.libraryError != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .clickable { onRetry() }
                                .padding(horizontal = 22.dp, vertical = 10.dp)
                        ) {
                            Text("Skúsiť znova", color = OnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // The list itself
        if (isHistory) {
            if (uiState.historyTracks.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "História prehrávania (${uiState.historyTracks.size})",
                                color = OnSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Uložené v pamäti zariadenia",
                                color = SecondaryFixed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(PrimaryContainer)
                                    .clickable { onPlayAll(uiState.historyTracks, false) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Prehrať",
                                    color = OnPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            IconButton(
                                onClick = onClearHistory,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Vymazať históriu",
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
                items(uiState.historyTracks, key = { it.id }) { track ->
                    LibraryTrackRow(
                        track = track,
                        isActive = uiState.currentTrack.id == track.id,
                        isDownloaded = track.id in uiState.downloadedTrackIds,
                        onClick = { onTrackClick(track, uiState.historyTracks) },
                        onMenuClick = { onOpenTrackMenu(track) }
                    )
                }
            }
        } else if (isDownloaded) {
            if (uiState.downloadedTracks.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Stiahnuté skladby (${uiState.downloadedTracks.size})",
                                color = OnSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "V pamäti: ${uiState.downloadedSizeText}",
                                color = SecondaryFixed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(PrimaryContainer)
                                    .clickable { onPlayAll(uiState.downloadedTracks, false) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Prehrať",
                                    color = OnPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            IconButton(
                                onClick = onClearDownloads,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Vymazať stiahnuté",
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
                items(uiState.downloadedTracks, key = { it.id }) { track ->
                    LibraryTrackRow(
                        track = track,
                        isActive = uiState.currentTrack.id == track.id,
                        isDownloaded = true,
                        onClick = { onTrackClick(track, uiState.downloadedTracks) },
                        onMenuClick = { onOpenTrackMenu(track) }
                    )
                }
            }
        } else if (isFavorites) {
            items(uiState.favoriteTracks, key = { it.id }) { track ->
                LibraryTrackRow(
                    track = track,
                    isActive = uiState.currentTrack.id == track.id,
                    isDownloaded = track.id in uiState.downloadedTrackIds,
                    onClick = { onTrackClick(track, uiState.favoriteTracks) },
                    onMenuClick = { onOpenTrackMenu(track) }
                )
            }
        } else if (uiState.libraryIsGridView) {
            items(rows.chunked(2), key = { chunk -> chunk.first().id }) { pair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    pair.forEach { item ->
                        LibraryGridCard(
                            item = item,
                            round = uiState.libraryFilterChip == "Interpreti",
                            onClick = { onOpenCollection(item.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (pair.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        } else {
            items(rows, key = { it.id }) { item ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    LibraryPlaylistRow(
                        playlist = item,
                        onClick = { onOpenCollection(item.id) }
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
                            text = "Vytvorí sa priamo na serveri",
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh)
                        .clickable { showCreateDialog = true }
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
private fun LibraryTrackRow(
    track: TrackItem,
    isActive: Boolean,
    isDownloaded: Boolean = false,
    onClick: () -> Unit,
    onMenuClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerHighest)
        ) {
            NetImage(
                url = track.imageUrl,
                contentDescription = track.title,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = track.title,
                    color = if (isActive) Primary else OnSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isDownloaded) {
                    Icon(
                        imageVector = Icons.Default.DownloadDone,
                        contentDescription = "Stiahnuté",
                        tint = SecondaryFixed,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(
                text = "${track.artist} · ${track.album}",
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
                modifier = Modifier.size(36.dp)
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
private fun LibraryGridCard(
    item: PlaylistItem,
    round: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(if (round) CircleShape else RoundedCornerShape(10.dp))
                .background(SurfaceContainerHighest)
        ) {
            NetImage(
                url = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column {
            Text(
                text = item.title,
                color = OnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.subtitle.ifBlank { item.trackCount },
                color = OnSurfaceVariant,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
                    if (playlist.subtitle.isNotBlank()) {
                        Text(
                            text = playlist.subtitle,
                            color = subtitleColor,
                            fontSize = 12.sp,
                            fontWeight = if (playlist.isStarred) FontWeight.Medium else FontWeight.Normal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (playlist.trackCount.isNotBlank()) {
                            Text("•", color = OnSurfaceVariant, fontSize = 12.sp)
                        }
                    }
                    Text(
                        text = playlist.trackCount,
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1
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
