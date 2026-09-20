package com.example.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {
    val allGames: Flow<List<GameItem>> = gameDao.getAllGames()

    suspend fun getGameByPackage(packageName: String): GameItem? {
        return gameDao.getGameByPackage(packageName)
    }

    suspend fun insertOrUpdate(game: GameItem): Long {
        return gameDao.insertGame(game)
    }

    suspend fun updateGame(game: GameItem) {
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: GameItem) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteById(id: Long) {
        gameDao.deleteById(id)
    }

    suspend fun recordLaunch(packageName: String, timestamp: Long) {
        gameDao.recordLaunch(packageName, timestamp)
    }
}
