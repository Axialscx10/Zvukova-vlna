package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HdrStrong
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SpatialAudio
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.SsidChart
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HeadphoneProfile
import com.example.data.SoundWaveCatalog
import com.example.ui.SoundWaveUiState
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.PlusJakartaSans
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixedDim
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun EqualizerScreen(
    uiState: SoundWaveUiState,
    onPreampChange: (Float) -> Unit,
    onBandChange: (Int, Float) -> Unit,
    onResetFlat: () -> Unit,
    onInvertBands: () -> Unit,
    onSelectPreset: (String) -> Unit,
    onOpenAutoEqModal: () -> Unit,
    onCloseAutoEqModal: () -> Unit,
    onSubBassBoostChange: (Float) -> Unit,
    onToggleCrossfeed: (Boolean) -> Unit,
    onToggleAutoGain: (Boolean) -> Unit,
    onAutoEqSearchChange: (String) -> Unit,
    onAutoEqTargetCurveSelect: (String) -> Unit,
    onSelectHeadphoneProfile: (HeadphoneProfile) -> Unit,
    onCalibrationDryWetChange: (Float) -> Unit,
    onToggleAutoPreamp: (Boolean) -> Unit,
    onToggleSubBassCompensation: (Boolean) -> Unit,
    onApplyAutoEqToDsp: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("equalizer_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Master DSP Procesor Card
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
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
                                    .size(10.dp)
                                    .shadow(6.dp, CircleShape, spotColor = Primary)
                                    .clip(CircleShape)
                                    .background(Primary)
                            )
                            Column {
                                Text(
                                    text = "Master DSP Procesor",
                                    color = OnSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Priame hardvérové spracovanie zvuku",
                                    color = OnSurfaceVariant,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (uiState.eqMasterEnabled) "AKTÍVNY" else "BYPASS",
                                color = Secondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }

                    // Tech Badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Memory,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "32-bit Float Processing",
                                color = OnSurface,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Bit-perfect DAC Bypass",
                                color = OnSurface,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Master Pre-amp
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .padding(12.dp)
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
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Hlavný predzosilňovač (Pre-amp)",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            val preampFormatted = String.format(
                                Locale.US,
                                "%s%.1f dB",
                                if (uiState.preampDb > 0f) "+" else "",
                                uiState.preampDb
                            )
                            Text(
                                text = preampFormatted,
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Slider(
                            value = uiState.preampDb,
                            onValueChange = onPreampChange,
                            valueRange = -12f..12f,
                            colors = SliderDefaults.colors(
                                thumbColor = Primary,
                                activeTrackColor = Primary,
                                inactiveTrackColor = SurfaceContainerHighest
                            ),
                            modifier = Modifier.height(32.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("-12 dB", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("0 dB (Referencia)", color = OnSurfaceVariant.copy(alpha = 0.6f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("+12 dB", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 2. Interactive Dynamic Frequency Curve Display
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
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
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SsidChart,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Frekvenčná odozva",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Secondary)
                            )
                            Text(
                                text = "Akustická krivka v reálnom čase",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(144.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLowest)
                            .padding(8.dp)
                    ) {
                        FrequencyResponseCurveCanvas(
                            bands = uiState.eqBands,
                            modifier = Modifier.fillMaxSize()
                        )

                        // dB Axis Markers
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("+12 dB", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("+6 dB", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("0 dB", color = Primary, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                            Text("-6 dB", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("-12 dB", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }

                        // Frequency Axis Markers
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(start = 32.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            listOf("20 Hz", "100 Hz", "1 kHz", "10 kHz", "20 kHz").forEach { freq ->
                                Text(
                                    text = freq,
                                    color = OnSurfaceVariant.copy(alpha = 0.7f),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 3. 10-Band Graphic Equalizer Faders
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "10-Pásmový Ekvalizér",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Presné analógové tvarovanie spektra",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHigh)
                                    .clickable { onResetFlat() }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                    .testTag("eq_flat_reset_btn"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HorizontalRule,
                                    contentDescription = null,
                                    tint = OnSurface,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Plochá (Flat)",
                                    color = OnSurface,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHigh)
                                    .clickable { onInvertBands() }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                    .testTag("eq_invert_btn"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapVert,
                                    contentDescription = null,
                                    tint = OnSurface,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Invertovať",
                                    color = OnSurface,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // 10 Vertical Sliders Shelf
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SoundWaveCatalog.eqBands.forEach { bandInfo ->
                            val value = uiState.eqBands.getOrElse(bandInfo.index) { 0f }
                            VerticalEqBandFader(
                                freqLabel = bandInfo.freqLabel,
                                subLabel = bandInfo.subLabel,
                                valueDb = value,
                                onValueChange = { newDb -> onBandChange(bandInfo.index, newDb) }
                            )
                        }
                    }
                }
            }

            // 4. Presets (Zvukové predvoľby)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                tint = Secondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Zvukové predvoľby (Presets)",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Všetky",
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val presetIcons = mapOf(
                            "Synthwave / Retrowave" to Icons.Default.GraphicEq,
                            "Elektronika & Bass Boost" to Icons.Default.ElectricBolt,
                            "Audiofil Flat (Referenčný)" to Icons.Default.Straighten,
                            "Vokály & Akustika" to Icons.Default.Mic,
                            "Nočné počúvanie (Loudness)" to Icons.Default.Nightlight
                        )

                        SoundWaveCatalog.eqPresets.keys.forEach { presetName ->
                            val isSelected = uiState.activePresetName == presetName
                            Row(
                                modifier = Modifier
                                    .then(
                                        if (isSelected) {
                                            Modifier.shadow(10.dp, RoundedCornerShape(50), spotColor = PrimaryContainer)
                                        } else Modifier
                                    )
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isSelected) Primary else SurfaceContainerHigh)
                                    .clickable { onSelectPreset(presetName) }
                                    .padding(horizontal = 14.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = presetIcons[presetName] ?: Icons.Default.GraphicEq,
                                    contentDescription = null,
                                    tint = if (isSelected) OnPrimary else OnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = presetName,
                                    color = if (isSelected) OnPrimary else OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainer)
                                .clickable { onOpenAutoEqModal() }
                                .padding(horizontal = 14.dp, vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Nový profil",
                                color = Secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // 5. Pokročilé vylepšenia prednesu
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SpatialAudio,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Pokročilé vylepšenia prednesu",
                            color = OnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Headphone AutoEq Profile Selector -> Opens AutoEQ Calibration Modal!
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .clickable { onOpenAutoEqModal() }
                            .padding(12.dp)
                            .testTag("open_autoeq_modal_card"),
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
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = uiState.selectedHeadphone.name,
                                    color = OnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "AutoEq Harman Target V2 • Kalibrované",
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Zmeniť AutoEQ slúchadlá",
                            tint = Secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Sub-bass Zosilňovač (Bass Boost)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .padding(12.dp)
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
                                    imageVector = Icons.Default.Speaker,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Sub-bass Zosilňovač (Bass Boost)",
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
                                    text = String.format(Locale.US, "+%.1f dB @ 55 Hz", uiState.subBassBoostDb),
                                    color = Secondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Slider(
                            value = uiState.subBassBoostDb,
                            onValueChange = onSubBassBoostChange,
                            valueRange = 0f..10f,
                            colors = SliderDefaults.colors(
                                thumbColor = Primary,
                                activeTrackColor = Primary,
                                inactiveTrackColor = SurfaceContainerHighest
                            ),
                            modifier = Modifier.height(32.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Vypnuté", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("Rezaná frekvencia: 55 Hz strmosť 24dB/oct", color = OnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("+10 dB", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Crossfeed Toggle
                    EnhancementToggleCard(
                        icon = Icons.Default.SurroundSound,
                        iconTint = Secondary,
                        title = "Crossfeed priestorový filter",
                        subtitle = "Znižuje únavu uší pri dlhom posluchu cez slúchadlá",
                        checked = uiState.crossfeedEnabled,
                        onCheckedChange = onToggleCrossfeed
                    )

                    // Auto-Gain Toggle
                    EnhancementToggleCard(
                        icon = Icons.Default.Shield,
                        iconTint = Primary,
                        title = "Automatická kompenzácia zisku (Auto-Gain)",
                        subtitle = "Aktívna ochrana proti inter-sample digitálnemu orezaniu (clipping)",
                        checked = uiState.autoGainEnabled,
                        onCheckedChange = onToggleAutoGain
                    )
                }
            }
        }

        // AutoEQ Calibration Bottom Sheet Overlay (Image_15 / Image_16)
        if (uiState.showAutoEqModal) {
            AutoEqCalibrationModalOverlay(
                uiState = uiState,
                onSearchChange = onAutoEqSearchChange,
                onTargetCurveSelect = onAutoEqTargetCurveSelect,
                onSelectProfile = onSelectHeadphoneProfile,
                onDryWetChange = onCalibrationDryWetChange,
                onToggleAutoPreamp = onToggleAutoPreamp,
                onToggleSubBassComp = onToggleSubBassCompensation,
                onApplyToDsp = onApplyAutoEqToDsp,
                onClose = onCloseAutoEqModal
            )
        }
    }
}

@Composable
private fun FrequencyResponseCurveCanvas(
    bands: List<Float>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val centerY = h / 2f

        // Subtle Grid Lines
        val rows = 4
        val cols = 9
        for (r in 1 until rows) {
            val y = (h / rows) * r
            drawLine(
                color = OnSurface.copy(alpha = 0.08f),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
            )
        }
        for (c in 1..cols) {
            val x = (w / (cols + 1)) * c
            drawLine(
                color = OnSurface.copy(alpha = 0.08f),
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
            )
        }

        // 0 dB Center Line
        drawLine(
            color = Primary.copy(alpha = 0.35f),
            start = Offset(0f, centerY),
            end = Offset(w, centerY),
            strokeWidth = 1.5f
        )

        if (bands.size < 2) return@Canvas

        val padX = w * 0.06f
        val usableW = w - 2 * padX
        val maxAmplitudeY = h * 0.38f

        val points = bands.mapIndexed { idx, db ->
            val x = padX + (idx.toFloat() / (bands.size - 1)) * usableW
            val y = centerY - (db.coerceIn(-12f, 12f) / 12f) * maxAmplitudeY
            Offset(x, y)
        }

        val strokePath = Path().apply {
            moveTo(0f, points.first().y)
            lineTo(points.first().x, points.first().y)
            for (i in 0 until points.size - 1) {
                val p0 = points[i]
                val p1 = points[i + 1]
                val mx = (p0.x + p1.x) / 2f
                cubicTo(mx, p0.y, mx, p1.y, p1.x, p1.y)
            }
            lineTo(w, points.last().y)
        }

        val fillPath = Path().apply {
            addPath(strokePath)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    PrimaryContainer.copy(alpha = 0.35f),
                    Primary.copy(alpha = 0.12f),
                    Color.Transparent
                )
            )
        )

        drawPath(
            path = strokePath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    SecondaryContainer,
                    PrimaryContainer,
                    Primary,
                    Tertiary
                )
            ),
            style = Stroke(width = 6.5f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun VerticalEqBandFader(
    freqLabel: String,
    subLabel: String,
    valueDb: Float,
    onValueChange: (Float) -> Unit
) {
    val formattedVal = String.format(
        Locale.US,
        "%s%.1f",
        if (valueDb > 0.05f) "+" else "",
        valueDb
    )
    val valColor = when {
        valueDb > 0.05f -> Primary
        valueDb < -0.05f -> SecondaryFixedDim
        else -> OnSurfaceVariant
    }

    Column(
        modifier = Modifier.width(46.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = formattedVal,
            color = valColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        // Vertical Track Container
        Box(
            modifier = Modifier
                .width(34.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(50))
                .background(SurfaceContainer)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val ratio = ((offset.y / size.height) - 0.5f) / -0.38f
                        val newDb = (ratio * 12f).coerceIn(-12f, 12f)
                        onValueChange(newDb)
                    }
                }
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, _ ->
                        change.consume()
                        val ratio = ((change.position.y / size.height) - 0.5f) / -0.38f
                        val newDb = (ratio * 12f).coerceIn(-12f, 12f)
                        onValueChange(newDb)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            // Inner Rail
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SurfaceContainerHighest)
            )
            // 0dB Center Notch
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(OutlineVariant.copy(alpha = 0.6f))
            )

            // Thumb Knob Position (-12..+12 mapped to vertical offset)
            val normalized = (valueDb.coerceIn(-12f, 12f) / 12f)
            val yOffsetDp = (-normalized * 54f).dp
            val thumbBg = when {
                valueDb > 0.05f -> Primary
                valueDb < -0.05f -> Secondary
                else -> SurfaceBright
            }
            val dotColor = when {
                valueDb > 0.05f -> OnPrimary
                valueDb < -0.05f -> OnSecondary
                else -> OnSurface
            }

            Box(
                modifier = Modifier
                    .offset(y = yOffsetDp)
                    .size(24.dp)
                    .shadow(6.dp, CircleShape)
                    .clip(CircleShape)
                    .background(thumbBg),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = freqLabel,
                color = OnSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subLabel,
                color = OnSurfaceVariant,
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun EnhancementToggleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainer)
            .clickable { onCheckedChange(!checked) }
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = OnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = OnSurfaceVariant,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = OnSurfaceVariant,
                uncheckedTrackColor = SurfaceContainerHighest
            )
        )
    }
}

@Composable
private fun AutoEqCalibrationModalOverlay(
    uiState: SoundWaveUiState,
    onSearchChange: (String) -> Unit,
    onTargetCurveSelect: (String) -> Unit,
    onSelectProfile: (HeadphoneProfile) -> Unit,
    onDryWetChange: (Float) -> Unit,
    onToggleAutoPreamp: (Boolean) -> Unit,
    onToggleSubBassComp: (Boolean) -> Unit,
    onApplyToDsp: () -> Unit,
    onClose: () -> Unit
) {
    val targetCurves = listOf(
        "Harman Target 2019v2",
        "Harman In-Ear 2019",
        "Diffuse Field (DF)",
        "Free Field",
        "Rtings Neutral"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceContainerLowest.copy(alpha = 0.85f))
            .clickable(enabled = true, onClick = {})
            .testTag("autoeq_modal_overlay")
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(SurfaceContainerLow),
            contentPadding = PaddingValues(bottom = 140.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Modal Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHighest)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Tune,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "AutoEQ Kalibrácia slúchadiel",
                                    color = OnSurface,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Výber profilu z databázy Jaakkopasanen AutoEQ (vyše 4 000 modelov)",
                                color = OnSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh)
                                .clickable { onClose() }
                                .testTag("autoeq_close_btn"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Zavrieť",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // 2. Search bar + Target Curve Filter Chips
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerHighest)
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        BasicTextField(
                            value = uiState.autoEqSearchQuery,
                            onValueChange = onSearchChange,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = PlusJakartaSans,
                                color = OnSurface,
                                fontSize = 14.sp
                            ),
                            cursorBrush = SolidColor(Primary),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh)
                                .clickable { onSearchChange("") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        targetCurves.forEach { curve ->
                            val isSelected = uiState.autoEqTargetCurve == curve
                            Row(
                                modifier = Modifier
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isSelected) Primary else SurfaceContainerHigh)
                                    .clickable { onTargetCurveSelect(curve) }
                                    .padding(horizontal = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = OnPrimary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                                Text(
                                    text = curve,
                                    color = if (isSelected) OnPrimary else OnSurfaceVariant,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // 3. Active Selected Headphone Card with Oratory1990 Compensation Curve
            item {
                val active = uiState.selectedHeadphone
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceContainer)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .shadow(10.dp, RoundedCornerShape(12.dp), spotColor = PrimaryContainer)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(PrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headphones,
                                        contentDescription = null,
                                        tint = OnPrimaryContainer,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = active.name,
                                            color = OnSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(50))
                                                .background(Primary.copy(alpha = 0.2f))
                                                .padding(horizontal = 8.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .clip(CircleShape)
                                                    .background(Primary)
                                            )
                                            Text(
                                                text = "AKTÍVNY PROFIL",
                                                color = Primary,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = active.targetInfo,
                                        color = OnSurfaceVariant,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // Mini Compensation Graph
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLowest)
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("20 Hz", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "Oratory1990 Korekčná Krivka (+4.2dB / -5.1dB)",
                                    color = Primary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("20 kHz", color = OnSurfaceVariant.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ) {
                                val w = size.width
                                val h = size.height
                                drawLine(
                                    color = Primary.copy(alpha = 0.25f),
                                    start = Offset(0f, h * 0.5f),
                                    end = Offset(w, h * 0.5f),
                                    strokeWidth = 1.5f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                                )

                                val curvePath = Path().apply {
                                    moveTo(0f, h * 0.75f)
                                    cubicTo(w * 0.1f, h * 0.72f, w * 0.14f, h * 0.42f, w * 0.22f, h * 0.36f)
                                    cubicTo(w * 0.32f, h * 0.28f, w * 0.4f, h * 0.52f, w * 0.5f, h * 0.55f)
                                    cubicTo(w * 0.6f, h * 0.58f, w * 0.65f, h * 0.42f, w * 0.72f, h * 0.36f)
                                    cubicTo(w * 0.82f, h * 0.30f, w * 0.9f, h * 0.6f, w, h * 0.64f)
                                }
                                val areaPath = Path().apply {
                                    addPath(curvePath)
                                    lineTo(w, h)
                                    lineTo(0f, h)
                                    close()
                                }
                                drawPath(areaPath, color = Primary.copy(alpha = 0.14f))
                                drawPath(curvePath, color = Primary, style = Stroke(width = 5f, cap = StrokeCap.Round))
                                drawCircle(color = Primary, radius = 6f, center = Offset(w * 0.22f, h * 0.36f))
                                drawCircle(color = Primary, radius = 6f, center = Offset(w * 0.72f, h * 0.36f))
                            }
                        }

                        // 3 Stat Boxes
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatBox("Pre-amp zisk", active.preampDb, Primary, Modifier.weight(1f))
                            StatBox("Filtre", active.filtersSpec, OnSurface, Modifier.weight(1f))
                            StatBox("Impedancia", active.impedanceSpec, OnSurface, Modifier.weight(1f))
                        }
                    }

                    // Other Headphone Profiles
                    SoundWaveCatalog.headphoneProfiles.filter { it.id != active.id }.forEach { profile ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainer)
                                .clickable { onSelectProfile(profile) }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceContainerHighest),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headphones,
                                        contentDescription = null,
                                        tint = OnSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = profile.name,
                                        color = OnSurface,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = profile.targetInfo,
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Pre-amp ${profile.preampDb}  •  ${profile.filtersSpec}  •  ${profile.impedanceSpec}",
                                        color = Primary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHighest)
                                    .clickable { onSelectProfile(profile) }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Zvoliť",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // 4. Calibration Intensity (Dry/Wet) & Switches
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
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.HdrStrong,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Intenzita kalibrácie (Dry / Wet)",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Primary.copy(alpha = 0.14f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${uiState.calibrationDryWetPct.roundToInt()}% (Plná kompenzácia)",
                                color = Primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Slider(
                        value = uiState.calibrationDryWetPct,
                        onValueChange = onDryWetChange,
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = OnSurface,
                            activeTrackColor = Primary,
                            inactiveTrackColor = SurfaceContainerHighest
                        ),
                        modifier = Modifier.height(28.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("0% (Originálny zvuk)", color = OnSurfaceVariant.copy(alpha = 0.6f), fontSize = 10.sp)
                        Text("50%", color = OnSurfaceVariant.copy(alpha = 0.6f), fontSize = 10.sp)
                        Text("100% (AutoEQ Cieľ)", color = OnSurfaceVariant.copy(alpha = 0.6f), fontSize = 10.sp)
                    }

                    // Auto Pre-amp Toggle
                    AutoEqToggleRow(
                        title = "Automatický Pre-amp zisk (${uiState.selectedHeadphone.preampDb})",
                        subtitle = "Digitálna ochrana pred clippingom a harmonickým skreslením",
                        checked = uiState.autoPreampEnabled,
                        onCheckedChange = onToggleAutoPreamp
                    )

                    // Sub-bass boost Toggle
                    AutoEqToggleRow(
                        title = "Korekcia basového poklesu (Sub-bass boost)",
                        subtitle = "Harman low-shelf posilnenie pásma pod 80 Hz",
                        checked = uiState.subBassCompensationEnabled,
                        onCheckedChange = onToggleSubBassComp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Apply Profile Button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(12.dp, RoundedCornerShape(50), spotColor = Primary)
                            .clip(RoundedCornerShape(50))
                            .background(Primary)
                            .clickable { onApplyToDsp() }
                            .testTag("autoeq_apply_btn"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Aplikovať profil na DSP",
                            color = OnPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .clickable { onApplyToDsp() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FileUpload,
                                contentDescription = null,
                                tint = OnSurface,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Importovať (.csv)",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SurfaceContainerHigh)
                                .clickable { onClose() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Zrušiť",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBox(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerHigh)
            .padding(vertical = 8.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = OnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            color = valueColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AutoEqToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainer)
            .clickable { onCheckedChange(!checked) }
            .padding(12.dp),
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
                color = OnSurfaceVariant,
                fontSize = 11.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = OnSurfaceVariant,
                uncheckedTrackColor = SurfaceContainerHighest
            )
        )
    }
}
