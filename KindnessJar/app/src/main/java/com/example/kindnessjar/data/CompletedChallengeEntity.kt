package com.example.kindnessjar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_challenge_table")
data class CompletedChallengeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val challenge: String,
    val date: String
)