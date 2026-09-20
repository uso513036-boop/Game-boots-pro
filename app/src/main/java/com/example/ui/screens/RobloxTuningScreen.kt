package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GameItem
import com.example.optimizer.MemoryOptimizer
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
fun RobloxTuningScreen(
    onLaunchRobloxTurbo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE11D48)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "OPTIMIZADOR ESPECÍFICO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Roblox Tuner (4GB RAM)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Launch Roblox Direct with Turbo
        Button(
            onClick = onLaunchRobloxTurbo,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("launch_roblox_with_turbo_tab_btn"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonOrange,
                contentColor = Color.Black
            )
        ) {
            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "APLICAR TURBO Y ABRIR ROBLOX",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Card 1: Por qué se traba tu teléfono
        TunerCard(
            title = "¿Por qué se traba Roblox en 4GB RAM + 4GB Virtual?",
            icon = Icons.Default.Warning,
            iconTint = CyberYellow
        ) {
            Text(
                text = "1. Memoria física vs virtual: Tu teléfono tiene 4GB de memoria física rápida (LPDDR) y 4GB de memoria virtual (SWAP/ZRAM) que usa el almacenamiento del teléfono.\n\n" +
                        "2. Cuello de botella: El chip de almacenamiento es hasta 10 veces más lento que la RAM real. Cuando Roblox consume 1.8GB de RAM y otras apps (WhatsApp, Chrome, TikTok) usan el resto, el teléfono colapsa transfiriendo datos a la memoria lenta.\n\n" +
                        "3. Solución: Cerrar los procesos en segundo plano con esta app y aplicar los ajustes gráficos descritos abajo.",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 2: Paso a Paso dentro de Roblox
        TunerCard(
            title = "Ajustes Clave Dentro de Roblox (60 FPS)",
            icon = Icons.Default.PlayArrow,
            iconTint = NeonCyan
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                StepItem(
                    number = "1",
                    title = "Gráficos en Manual (Nivel 1 o 2)",
                    description = "Por defecto Roblox viene en 'Modo de Gráficos: Automático'. En automático el juego intenta renderizar sombras y reflejos pesados. Ponlo en MANUAL y baja la barra a Nivel 1 o 2 para tener 60 FPS estables."
                )

                StepItem(
                    number = "2",
                    title = "Desactivar Vibración de Cámara",
                    description = "En Configuración de Roblox, desactiva 'Vibración de Cámara'. Reduce el uso de física y ahorra ciclos de CPU."
                )

                StepItem(
                    number = "3",
                    title = "Reducir Volumen General al 50%",
                    description = "El motor de audio de Roblox decodifica sonidos en tiempo real; bajar el volumen alivia el procesador en mapas con mucha música y efectos."
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 3: Ajustes del Sistema Android (Opciones de desarrollador)
        TunerCard(
            title = "Ajustes de Android recomendados",
            icon = Icons.Default.DeveloperMode,
            iconTint = CyberGreen
        ) {
            Column {
                Text(
                    text = "Ajusta estas opciones para evitar que otras apps se abran solas en segundo plano mientras juegas:",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                StepItem(
                    number = "A",
                    title = "Límite de procesos en segundo plano",
                    description = "En Opciones de desarrollador, busca 'Límite de procesos en segundo plano' y cámbialo a 'Máximo 2 procesos' o 'Sin procesos'."
                )

                Spacer(modifier = Modifier.height(8.dp))

                StepItem(
                    number = "B",
                    title = "Desactivar Superposiciones HW",
                    description = "Activa 'Desactivar superposiciones de hardware'. Esto fuerza al procesador gráfico (GPU) a componer la pantalla directamente, liberando memoria RAM de video."
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { MemoryOptimizer.openDeveloperOptions(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkSurface,
                        contentColor = NeonCyan
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir Opciones de Desarrollador", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Card 4: Limpieza de Caché de Roblox
        TunerCard(
            title = "Mantenimiento y Caché de Roblox",
            icon = Icons.Default.CleaningServices,
            iconTint = NeonOrange
        ) {
            Column {
                Text(
                    text = "Roblox almacena miles de texturas y sonidos temporales en la memoria de tu teléfono. Borrar la caché (no los datos) libera hasta 1 GB de espacio y hace que cargue más rápido.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { MemoryOptimizer.openAppSettings(context, "com.roblox.client") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkSurface,
                        contentColor = NeonOrange
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonOrange.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Default.CleaningServices, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Borrar Caché en Ajustes de Roblox", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TunerCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkCard)
            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun StepItem(
    number: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(NeonCyan.copy(alpha = 0.2f))
                .border(1.dp, NeonCyan, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 11.sp,
                color = TextSecondary,
                lineHeight = 15.sp
            )
        }
    }
}
