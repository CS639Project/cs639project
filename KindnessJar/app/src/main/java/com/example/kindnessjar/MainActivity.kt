package com.example.kindnessjar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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

    val context = LocalContext.current
    val navController = rememberNavController()

    // Database → Repository → ViewModel setup
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "kindness_db"
    ).build()

    val repository = ChallengeRepository(db.challengeDao())
    val viewModel: MainViewModel = viewModel()

    // Inject repository into ViewModel
    viewModel.repository = repository

    // Load challenge template list + history ONCE on app launch
    LaunchedEffect(true) {
        viewModel.loadChallenges(context)
        viewModel.loadHistoryFromDb()
    }

    Scaffold(
        bottomBar = {
            val currentRoute = navController
                .currentBackStackEntryAsState()
                .value
                ?.destination
                ?.route

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

                // HOME SCREEN
                composable(Routes.HOME) {
                    HomeScreen(
                        onPickNoteClick = {
                            // Generate a NEW random, non-repeating challenge
                            viewModel.generateRandomChallenge()

                            // Navigate to challenge screen
                            navController.navigate(Routes.CHALLENGE)
                        }
                    )
                }

                // CHALLENGE SCREEN
                composable(Routes.CHALLENGE) {
                    ChallengeScreen(
                        todayChallenge = viewModel.todayChallenge,
                        onMarkCompleted = {
                            viewModel.markTodayCompleted()
                            navController.popBackStack()
                        }
                    )
                }

                // PROGRESS SCREEN
                composable(Routes.PROGRESS) {
                    ProgressScreen(
                        streak = viewModel.streak,
                        weeklyCompleted = viewModel.weeklyCompleted
                    )
                }

                // HISTORY SCREEN
                composable(Routes.HISTORY) {
                    HistoryScreen(historyList = viewModel.history)
                }
            }
        }
    }
}
