package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val performanceMode: String = "ULTRA", // ULTRA, BALANCED, BATTERY
    val targetFps: Int = 60,
    val launchCount: Int = 0,
    val lastBoostTimestamp: Long = 0L,
    val isFavorite: Boolean = false,
    val customNotes: String = ""
)
