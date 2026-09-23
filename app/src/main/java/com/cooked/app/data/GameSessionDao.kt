package com.cooked.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameSessionDao {
    @Insert
    suspend fun insert(session: GameSession)

    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    fun all(): Flow<List<GameSession>>

    @Query("SELECT SUM(durationMs) FROM sessions WHERE startTime >= :since")
    suspend fun totalSince(since: Long): Long?

    @Query("DELETE FROM sessions")
    suspend fun clearAll()
}
