package com.example.kindnessjar.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kindnessjar.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel : ViewModel() {

    lateinit var repository: ChallengeRepository
    private val _challenges = MutableStateFlow<List<String>>(emptyList())

    private val _todayChallenge = MutableStateFlow("")
    val todayChallenge: StateFlow<String> = _todayChallenge

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    private val _weeklyCompleted = MutableStateFlow(0)
    val weeklyCompleted: StateFlow<Int> = _weeklyCompleted

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    // -------- LOAD CHALLENGE LIST FROM DATABASE --------
    fun loadChallenges(context: Context) {
        viewModelScope.launch {
            val array = context.resources.getStringArray(com.example.kindnessjar.R.array.challenge_list)

            // If DB empty, insert them
            val fromDb = repository.loadChallengeTemplates()
            if (fromDb.isEmpty()) {
                repository.insertDefaultChallenges(array.toList())
            }

            // Load from DB again
            _challenges.value = repository.loadChallengeTemplates()
        }
    }

    // -------- GENERATE A NON-REPEATING RANDOM CHALLENGE --------
    fun generateRandomChallenge() {
        val list = _challenges.value
        if (list.isEmpty()) return

        var next = list.random()
        while (next == _todayChallenge.value) {
            next = list.random()
        }
        _todayChallenge.value = next
    }

    // -------- WHEN USER MARKS TODAY COMPLETED --------
    @RequiresApi(Build.VERSION_CODES.O)
    fun markTodayCompleted() {
        val today = LocalDate.now().toString()
        val text = _todayChallenge.value

        viewModelScope.launch {
            repository.saveCompleted(text, today)
            loadHistoryFromDb()
            _streak.value += 1
            _weeklyCompleted.value += 1
            if (_weeklyCompleted.value > 7) {
                _weeklyCompleted.value = 1
            }

        }
    }

    // -------- LOAD HISTORY FROM DB --------
    fun loadHistoryFromDb() {
        viewModelScope.launch {
            val items = repository.getHistory()
            _history.value = items.map {
                HistoryItem(it.challenge, it.date)
            }
        }
    }
}

data class HistoryItem(
    val title: String,
    val date: String
)
