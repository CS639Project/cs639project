package com.example.kindnessjar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.kindnessjar.ui.theme.KindnessJarTheme

import com.example.kindnessjar.navigation.Routes
import com.example.kindnessjar.screens.HomeScreen
import com.example.kindnessjar.screens.ProgressScreen
import com.example.kindnessjar.screens.HistoryScreen
import com.example.kindnessjar.screens.ChallengeScreen

import com.example.kindnessjar.ui.components.BottomNavBar
import com.example.kindnessjar.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KindnessJarTheme {
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    MaterialTheme {
        val navController = rememberNavController()
        val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

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
                        HomeScreen(onPickNoteClick = {
                            navController.navigate(Routes.CHALLENGE)
                        })
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
}

