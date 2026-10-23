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
import com.example.data.SoundWaveCatalog
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
    if (useGraphicEqIcon) {
        Box(
            modifier = Modifier
                .size(size)
                .shadow(12.dp, CircleShape, ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                .clip(CircleShape)
                .background(PrimaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = "SoundWave",
                tint = OnPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFF1A0507))
                .border(2.dp, Color(0xFFFF1E27), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size * 0.62f)
                    .clip(CircleShape)
                    .background(Color(0xFFFF1E27)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "SoundWave Logo",
                    tint = Color.White,
                    modifier = Modifier.size(size * 0.42f)
                )
            }
        }
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
                            contentDescription = "Search",
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
                    if (useGraphicEqLogo) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil / Obrazovky",
                            tint = OnPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        NetImage(
                            url = SoundWaveCatalog.PROFILE_AVATAR_URL,
                            contentDescription = "Profil / Obrazovky",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
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
                        NetImage(
                            url = SoundWaveCatalog.PROFILE_AVATAR_URL,
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize()
                        )
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
    subtitleVariant: String = "Mr.Kitty • Navidrome",
    badgeText: String = "FLAC",
    showSkipPrevious: Boolean = false,
    showCastButton: Boolean = false,
    showAirPodsSubtitle: Boolean = false,
    onDockClick: () -> Unit,
    onLikeClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit = {},
    onCastClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .shadow(24.dp, RoundedCornerShape(16.dp), spotColor = Color.Black)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xEB1C1B1B))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .clickable { onDockClick() }
            .testTag("mini_player_dock")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top Scrubbing Line (unless bottom style for Home)
            if (!showAirPodsSubtitle) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.5.dp)
                        .background(SurfaceContainerHighest)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.coerceIn(0.05f, 1f))
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Primary, PrimaryContainer)
                                )
                            )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHighest)
                    ) {
                        NetImage(
                            url = track.imageUrl,
                            contentDescription = track.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (badgeText == "24-BIT") {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(PrimaryContainer.copy(alpha = 0.9f))
                                    .padding(vertical = 1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "24-BIT",
                                    color = OnPrimaryContainer,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = track.title,
                                color = OnSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (!showAirPodsSubtitle) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SecondaryFixed.copy(alpha = 0.18f))
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = badgeText,
                                        color = SecondaryFixed,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        if (showAirPodsSubtitle) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = track.artist,
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                                Text("•", color = OnSurfaceVariant.copy(alpha = 0.5f), fontSize = 10.sp)
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "AirPods Pro",
                                    color = Secondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1
                                )
                            }
                        } else {
                            Text(
                                text = subtitleVariant,
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
                    if (showSkipPrevious) {
                        IconButton(
                            onClick = onPreviousClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = "Predchádzajúca",
                                tint = OnSurface,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else if (showCastButton) {
                        IconButton(
                            onClick = onCastClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cast,
                                contentDescription = "Zariadenia",
                                tint = SecondaryFixed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = onLikeClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Obľúbené",
                                tint = if (isLiked) PrimaryContainer else OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Crimson / Primary Play/Pause Button
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .shadow(10.dp, CircleShape, ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                            .clip(CircleShape)
                            .background(if (showSkipPrevious) Primary else PrimaryContainer)
                            .clickable { onPlayPauseClick() }
                            .testTag("mini_player_play_pause"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = if (showSkipPrevious) OnPrimary else OnPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = onNextClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("mini_player_next")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Ďalšia skladba",
                            tint = OnSurface,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            if (showAirPodsSubtitle) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(SurfaceContainerLow)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.coerceIn(0.05f, 1f))
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Primary, PrimaryContainer)
                                )
                            )
                    )
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
        color = Surface.copy(alpha = 0.94f),
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
                label = "Home",
                selected = currentScreen == AppScreen.HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onTabSelected(AppScreen.HOME) },
                testTag = "nav_home"
            )
            BottomNavItem(
                label = "Explore",
                selected = currentScreen == AppScreen.EXPLORE,
                selectedIcon = Icons.Filled.Explore,
                unselectedIcon = Icons.Outlined.Explore,
                onClick = { onTabSelected(AppScreen.EXPLORE) },
                testTag = "nav_explore"
            )
            BottomNavItem(
                label = "Library",
                selected = currentScreen == AppScreen.LIBRARY,
                selectedIcon = Icons.Filled.LibraryMusic,
                unselectedIcon = Icons.Outlined.LibraryMusic,
                onClick = { onTabSelected(AppScreen.LIBRARY) },
                testTag = "nav_library"
            )
            BottomNavItem(
                label = "Upgrade",
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
    onDismiss: () -> Unit
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
                Triple(AppScreen.HOME, "1. Home (Quick Picks & Mixes)", Icons.Default.Home),
                Triple(AppScreen.EXPLORE, "2. Explore (Navidrome Search & Filters)", Icons.Default.Explore),
                Triple(AppScreen.LIBRARY, "3. Library (Kolekcia & Zoznamy)", Icons.Default.LibraryMusic),
                Triple(AppScreen.ARTIST_DETAIL, "4. Detail Interpreta (The Midnight)", Icons.Default.Star),
                Triple(AppScreen.PLAYLIST_DETAIL, "5. Detail Playlistu (Synthwave Nights)", Icons.Default.Album),
                Triple(AppScreen.NOW_PLAYING, "6. Now Playing (Waveform & Lyrics)", Icons.Default.PlayArrow),
                Triple(AppScreen.EQUALIZER, "7. 10-Pásmový Ekvalizér & DSP", Icons.Default.Equalizer),
                Triple(AppScreen.UPGRADE_STREAMING, "8. Kvalita streamovania & ffmpeg", Icons.Default.Tune),
                Triple(AppScreen.SERVER_LOGIN, "9. Pripojenie k Navidrome (Server Login)", Icons.Default.Dns)
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
                        text = "10. AutoEQ Kalibrácia slúchadiel (Modal)",
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
        }
    }
}
