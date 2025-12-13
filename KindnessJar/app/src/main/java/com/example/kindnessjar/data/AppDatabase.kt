package com.example.kindnessjar.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ChallengeEntity::class, CompletedChallengeEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
}
