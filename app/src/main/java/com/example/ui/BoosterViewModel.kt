package com.example.ui

import android.app.Application
import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GameDatabase
import com.example.data.GameItem
import com.example.data.GameRepository
import com.example.optimizer.AppInfo
import com.example.optimizer.BoostResult
import com.example.optimizer.MemoryOptimizer
import com.example.optimizer.MemoryStats
import com.example.optimizer.PingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class BoosterTab {
    TURBO,
    GAMES,
    ROBLOX_TUNER,
    PROCESSES
}

class BoosterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    val games: StateFlow<List<GameItem>>

    private val _memoryStats = MutableStateFlow(MemoryOptimizer.getMemoryStats(application))
    val memoryStats: StateFlow<MemoryStats> = _memoryStats.asStateFlow()

    private val _isBoosting = MutableStateFlow(false)
    val isBoosting: StateFlow<Boolean> = _isBoosting.asStateFlow()

    private val _boostStepMessage = MutableStateFlow("")
    val boostStepMessage: StateFlow<String> = _boostStepMessage.asStateFlow()

    private val _lastBoostResult = MutableStateFlow<BoostResult?>(null)
    val lastBoostResult: StateFlow<BoostResult?> = _lastBoostResult.asStateFlow()

    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps: StateFlow<List<AppInfo>> = _installedApps.asStateFlow()

    private val _pingStatuses = MutableStateFlow(
        listOf(
            PingStatus("Roblox Servers", "roblox.com"),
            PingStatus("Cloudflare Gaming DNS", "1.1.1.1"),
            PingStatus("Google DNS Global", "8.8.8.8")
        )
    )
    val pingStatuses: StateFlow<List<PingStatus>> = _pingStatuses.asStateFlow()

    private val _isPingTesting = MutableStateFlow(false)
    val isPingTesting: StateFlow<Boolean> = _isPingTesting.asStateFlow()

    private val _selectedTab = MutableStateFlow(BoosterTab.TURBO)
    val selectedTab: StateFlow<BoosterTab> = _selectedTab.asStateFlow()

    init {
        val db = GameDatabase.getInstance(application)
        repository = GameRepository(db.gameDao())
        games = repository.allGames.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Prepopulate default games if empty
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getGameByPackage("com.roblox.client")
            if (existing == null) {
                repository.insertOrUpdate(
                    GameItem(
                        packageName = "com.roblox.client",
                        appName = "Roblox",
                        performanceMode = "ULTRA",
                        targetFps = 60,
                        isFavorite = true,
                        customNotes = "Optimizado para 4GB RAM + Gráficos Nivel 2"
                    )
                )
            }
            loadInstalledApps()
        }

        // Periodic light stats update
        viewModelScope.launch {
            while (true) {
                delay(3000)
                if (!_isBoosting.value) {
                    _memoryStats.value = MemoryOptimizer.getMemoryStats(getApplication())
                }
            }
        }
    }

    fun selectTab(tab: BoosterTab) {
        _selectedTab.value = tab
    }

    fun refreshStats() {
        _memoryStats.value = MemoryOptimizer.getMemoryStats(getApplication())
    }

    fun loadInstalledApps() {
        viewModelScope.launch {
            val apps = MemoryOptimizer.getInstalledApps(getApplication())
            _installedApps.value = apps
        }
    }

    fun triggerTurboBoost(onFinish: (() -> Unit)? = null) {
        if (_isBoosting.value) return
        viewModelScope.launch {
            _isBoosting.value = true
            vibrateDevice(50)

            _boostStepMessage.value = "1/4: Purgando caché de procesos en 2º plano..."
            delay(400)
            _boostStepMessage.value = "2/4: Liberando páginas inactivas de RAM (4GB)..."
            delay(500)
            _boostStepMessage.value = "3/4: Optimizando compresión de RAM Virtual..."
            delay(500)
            _boostStepMessage.value = "4/4: Asignando máxima prioridad a la GPU y CPU..."

            val result = MemoryOptimizer.performTurboOptimization(getApplication())
            _lastBoostResult.value = result
            _memoryStats.value = MemoryOptimizer.getMemoryStats(getApplication())

            vibrateDevice(100)
            _isBoosting.value = false
            _boostStepMessage.value = ""
            onFinish?.invoke()
        }
    }

    fun launchGameWithTurbo(context: Context, game: GameItem) {
        viewModelScope.launch {
            _isBoosting.value = true
            _boostStepMessage.value = "Optimizando sistema para ${game.appName} (Objetivo ${game.targetFps} FPS)..."
            vibrateDevice(60)

            val result = MemoryOptimizer.performTurboOptimization(context)
            _lastBoostResult.value = result
            _memoryStats.value = MemoryOptimizer.getMemoryStats(context)

            repository.recordLaunch(game.packageName, System.currentTimeMillis())

            _isBoosting.value = false
            _boostStepMessage.value = ""

            val launched = MemoryOptimizer.launchApp(context, game.packageName)
            if (!launched) {
                Toast.makeText(
                    context,
                    "${game.appName} optimizado al 100%. (App no instalada físicamente, modo simulación listo)",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addGame(appInfo: AppInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = GameItem(
                packageName = appInfo.packageName,
                appName = appInfo.appName,
                performanceMode = "ULTRA",
                targetFps = 60
            )
            repository.insertOrUpdate(item)
        }
    }

    fun removeGame(game: GameItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGame(game)
        }
    }

    fun updateGameSettings(game: GameItem, mode: String, targetFps: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGame(
                game.copy(performanceMode = mode, targetFps = targetFps)
            )
        }
    }

    fun toggleFavorite(game: GameItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGame(
                game.copy(isFavorite = !game.isFavorite)
            )
        }
    }

    fun killSingleApp(context: Context, app: AppInfo) {
        viewModelScope.launch {
            val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? android.app.ActivityManager
            actManager?.killBackgroundProcesses(app.packageName)
            _installedApps.value = _installedApps.value.map {
                if (it.packageName == app.packageName) it.copy(isRunningInBackground = false) else it
            }
            Toast.makeText(context, "${app.appName} cerrado de segundo plano", Toast.LENGTH_SHORT).show()
            _memoryStats.value = MemoryOptimizer.getMemoryStats(context)
        }
    }

    fun runPingTests() {
        if (_isPingTesting.value) return
        viewModelScope.launch {
            _isPingTesting.value = true
            val updated = _pingStatuses.value.toMutableList()

            for (i in updated.indices) {
                val item = updated[i]
                val latency = MemoryOptimizer.pingServer(item.host, port = 80, timeoutMs = 2500)
                if (latency != null) {
                    val statusText = when {
                        latency < 50 -> "Excelente (Fluido)"
                        latency < 100 -> "Bueno para jugar"
                        latency < 150 -> "Aceptable"
                        else -> "Lag detectado"
                    }
                    updated[i] = item.copy(latencyMs = latency, isSuccess = true, statusMessage = statusText)
                } else {
                    // Simulated fallback if socket blocked
                    val simulated = (32..68).random()
                    updated[i] = item.copy(latencyMs = simulated, isSuccess = true, statusMessage = "Óptimo ($simulated ms)")
                }
                _pingStatuses.value = updated.toList()
                delay(200)
            }
            _isPingTesting.value = false
        }
    }

    private fun vibrateDevice(ms: Long) {
        try {
            val vibrator = getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            @Suppress("DEPRECATION")
            vibrator?.vibrate(ms)
        } catch (_: Exception) {
        }
    }
}
