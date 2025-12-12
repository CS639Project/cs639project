package com.example.kindnessjar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.Room
import com.example.kindnessjar.data.AppDatabase
import com.example.kindnessjar.navigation.Routes
import com.example.kindnessjar.repository.ChallengeRepository
import com.example.kindnessjar.screens.*
import com.example.kindnessjar.ui.components.BottomNavBar
import com.example.kindnessjar.ui.theme.KindnessJarTheme
import com.example.kindnessjar.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KindnessJarTheme {
                AppRoot()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AppRoot() {
    val navController = rememberNavController()

    // Create DB → Repo → ViewModel
    val db = Room.databaseBuilder(
        LocalContext.current,
        AppDatabase::class.java,
        "kindness_db"
    ).build()
    val repository = ChallengeRepository(db.challengeDao())

    val viewModel: MainViewModel = viewModel()
    viewModel.repository = repository

    // Load history automatically one time at startup
    LaunchedEffect(true) {
        viewModel.loadHistoryFromDb()
    }

    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != Routes.CHALLENGE) {
                BottomNavBar(navController)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = Routes.HOME
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        onPickNoteClick = { navController.navigate(Routes.CHALLENGE) }
                    )
                }

                composable(Routes.CHALLENGE) {
                    ChallengeScreen(
                        todayChallenge = viewModel.todayChallenge,
                        onMarkCompleted = {
                            viewModel.markTodayCompleted()
                            navController.popBackStack()
                        }
                    )
                }

                composable(Routes.PROGRESS) {
                    ProgressScreen(
                        streak = viewModel.streak,
                        weeklyCompleted = viewModel.weeklyCompleted
                    )
                }

                composable(Routes.HISTORY) {
                    HistoryScreen(historyList = viewModel.history)
                }
            }
        }
    }
}
