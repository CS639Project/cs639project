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

import com.example.kindnessjar.navigation.Routes
import com.example.kindnessjar.screens.HomeScreen
import com.example.kindnessjar.screens.ProgressScreen
import com.example.kindnessjar.screens.HistoryScreen
import com.example.kindnessjar.ui.theme.components.BottomNavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoot()
        }
    }
}

@Composable
private fun AppRoot() {
    MaterialTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavBar { route ->
                    // navigate when user taps a nav item
                    navController.navigate(route) {
                        // avoid multiple copies on back stack
                        launchSingleTop = true
                        // keep the nav graph root in backstack (optional)
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME
                ) {
                    composable(Routes.HOME) { HomeScreen() }
                    composable(Routes.PROGRESS) { ProgressScreen() }
                    composable(Routes.HISTORY) { HistoryScreen() }
                }
            }
        }
    }
}



