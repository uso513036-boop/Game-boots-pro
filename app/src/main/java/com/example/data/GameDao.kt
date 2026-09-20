package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games ORDER BY isFavorite DESC, launchCount DESC, id ASC")
    fun getAllGames(): Flow<List<GameItem>>

    @Query("SELECT * FROM games WHERE packageName = :packageName LIMIT 1")
    suspend fun getGameByPackage(packageName: String): GameItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameItem): Long

    @Update
    suspend fun updateGame(game: GameItem)

    @Delete
    suspend fun deleteGame(game: GameItem)

    @Query("DELETE FROM games WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE games SET launchCount = launchCount + 1, lastBoostTimestamp = :timestamp WHERE packageName = :packageName")
    suspend fun recordLaunch(packageName: String, timestamp: Long)
}
