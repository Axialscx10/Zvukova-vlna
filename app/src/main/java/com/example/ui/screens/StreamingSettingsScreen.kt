package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SoundWaveUiState
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.PlusJakartaSans
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.PrimaryFixedDim
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
import java.util.Locale

@Composable
fun StreamingSettingsScreen(
    uiState: SoundWaveUiState,
    onOpenServerLogin: () -> Unit,
    onToggleCellularTranscoding: () -> Unit,
    onSelectOpusBitrate: (String) -> Unit,
    onSelectBackendCodec: (String) -> Unit,
    onCycleSampleRate: () -> Unit,
    onToggleHardwareAccel: () -> Unit,
    onToggleBitPerfect: () -> Unit,
    onToggleReplayGain: () -> Unit,
    onSelectReplayGainMode: (String) -> Unit,
    onReplayGainPreampChange: (Float) -> Unit,
    onToggleGapless: () -> Unit,
    onCrossfadeChange: (Int) -> Unit,
    onClearCache: () -> Unit,
    onRunDiagnostics: () -> Unit,
    onClearDownloads: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("streaming_settings_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 160.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Server Connection Status Ribbon (Click to open Server Login)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLow)
                    .clickable { onOpenServerLogin() }
                    .padding(16.dp)
                    .testTag("open_server_login_ribbon"),
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
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Dns,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(3.dp)
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Secondary)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (uiState.isLoggedIn) uiState.serverHost else "Nepripojené",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHighest)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = when {
                                        !uiState.isLoggedIn -> "PRIHLÁSIŤ"
                                        uiState.pingMs >= 0 -> "ONLINE"
                                        else -> "NEDOSTUPNÝ"
                                    },
                                    color = Secondary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = uiState.serverInfoText.ifBlank { "Klikni a pripoj sa k Navidrome serveru" },
                            color = Outline,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = SecondaryFixed,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (uiState.pingMs >= 0) "${uiState.pingMs} ms" else "— ms",
                            color = SecondaryFixed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = if (uiState.serverUrl.startsWith("https://")) "HTTPS" else "HTTP",
                        color = Outline,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Section 1: Kvalita streamovania
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Equalizer,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Kvalita streamovania",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "WI-FI & LTE",
                        color = Outline,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )
                }

                // Wi-Fi Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Wifi,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Streamovanie cez Wi-Fi",
                                    color = OnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Bez obmedzenia dátovej spotreby",
                                    color = Outline,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(PrimaryContainer)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Direct Play",
                                color = OnPrimaryContainer,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(18.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Pôvodný FLAC / ALAC štúdiový formát",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "24-bit / 192 kHz",
                                    color = Secondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pôvodný súbor bez rekompresie • Priemerný bitrate ~1 411 - 4 608 kbps",
                                color = Outline,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                // Cellular Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SignalCellularAlt,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Mobilné dáta",
                                    color = OnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Adaptívne serverové transkódovanie",
                                    color = Outline,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Switch(
                            checked = uiState.cellularTranscodingEnabled,
                            onCheckedChange = { onToggleCellularTranscoding() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = PrimaryContainer,
                                uncheckedThumbColor = OnSurfaceVariant,
                                uncheckedTrackColor = SurfaceContainerHighest
                            )
                        )
                    }

                    // Bitrate Segmented Control
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Cieľový dátový tok (Opus VBR)",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (uiState.opusBitrateTarget == "Auto") "Auto VBR" else "${uiState.opusBitrateTarget.replace("k", "")} kbps",
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("Auto", "128k", "256k", "320k").forEach { rate ->
                                val selected = uiState.opusBitrateTarget == rate
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (selected) SurfaceBright else Color.Transparent)
                                        .clickable { onSelectOpusBitrate(rate) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = rate,
                                        color = if (selected) OnSurface else Outline,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }

                    // Data Savings Chip
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHigh.copy(alpha = 0.65f))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DataSaverOn,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                    append("Približne ")
                                }
                                withStyle(SpanStyle(color = Secondary, fontWeight = FontWeight.Bold)) {
                                    append("~72% úspora dát")
                                }
                                withStyle(SpanStyle(color = OnSurfaceVariant)) {
                                    append(" voči bezstratovému FLAC pri zachovaní transparentnej štúdiovej akustiky.")
                                }
                            },
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Section 2: Serverové transkódovanie ffmpeg
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Serverové transkódovanie ffmpeg",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Navidrome Daemon",
                        color = SecondaryFixed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Formát transkódovania backendu",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CodecRadioCard(
                            title = "Opus (libopus)",
                            subtitle = "Nízka latencia • Hi-Res",
                            selected = uiState.backendCodec == "Opus (libopus)",
                            onClick = { onSelectBackendCodec("Opus (libopus)") },
                            modifier = Modifier.weight(1f)
                        )
                        CodecRadioCard(
                            title = "FLAC Stream",
                            subtitle = "Direct Subsonic",
                            selected = uiState.backendCodec == "FLAC Stream",
                            onClick = { onSelectBackendCodec("FLAC Stream") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CodecRadioCard(
                            title = "MP3 (LAME)",
                            subtitle = "Univerzálna kompatibilita",
                            selected = uiState.backendCodec == "MP3 (LAME)",
                            onClick = { onSelectBackendCodec("MP3 (LAME)") },
                            modifier = Modifier.weight(1f)
                        )
                        CodecRadioCard(
                            title = "Ogg Vorbis",
                            subtitle = "Legacy fallback",
                            selected = uiState.backendCodec == "Ogg Vorbis",
                            onClick = { onSelectBackendCodec("Ogg Vorbis") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Sample Rate Ceiling
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Maximálna vzorkovacia frekvencia",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Resampling filter SoX libsoxr",
                                color = Outline,
                                fontSize = 11.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHigh)
                                .clickable { onCycleSampleRate() }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = uiState.maxSampleRate,
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Default.UnfoldMore,
                                contentDescription = null,
                                tint = Outline,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Hardware Acceleration (VAAPI / QSV)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .clickable { onToggleHardwareAccel() }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeveloperBoard,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Hardvérová akcelerácia (VAAPI / QSV)",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Nastavuje sa na serveri (Navidrome / ffmpeg) – prepínač je zatiaľ len vizuálny",
                                    color = Outline,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Switch(
                            checked = uiState.hardwareAccelVaapi,
                            onCheckedChange = { onToggleHardwareAccel() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnSecondaryContainer,
                                checkedTrackColor = SecondaryContainer,
                                uncheckedThumbColor = OnSurfaceVariant,
                                uncheckedTrackColor = SurfaceContainerHighest
                            )
                        )
                    }
                }
            }
        }

        // Section 3: Audio výstup & Audiophile
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Album,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Audio výstup & Audiophile",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Hi-Res Engine",
                        color = Primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Bit-perfect Exclusive Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Usb,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Bit-perfect Exclusive Mode",
                                        color = OnSurface,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(PrimaryContainer)
                                            .padding(horizontal = 6.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = "DAC",
                                            color = OnPrimaryContainer,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Obchádza interný systémový audio resampler a posiela čistý bitstream priamo do USB/Lighting DAC prevodníka.",
                                    color = Outline,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }

                        Switch(
                            checked = uiState.bitPerfectExclusiveMode,
                            onCheckedChange = { onToggleBitPerfect() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = PrimaryContainer,
                                uncheckedThumbColor = OnSurfaceVariant,
                                uncheckedTrackColor = SurfaceContainerHighest
                            )
                        )
                    }

                    // ReplayGain Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                Icon(
                                    imageVector = Icons.Default.Tune,
                                    contentDescription = null,
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "ReplayGain (Normalizácia)",
                                    color = OnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Switch(
                                checked = uiState.replayGainEnabled,
                                onCheckedChange = { onToggleReplayGain() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = OnPrimary,
                                    checkedTrackColor = PrimaryContainer,
                                    uncheckedThumbColor = OnSurfaceVariant,
                                    uncheckedTrackColor = SurfaceContainerHighest
                                )
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerHigh)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("Album Gain (Zachová dynamiku)", "Track Gain").forEach { mode ->
                                val selected = uiState.replayGainMode == mode
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (selected) SurfaceBright else Color.Transparent)
                                        .clickable { onSelectReplayGainMode(mode) }
                                        .padding(vertical = 8.dp, horizontal = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = mode,
                                        color = if (selected) OnSurface else Outline,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Pre-amp kompenzačný zisk",
                                color = Outline,
                                fontSize = 11.sp
                            )
                            Text(
                                text = String.format(Locale.US, "%.1f dB", uiState.replayGainPreampDb),
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Slider(
                            value = uiState.replayGainPreampDb,
                            onValueChange = onReplayGainPreampChange,
                            valueRange = -6f..6f,
                            colors = SliderDefaults.colors(
                                thumbColor = PrimaryFixed,
                                activeTrackColor = Primary,
                                inactiveTrackColor = SurfaceContainerHighest
                            ),
                            modifier = Modifier.height(26.dp)
                        )
                    }

                    // Gapless & Crossfade
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Prehrávanie bez medzier (Gapless)",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Plynulý prechod stôp pre FLAC, ALAC a Opus",
                                color = Outline,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = uiState.gaplessEnabled,
                            onCheckedChange = { onToggleGapless() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = PrimaryContainer,
                                uncheckedThumbColor = OnSurfaceVariant,
                                uncheckedTrackColor = SurfaceContainerHighest
                            )
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Prelínanie skladieb (Crossfade)",
                                color = Outline,
                                fontSize = 12.sp
                            )
                            Text(
                                text = if (uiState.crossfadeSeconds == 0) "0 s (Vypnuté)" else "${uiState.crossfadeSeconds} s",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Slider(
                            value = uiState.crossfadeSeconds.toFloat(),
                            onValueChange = { onCrossfadeChange(it.toInt()) },
                            valueRange = 0f..12f,
                            colors = SliderDefaults.colors(
                                thumbColor = SurfaceBright,
                                activeTrackColor = Primary,
                                inactiveTrackColor = SurfaceContainerHighest
                            ),
                            modifier = Modifier.height(26.dp)
                        )

                        Text(
                            text = "Odporúčané pre autentické živé a konceptuálne albumy.",
                            color = OutlineVariant,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Section 4: Vyrovnávacia pamäť & Cache
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Layers,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Vyrovnávacia pamäť & Cache",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Buffer",
                        color = Outline,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(12.dp)
                        ) {
                            Text("Pre-buffer vopred", color = Outline, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("3 skladby", color = OnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("~50 MB alokovaných", color = Secondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(12.dp)
                        ) {
                            Text("Audio streaming buffer", color = Outline, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("10 sekúnd", color = OnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("Zero-drop prenos", color = Primary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHigh)
                            .clickable { onClearCache() }
                            .padding(12.dp)
                            .testTag("clear_cache_btn"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CleaningServices,
                                contentDescription = null,
                                tint = Outline,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Vymazať dočasnú vyrovnávaciu pamäť",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = uiState.cacheSizeText,
                                    color = Outline,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Outline,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHigh)
                            .clickable { onClearDownloads() }
                            .padding(12.dp)
                            .testTag("clear_downloads_btn"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Vymazať stiahnuté offline skladby",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${uiState.downloadedTracks.size} skladieb zaberá ${uiState.downloadedSizeText}",
                                    color = Outline,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Outline,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Section 5: Diagnostic Action Button
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(14.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                        .clip(RoundedCornerShape(50))
                        .background(Primary)
                        .clickable { onRunDiagnostics() }
                        .testTag("test_diagnostics_btn"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (uiState.isTestingDiagnostics) Icons.Default.Sync else Icons.Default.NetworkCheck,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState.isTestingDiagnostics) "Pripájam ffmpeg test stream..." else "Otestovať transkódovanie a latenciu",
                        color = OnPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                AnimatedVisibility(visible = uiState.diagnosticResultVisible) {
                    Text(
                        text = uiState.diagnosticResultText,
                        color = Secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CodecRadioCard(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) SurfaceContainerHigh else SurfaceContainerLow)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = OnSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = Outline,
                fontSize = 10.sp
            )
        }
        Icon(
            imageVector = if (selected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (selected) Primary else Outline,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ServerLoginScreen(
    uiState: SoundWaveUiState,
    onServerUrlChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleTokenHint: () -> Unit,
    onToggleAdvancedAccordion: () -> Unit,
    onToggleOfflineCache: () -> Unit,
    onTogglePreferFlac: () -> Unit,
    onCycleTranscodeOption: () -> Unit,
    onConnectServer: () -> Unit,
    onFillDemoServer: () -> Unit,
    onLogout: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("server_login_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Visual Hero & Vinyl Emblem
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .shadow(20.dp, CircleShape, ambientColor = PrimaryContainer, spotColor = PrimaryContainer)
                        .clip(CircleShape)
                        .background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(66.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerLowest),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainer.copy(alpha = 0.5f))
                        )
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerLowest)
                        )
                        Icon(
                            imageVector = Icons.Default.Album,
                            contentDescription = null,
                            tint = PrimaryFixed,
                            modifier = Modifier.size(28.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Primary)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Pripojenie k Navidrome",
                    color = OnSurface,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Zadajte údaje vášho osobného hudobného servera pre streamovanie bezstratového zvuku (FLAC/MP3).",
                    color = OnSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }

        // Server Address Input Field
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "URL Servera (Server Address)",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "SUBSONIC V1.16+",
                        color = Primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Dns,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    BasicTextField(
                        value = uiState.serverUrl,
                        onValueChange = onServerUrlChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = PlusJakartaSans,
                            color = OnSurface,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(Primary),
                        decorationBox = { innerTextField ->
                            if (uiState.serverUrl.isEmpty()) {
                                Text(
                                    text = "https://vas-server.sk:4533",
                                    color = OnSurfaceVariant.copy(alpha = 0.5f),
                                    fontSize = 14.sp,
                                    fontFamily = PlusJakartaSans
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("login_server_url_input")
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = SecondaryFixedDim,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Podporuje SSL (HTTPS), Tailscale aj lokálnu sieť",
                        color = OnSurfaceVariant.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Username Input Field
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Používateľské meno",
                    color = OnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    BasicTextField(
                        value = uiState.username,
                        onValueChange = onUsernameChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = PlusJakartaSans,
                            color = OnSurface,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(Primary),
                        decorationBox = { innerTextField ->
                            if (uiState.username.isEmpty()) {
                                Text(
                                    text = "Používateľské meno",
                                    color = OnSurfaceVariant.copy(alpha = 0.5f),
                                    fontSize = 14.sp,
                                    fontFamily = PlusJakartaSans
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("login_username_input")
                    )
                }
            }
        }

        // Password / App Token Field
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heslo alebo API Token",
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Čo je App Token?",
                        color = SecondaryFixed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onToggleTokenHint() }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    BasicTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        singleLine = true,
                        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        textStyle = TextStyle(
                            fontFamily = PlusJakartaSans,
                            color = OnSurface,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(Primary),
                        decorationBox = { innerTextField ->
                            if (uiState.password.isEmpty()) {
                                Text(
                                    text = "Heslo k vášmu účtu",
                                    color = OnSurfaceVariant.copy(alpha = 0.5f),
                                    fontSize = 14.sp,
                                    fontFamily = PlusJakartaSans
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("login_password_input")
                    )
                    IconButton(
                        onClick = onTogglePasswordVisibility,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Prepnúť viditeľnosť hesla",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                AnimatedVisibility(visible = uiState.showTokenHint) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SecondaryContainer.copy(alpha = 0.12f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Zadaj heslo svojho Navidrome účtu. Aplikácia heslo neukladá – po úspešnom prihlásení si zapamätá len bezpečnostný token (md5 hash s náhodnou soľou).",
                            color = Secondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Advanced Settings Collapsible Accordion
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLow.copy(alpha = 0.75f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleAdvancedAccordion() }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Pokročilé nastavenia zvuku",
                            color = OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        imageVector = if (uiState.showAdvancedAudioAccordion) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                AnimatedVisibility(visible = uiState.showAdvancedAudioAccordion) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Offline vyrovnávacia pamäť",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Ukladať práve počúvané skladby",
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = uiState.loginOfflineCacheEnabled,
                                onCheckedChange = { onToggleOfflineCache() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = OnPrimaryContainer,
                                    checkedTrackColor = PrimaryContainer
                                )
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Preferovať bezstratové FLAC",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Streamovať 24-bit/96kHz bez kompresie",
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = uiState.loginPreferFlacEnabled,
                                onCheckedChange = { onTogglePreferFlac() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = OnPrimaryContainer,
                                    checkedTrackColor = PrimaryContainer
                                )
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Maximálny dátový tok (Transcoding)",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SurfaceContainer)
                                    .clickable { onCycleTranscodeOption() }
                                    .padding(horizontal = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = uiState.loginTranscodeOption,
                                    color = OnSurface,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.UnfoldMore,
                                    contentDescription = null,
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Primary Connect Button + Demo Server Link
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val btnBg = if (uiState.isServerConnectedSuccess) SecondaryContainer else PrimaryContainer
                val btnFg = if (uiState.isServerConnectedSuccess) OnSecondaryContainer else OnPrimaryContainer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(18.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                        .clip(RoundedCornerShape(50))
                        .background(btnBg)
                        .clickable { onConnectServer() }
                        .testTag("login_connect_btn"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (uiState.isServerConnectedSuccess) Icons.Default.CheckCircle else Icons.Default.Bolt,
                        contentDescription = null,
                        tint = btnFg,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            uiState.isConnectingServer -> "Pripájam..."
                            uiState.isServerConnectedSuccess -> "Pripojené úspešne!"
                            else -> "Pripojiť k serveru"
                        },
                        color = btnFg,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Vyskúšať demo server",
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onFillDemoServer() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("login_demo_server_btn")
                    )
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHighest)
                    )
                    Text(
                        text = "Pomocník",
                        color = OnSurfaceVariant,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onToggleTokenHint() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                if (uiState.connectionError != null) {
                    Text(
                        text = uiState.connectionError ?: "",
                        color = PrimaryFixedDim,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_error_text")
                    )
                }

                // Live connection status pill
                AnimatedVisibility(visible = uiState.isConnectingServer || uiState.isServerConnectedSuccess) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceContainer)
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isServerConnectedSuccess) Icons.Default.CloudDone else Icons.Default.Sync,
                            contentDescription = null,
                            tint = SecondaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = if (uiState.isServerConnectedSuccess) {
                                    "Prihlásenie overené"
                                } else {
                                    "Overujem prihlásenie na serveri..."
                                },
                                color = OnSurface,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (uiState.isServerConnectedSuccess) {
                                    buildString {
                                        if (uiState.pingMs >= 0) append("Ping: ${uiState.pingMs} ms")
                                        if (uiState.serverInfoText.isNotBlank()) {
                                            if (isNotEmpty()) append(" • ")
                                            append(uiState.serverInfoText)
                                        }
                                    }
                                } else {
                                    "Čakám na odpoveď servera…"
                                },
                                color = OnSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Security End-to-End Footnote
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.65f))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = PrimaryFixedDim,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(18.dp)
                )
                Text(
                    text = "Heslo sa neukladá. Aplikácia si zapamätá len prihlasovací token (md5 hash s náhodnou soľou) v súkromnom úložisku aplikácie, ktoré je vylúčené zo zálohovania. Komunikuje výhradne s adresou tvojho servera. Poznámka: http:// spojenie nie je šifrované – v domácej sieti alebo cez Tailscale je to bežné, mimo nej používaj https://.",
                    color = OnSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        if (uiState.isLoggedIn) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh)
                        .clickable { onLogout() }
                        .testTag("login_logout_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Odhlásiť sa a zabudnúť server",
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
