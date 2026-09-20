package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.BoosterTab
import com.example.ui.BoosterViewModel
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCanvas
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonOrange
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainGameBoosterScreen(
    viewModel: BoosterViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val stats by viewModel.memoryStats.collectAsStateWithLifecycle()
    val isBoosting by viewModel.isBoosting.collectAsStateWithLifecycle()
    val boostStepMessage by viewModel.boostStepMessage.collectAsStateWithLifecycle()
    val lastResult by viewModel.lastBoostResult.collectAsStateWithLifecycle()
    val games by viewModel.games.collectAsStateWithLifecycle()
    val installedApps by viewModel.installedApps.collectAsStateWithLifecycle()
    val pingStatuses by viewModel.pingStatuses.collectAsStateWithLifecycle()
    val isPingTesting by viewModel.isPingTesting.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkCanvas,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(NeonOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "GAME BOOSTER FPS",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = TextPrimary,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "4GB RAM + 4GB VIRTUAL TUNER",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSurface,
                    titleContentColor = TextPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurface,
                contentColor = TextPrimary,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == BoosterTab.TURBO,
                    onClick = { viewModel.selectTab(BoosterTab.TURBO) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == BoosterTab.TURBO) Icons.Filled.RocketLaunch else Icons.Outlined.RocketLaunch,
                            contentDescription = "Turbo"
                        )
                    },
                    label = { Text("Turbo", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = NeonCyan,
                        indicatorColor = NeonCyan,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("tab_turbo")
                )

                NavigationBarItem(
                    selected = selectedTab == BoosterTab.GAMES,
                    onClick = { viewModel.selectTab(BoosterTab.GAMES) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == BoosterTab.GAMES) Icons.Filled.SportsEsports else Icons.Outlined.SportsEsports,
                            contentDescription = "Juegos"
                        )
                    },
                    label = { Text("Mis Juegos", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = NeonCyan,
                        indicatorColor = NeonCyan,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("tab_games")
                )

                NavigationBarItem(
                    selected = selectedTab == BoosterTab.ROBLOX_TUNER,
                    onClick = { viewModel.selectTab(BoosterTab.ROBLOX_TUNER) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == BoosterTab.ROBLOX_TUNER) Icons.Filled.Tune else Icons.Outlined.Tune,
                            contentDescription = "Roblox Tuner"
                        )
                    },
                    label = { Text("Roblox", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = NeonOrange,
                        indicatorColor = NeonOrange,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("tab_roblox")
                )

                NavigationBarItem(
                    selected = selectedTab == BoosterTab.PROCESSES,
                    onClick = { viewModel.selectTab(BoosterTab.PROCESSES) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == BoosterTab.PROCESSES) Icons.Filled.Speed else Icons.Outlined.Speed,
                            contentDescription = "Procesos"
                        )
                    },
                    label = { Text("Procesos", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = NeonCyan,
                        indicatorColor = NeonCyan,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("tab_processes")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                BoosterTab.TURBO -> TurboDashboardScreen(
                    stats = stats,
                    isBoosting = isBoosting,
                    boostStepMessage = boostStepMessage,
                    lastResult = lastResult,
                    games = games,
                    onTriggerBoost = { viewModel.triggerTurboBoost() },
                    onLaunchGame = { game -> viewModel.launchGameWithTurbo(context, game) },
                    onNavigateToRobloxTuner = { viewModel.selectTab(BoosterTab.ROBLOX_TUNER) },
                    onRefreshStats = { viewModel.refreshStats() }
                )

                BoosterTab.GAMES -> GamesLauncherScreen(
                    games = games,
                    installedApps = installedApps,
                    onLaunchGame = { game -> viewModel.launchGameWithTurbo(context, game) },
                    onToggleFavorite = { game -> viewModel.toggleFavorite(game) },
                    onUpdateGameSettings = { game, mode, fps -> viewModel.updateGameSettings(game, mode, fps) },
                    onDeleteGame = { game -> viewModel.removeGame(game) },
                    onAddGame = { app -> viewModel.addGame(app) }
                )

                BoosterTab.ROBLOX_TUNER -> RobloxTuningScreen(
                    onLaunchRobloxTurbo = {
                        val roblox = games.find { it.packageName == "com.roblox.client" } ?: com.example.data.GameItem(
                            packageName = "com.roblox.client",
                            appName = "Roblox",
                            performanceMode = "ULTRA",
                            targetFps = 60
                        )
                        viewModel.launchGameWithTurbo(context, roblox)
                    }
                )

                BoosterTab.PROCESSES -> ProcessAndNetworkScreen(
                    installedApps = installedApps,
                    pingStatuses = pingStatuses,
                    isPingTesting = isPingTesting,
                    onKillAllProcesses = { viewModel.triggerTurboBoost() },
                    onKillSingleApp = { app -> viewModel.killSingleApp(context, app) },
                    onRunPingTest = { viewModel.runPingTests() }
                )
            }

            // Full-screen Animated Overlay during Turbo Boost
            AnimatedVisibility(
                visible = isBoosting,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.75f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(60.dp),
                            color = NeonOrange,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "MODO TURBO ACTIVADO",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = boostStepMessage.ifEmpty { "Liberando memoria y compactando RAM..." },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NeonCyan
                        )
                    }
                }
            }
        }
    }
}
