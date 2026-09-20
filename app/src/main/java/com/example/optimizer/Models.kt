package com.example.optimizer

data class MemoryStats(
    val totalPhysicalRamBytes: Long = 4L * 1024 * 1024 * 1024,
    val availableRamBytes: Long = 1800L * 1024 * 1024,
    val usedRamBytes: Long = 2200L * 1024 * 1024,
    val usedRamPercent: Int = 55,
    val virtualRamTotalMb: Int = 4096,
    val virtualRamUsedMb: Int = 2048,
    val lowMemory: Boolean = false,
    val batteryTempCelsius: Float = 34.5f,
    val batteryLevel: Int = 85,
    val isCharging: Boolean = false,
    val screenRefreshRate: Int = 60,
    val currentFps: Int = 60,
    val cpuCores: Int = 8,
    val deviceModel: String = "Android Device"
) {
    val totalPhysicalRamGb: Float
        get() = totalPhysicalRamBytes / (1024f * 1024f * 1024f)

    val availableRamGb: Float
        get() = availableRamBytes / (1024f * 1024f * 1024f)

    val usedRamGb: Float
        get() = usedRamBytes / (1024f * 1024f * 1024f)
}

data class BoostResult(
    val ramFreedMb: Int,
    val processesKilledCount: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val ramUsedBeforePercent: Int,
    val ramUsedAfterPercent: Int,
    val logs: List<String> = emptyList()
)

data class AppInfo(
    val packageName: String,
    val appName: String,
    val isGame: Boolean = false,
    val isSystemApp: Boolean = false,
    val estimatedRamMb: Int = 120,
    val isRunningInBackground: Boolean = true
)

data class PingStatus(
    val serverName: String,
    val host: String,
    val latencyMs: Int = -1,
    val isSuccess: Boolean = false,
    val statusMessage: String = "Pendiente"
)
