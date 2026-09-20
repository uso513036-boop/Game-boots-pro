package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GameItem
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberYellow
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonOrange
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GameItemCard(
    game: GameItem,
    onLaunchTurbo: () -> Unit,
    onToggleFavorite: () -> Unit,
    onUpdateSettings: (String, Int) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfigDialog by remember { mutableStateOf(false) }

    val modeColor = when (game.performanceMode) {
        "ULTRA" -> NeonOrange
        "BALANCED" -> NeonCyan
        else -> CyberGreen
    }

    val modeLabel = when (game.performanceMode) {
        "ULTRA" -> "🚀 Ultra FPS (Max)"
        "BALANCED" -> "⚖️ Equilibrado"
        else -> "🔋 Batería Fría"
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkCard)
            .border(1.dp, if (game.packageName == "com.roblox.client") NeonCyan.copy(alpha = 0.5f) else DarkBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("game_card_${game.packageName}")
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Game Icon Box
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    if (game.packageName == "com.roblox.client") Color(0xFFE11D48) else Color(0xFF0284C7),
                                    Color(0xFF0F172A)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = game.appName,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title & Subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = game.appName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        if (game.packageName == "com.roblox.client") {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(NeonCyan.copy(alpha = 0.15f))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "DESTACADO",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonCyan
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = modeLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = modeColor
                        )
                        Text(
                            text = "• ${game.targetFps} FPS",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                        if (game.launchCount > 0) {
                            Text(
                                text = "• ${game.launchCount} partidas",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }

                // Favorite button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (game.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorito",
                        tint = if (game.isFavorite) CyberYellow else TextMuted
                    )
                }

                // Settings button
                IconButton(
                    onClick = { showConfigDialog = true },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configurar",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom action row: Launch with Turbo
            Button(
                onClick = onLaunchTurbo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("launch_turbo_${game.packageName}"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonOrange,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "OPTIMIZAR Y JUGAR",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }

    if (showConfigDialog) {
        GameConfigDialog(
            game = game,
            onDismiss = { showConfigDialog = false },
            onSave = { mode, fps ->
                onUpdateSettings(mode, fps)
                showConfigDialog = false
            },
            onDelete = {
                onDelete()
                showConfigDialog = false
            }
        )
    }
}

@Composable
fun GameConfigDialog(
    game: GameItem,
    onDismiss: () -> Unit,
    onSave: (String, Int) -> Unit,
    onDelete: () -> Unit
) {
    var selectedMode by remember { mutableStateOf(game.performanceMode) }
    var selectedFps by remember { mutableIntStateOf(game.targetFps) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Text(
                text = "Perfil de Juego: ${game.appName}",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column {
                Text(
                    text = "Modo de Optimización",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                listOf(
                    Triple("ULTRA", "Ultra Rendimiento (60 FPS / Limpieza Agresiva)", NeonOrange),
                    Triple("BALANCED", "Equilibrado (Temperatura media)", NeonCyan),
                    Triple("BATTERY", "Ahorro de Batería (Sin sobrecalentamiento)", CyberGreen)
                ).forEach { (mode, title, color) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMode = mode }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedMode == mode,
                            onClick = { selectedMode = mode },
                            colors = RadioButtonDefaults.colors(selectedColor = color)
                        )
                        Text(
                            text = title,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Objetivo de FPS",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(30, 45, 60, 90).forEach { fps ->
                        val isSelected = selectedFps == fps
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) NeonCyan else DarkCard)
                                .border(1.dp, if (isSelected) NeonCyan else DarkBorder, RoundedCornerShape(8.dp))
                                .clickable { selectedFps = fps }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$fps",
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else TextPrimary,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                if (game.packageName != "com.roblox.client") {
                    Spacer(modifier = Modifier.height(18.dp))
                    TextButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.textButtonColors(contentColor = CyberRed)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Eliminar juego de la lista")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(selectedMode, selectedFps) },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = TextSecondary)
            }
        }
    )
}
