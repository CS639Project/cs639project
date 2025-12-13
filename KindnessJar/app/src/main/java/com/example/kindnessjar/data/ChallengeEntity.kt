package com.example.kindnessjar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_table")
data class ChallengeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String
)