package com.example.kindnessjar.repository

import com.example.kindnessjar.data.ChallengeDao
import com.example.kindnessjar.data.ChallengeEntity
import com.example.kindnessjar.data.CompletedChallengeEntity

class ChallengeRepository(private val dao: ChallengeDao) {

    suspend fun saveCompleted(text: String, date: String) {
        dao.insertCompleted(CompletedChallengeEntity(challenge = text, date = date))
    }

    suspend fun getHistory(): List<CompletedChallengeEntity> {
        return dao.getAllCompleted()
    }

    suspend fun insertDefaultChallenges(items: List<String>) {
        val entities = items.map { ChallengeEntity(text = it) }
        dao.insertChallenges(entities)
    }

    suspend fun loadChallengeTemplates(): List<String> {
        return dao.getAllChallenges().map { it.text }
    }
}