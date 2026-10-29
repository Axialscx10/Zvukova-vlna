package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.data.AppScreen
import com.example.data.SleepTimerOption
import com.example.data.TrackItem
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryFixedDim
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryContainer

@Composable
fun NetImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = {
            SynthwaveFallbackArt()
        },
        error = {
            SynthwaveFallbackArt()
        }
    )
}

@Composable
fun SynthwaveFallbackArt() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF280B2E),
                        Color(0xFF4F1228),
                        Color(0xFF0A2436)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Album,
            contentDescription = null,
            tint = Primary.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun AnimatedEqualizerBars(
    modifier: Modifier = Modifier,
    barColor: Color = PrimaryContainer,
    secondaryBarColor: Color = SecondaryFixed,
    barWidth: Dp = 3.dp,
    maxHeight: Dp = 16.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "eq_bars")
    val bar1 by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(420, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "b1"
    )
    val bar2 by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(350, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "b2"
    )
    val bar3 by infiniteTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "b3"
    )

    Row(
        modifier = modifier.height(maxHeight),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = Modifier
                .width(barWidth)
                .fillMaxHeight(bar1)
                .clip(RoundedCornerShape(50))
                .background(barColor)
        )
        Box(
            modifier = Modifier
                .width(barWidth)
                .fillMaxHeight(bar2)
                .clip(RoundedCornerShape(50))
                .background(secondaryBarColor)
        )
        Box(
            modifier = Modifier
                .width(barWidth)
                .fillMaxHeight(bar3)
                .clip(RoundedCornerShape(50))
                .background(barColor)
        )
    }
}

@Composable
fun SoundWaveBrandLogoCircle(
    useGraphicEqIcon: Boolean = false,
    size: Dp = 34.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                androidx.compose.ui.graphics.Brush.radialGradient(
                    colors = listOf(Color(0xFF28204E), Color(0xFF0A0B14))
                )
            )
            .border(
                width = 1.5.dp,
                brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                    colors = listOf(Color(0xFF00E5FF), Color(0xFFFF5545))
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (useGraphicEqIcon) Icons.Default.GraphicEq else Icons.Default.Headphones,
            contentDescription = "SoundWave Logo",
            tint = if (useGraphicEqIcon) Color(0xFF00E5FF) else Color.White,
            modifier = Modifier.size(size * 0.58f)
        )
    }
}

