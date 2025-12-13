package com.example.kindnessjar.data

import androidx.room.*

@Dao
interface ChallengeDao {

    @Insert
    suspend fun insertCompleted(item: CompletedChallengeEntity)

    @Query("SELECT * FROM completed_challenge_table ORDER BY id DESC")
    suspend fun getAllCompleted(): List<CompletedChallengeEntity>

    @Insert
    suspend fun insertChallenges(list: List<ChallengeEntity>)

    @Query("SELECT * FROM challenge_table")
    suspend fun getAllChallenges(): List<ChallengeEntity>

}
