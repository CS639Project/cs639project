package com.example.kindnessjar.viewmodel

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

    private val _todayChallenge = MutableStateFlow("Feed a street dog today")
    val todayChallenge: StateFlow<String> = _todayChallenge

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    private val _weeklyCompleted = MutableStateFlow(0)
    val weeklyCompleted: StateFlow<Int> = _weeklyCompleted

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    @RequiresApi(Build.VERSION_CODES.O)
    fun markTodayCompleted() {
        val today = LocalDate.now().toString()
        val text = _todayChallenge.value

        viewModelScope.launch {
            repository.saveCompleted(text, today)
            loadHistoryFromDb()

            // Update streak + weekly
            _streak.value = _streak.value + 1
            _weeklyCompleted.value = _weeklyCompleted.value + 1
        }
    }

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