@Composable
fun SoundWaveMainTopBar(
    useGraphicEqLogo: Boolean = false,
    showSearchIcon: Boolean = true,
    onCastClick: () -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEqualizerQuickClick: () -> Unit
) {
    Surface(
        color = Surface.copy(alpha = 0.92f),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(64.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable { onProfileClick() }
            ) {
                SoundWaveBrandLogoCircle(useGraphicEqIcon = useGraphicEqLogo)
                Text(
                    text = "SoundWave",
                    color = OnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.3).sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onEqualizerQuickClick,
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("top_eq_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Equalizer,
                        contentDescription = "Ekvalizér A Zvukové Profily",
                        tint = Primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                IconButton(
                    onClick = onCastClick,
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("top_cast_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Cast,
                        contentDescription = "Cast / Server",
                        tint = OnSurface,
                        modifier = Modifier.size(22.dp)
                    )
                }
                if (showSearchIcon) {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("top_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Hľadať",
                            tint = OnSurface,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Primary)
                        .clickable { onProfileClick() }
                        .testTag("top_profile_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil / Obrazovky",
                        tint = OnPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StackTopBar(
    title: String,
    showLogo: Boolean = true,
    masterSwitchChecked: Boolean? = null,
    onMasterSwitchChange: ((Boolean) -> Unit)? = null,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = Surface.copy(alpha = 0.92f),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(64.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("stack_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Späť",
                        tint = OnSurface
                    )
                }
                if (showLogo) {
                    Spacer(modifier = Modifier.width(4.dp))
                    SoundWaveBrandLogoCircle(useGraphicEqIcon = false, size = 32.dp)
                    Spacer(modifier = Modifier.width(10.dp))
                } else {
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = title,
                    color = OnSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (masterSwitchChecked != null && onMasterSwitchChange != null) {
                    Switch(
                        checked = masterSwitchChecked,
                        onCheckedChange = onMasterSwitchChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Primary,
                            uncheckedThumbColor = OnSurfaceVariant,
                            uncheckedTrackColor = SurfaceContainerHighest
                        ),
                        modifier = Modifier.testTag("eq_master_switch")
                    )
                }
                IconButton(
                    onClick = onMoreClick,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("stack_more_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Viac možností / Prepnúť obrazovku",
                        tint = OnSurface
                    )
                }
                if (showLogo) {
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { onProfileClick() }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profil",
                                tint = OnPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlassMiniPlayerDock(
    track: TrackItem,
    isPlaying: Boolean,
    progress: Float,
    isLiked: Boolean,
    subtitleVariant: String = "",
    badgeText: String = "",
    showSkipPrevious: Boolean = false,
    showCastButton: Boolean = false,
    showAirPodsSubtitle: Boolean = false,
    onDockClick: () -> Unit,
    onLikeClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit = {},
    onCastClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 3.dp)
            .shadow(16.dp, RoundedCornerShape(12.dp), spotColor = Color.Black)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1B1A22)) // 100% solid opaque - zero bleed-through!
            .border(1.dp, Color(0xFF2C2B36), RoundedCornerShape(12.dp))
            .clickable { onDockClick() }
            .testTag("mini_player_dock")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Slim progress line (2.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFF262530))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0.01f, 1f))
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Primary, PrimaryContainer)
                            )
                        )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Album Art (38dp x 38dp)
                Box(
                    modifier = Modifier
                        .size(38.dp)
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

                // Track title & badge, artist/subtitle
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = track.title,
                            color = OnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false) // Title yields space to badge!
                        )
                        if (badgeText.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SecondaryFixed.copy(alpha = 0.22f))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = badgeText,
                                    color = SecondaryFixed,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    softWrap = false, // Never wrap vertically!
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(1.dp))

                    Text(
                        text = subtitleVariant.ifBlank { track.artist },
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Controls: Play/Pause, Next, and Dismiss ("X")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // Play / Pause Button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(6.dp, CircleShape, spotColor = PrimaryContainer)
                            .clip(CircleShape)
                            .background(PrimaryContainer)
                            .clickable { onPlayPauseClick() }
                            .testTag("mini_player_play_pause"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pozastaviť" else "Prehrať",
                            tint = OnPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Next Button
                    IconButton(
                        onClick = onNextClick,
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("mini_player_next")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Ďalšia skladba",
                            tint = OnSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Close / Dismiss Mini-Player Button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("mini_player_dismiss")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Zavrieť mini prehrávač",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SoundWaveBottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit
) {
    Surface(
        color = Color(0xFF111015), // 100% solid opaque - zero bleed-through!
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(64.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                label = "Domov",
                selected = currentScreen == AppScreen.HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onTabSelected(AppScreen.HOME) },
                testTag = "nav_home"
            )
            BottomNavItem(
                label = "Objavuj",
                selected = currentScreen == AppScreen.EXPLORE,
                selectedIcon = Icons.Filled.Explore,
                unselectedIcon = Icons.Outlined.Explore,
                onClick = { onTabSelected(AppScreen.EXPLORE) },
                testTag = "nav_explore"
            )
            BottomNavItem(
                label = "Knižnica",
                selected = currentScreen == AppScreen.LIBRARY,
                selectedIcon = Icons.Filled.LibraryMusic,
                unselectedIcon = Icons.Outlined.LibraryMusic,
                onClick = { onTabSelected(AppScreen.LIBRARY) },
                testTag = "nav_library"
            )
            BottomNavItem(
                label = "Kvalita",
                selected = currentScreen == AppScreen.UPGRADE_STREAMING,
                selectedIcon = Icons.Filled.WorkspacePremium,
                unselectedIcon = Icons.Outlined.WorkspacePremium,
                onClick = { onTabSelected(AppScreen.UPGRADE_STREAMING) },
                testTag = "nav_upgrade"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    selected: Boolean,
    selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    val tint = if (selected) PrimaryContainer else OnSurfaceVariant
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (selected) selectedIcon else unselectedIcon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(23.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = tint,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickScreenSwitcherModal(
    currentScreen: AppScreen,
    onSelectScreen: (AppScreen) -> Unit,
    onOpenAutoEq: () -> Unit,
    onDismiss: () -> Unit,
    onLogout: (() -> Unit)? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceContainerLow,
        contentColor = OnSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "SoundWave • Prehľad obrazoviek",
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Text(
                text = "Rýchla navigácia medzi všetkými obrazovkami",
                color = OnSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))

            val items = listOf(
                Triple(AppScreen.HOME, "1. Domov (Rýchly výber & mixy)", Icons.Default.Home),
                Triple(AppScreen.EXPLORE, "2. Objavuj (Hľadanie v Navidrome)", Icons.Default.Explore),
                Triple(AppScreen.LIBRARY, "3. Knižnica (Kolekcia & zoznamy)", Icons.Default.LibraryMusic),
                Triple(AppScreen.NOW_PLAYING, "4. Práve hrá (Texty & fronta)", Icons.Default.PlayArrow),
                Triple(AppScreen.EQUALIZER, "5. 10-Pásmový Ekvalizér & DSP", Icons.Default.Equalizer),
                Triple(AppScreen.UPGRADE_STREAMING, "6. Kvalita streamovania & ffmpeg", Icons.Default.Tune),
                Triple(AppScreen.SERVER_LOGIN, "7. Pripojenie k Navidrome (Prihlásenie)", Icons.Default.Dns)
            )

            items.forEach { (screen, label, icon) ->
                val isCurrent = currentScreen == screen
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isCurrent) PrimaryContainer.copy(alpha = 0.2f) else SurfaceContainer)
                        .clickable { onSelectScreen(screen) }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isCurrent) PrimaryContainer else SecondaryFixed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = label,
                            color = if (isCurrent) Primary else OnSurface,
                            fontSize = 14.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                    if (isCurrent) {
                        Text(
                            text = "AKTÍVNE",
                            color = PrimaryContainer,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            // Direct trigger for 10th screen: AutoEQ Modal on top of Equalizer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerHigh)
                    .clickable { onOpenAutoEq() }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "8. AutoEQ Kalibrácia slúchadiel (Modal)",
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = "OTVORIŤ",
                    color = SecondaryFixed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (onLogout != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .clickable { onLogout() }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Odhlásiť sa zo servera",
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackActionsBottomSheet(
    track: TrackItem,
    isDownloaded: Boolean,
    isDownloading: Boolean,
    isLiked: Boolean,
    onDismiss: () -> Unit,
    onPlayNow: () -> Unit,
    onPlayNext: () -> Unit,
    onAddToQueue: () -> Unit,
    onDownload: () -> Unit,
    onDeleteDownload: () -> Unit,
    onToggleLike: () -> Unit,
    onShare: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = SurfaceContainerLow,
        contentColor = OnSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header: Track preview
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainer)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
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
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${track.artist} • ${track.album.ifBlank { "Navidrome" }}",
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (track.techLine.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = track.techLine,
                            color = SecondaryFixed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Actions
            TrackActionRow(
                icon = Icons.Default.PlayArrow,
                title = "Prehrať teraz",
                subtitle = "Spustiť okamžité prehrávanie",
                iconTint = Primary,
                onClick = {
                    onPlayNow()
                    onDismiss()
                }
            )

            TrackActionRow(
                icon = Icons.Default.QueueMusic,
                title = "Prehrať ako nasledujúcu",
                subtitle = "Zaradiť hneď za práve hrajúcu skladbu",
                iconTint = PrimaryContainer,
                onClick = {
                    onPlayNext()
                    onDismiss()
                }
            )

            TrackActionRow(
                icon = Icons.Default.PlaylistAdd,
                title = "Pridať na koniec poradia",
                subtitle = "Zaradiť na koniec aktuálneho zoznamu",
                iconTint = SecondaryFixed,
                onClick = {
                    onAddToQueue()
                    onDismiss()
                }
            )

            if (isDownloaded) {
                TrackActionRow(
                    icon = Icons.Default.Delete,
                    title = "Odstrániť zo stiahnutých",
                    subtitle = "Uvoľniť miesto v pamäti telefónu",
                    iconTint = Primary,
                    onClick = {
                        onDeleteDownload()
                        onDismiss()
                    }
                )
            } else {
                TrackActionRow(
                    icon = if (isDownloading) Icons.Default.DownloadDone else Icons.Default.Download,
                    title = if (isDownloading) "Sťahuje sa do pamäte…" else "Stiahnuť na offline počúvanie",
                    subtitle = if (isDownloading) "Počkajte na stiahnutie" else "Uložiť skladbu na počúvanie bez internetu",
                    iconTint = if (isDownloading) SecondaryFixed else PrimaryContainer,
                    onClick = {
                        if (!isDownloading) {
                            onDownload()
                            onDismiss()
                        }
                    }
                )
            }

            TrackActionRow(
                icon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                title = if (isLiked) "Odstrániť z obľúbených" else "Pridať do obľúbených",
                subtitle = if (isLiked) "Odobrať hviezdičku na serveri" else "Označiť srdiečkom / hviezdičkou",
                iconTint = if (isLiked) PrimaryContainer else OnSurfaceVariant,
                onClick = {
                    onToggleLike()
                    onDismiss()
                }
            )

            TrackActionRow(
                icon = Icons.Default.Share,
                title = "Zdieľať skladbu",
                subtitle = "Odoslať názov a interpreta do inej aplikácie",
                iconTint = OnSurface,
                onClick = {
                    onShare()
                    onDismiss()
                }
            )
        }
    }
}

@Composable
private fun TrackActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainer)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = OnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = OnSurfaceVariant,
                fontSize = 11.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepTimerBottomSheet(
    currentOption: SleepTimerOption,
    remainingSeconds: Int,
    onDismiss: () -> Unit,
    onSelectOption: (SleepTimerOption) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = SurfaceContainerLow,
        contentColor = OnSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bedtime,
                        contentDescription = null,
                        tint = PrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = "Časovač spánku",
                        color = OnSurface,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Automatické pozastavenie po vybranom čase",
                        color = OnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }

            if (remainingSeconds > 0) {
                val mins = remainingSeconds / 60
                val secs = remainingSeconds % 60
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimaryContainer.copy(alpha = 0.15f))
                        .border(1.dp, PrimaryContainer.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Aktívny odpočet:",
                        color = OnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = String.format(java.util.Locale.US, "%02d:%02d", mins, secs),
                        color = PrimaryContainer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            SleepTimerOption.values().forEach { option ->
                val isSelected = currentOption == option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) PrimaryContainer.copy(alpha = 0.18f) else SurfaceContainer)
                        .clickable { onSelectOption(option) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = option.label,
                        color = if (isSelected) Primary else OnSurface,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Zvolené",
                            tint = PrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

