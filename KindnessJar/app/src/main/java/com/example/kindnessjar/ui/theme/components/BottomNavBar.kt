package com.example.kindnessjar.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kindnessjar.navigation.Routes

// ICON IMPORTS
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History

@Composable
fun BottomNavBar(navController: NavHostController) {

    val items = listOf(
        NavItem("Progress", Routes.PROGRESS, Icons.AutoMirrored.Filled.ShowChart),
        NavItem("Home", Routes.HOME, Icons.Filled.Home),
        NavItem("History", Routes.HISTORY, Icons.Filled.History)
    )

    NavigationBar(
        containerColor = Color(0xFFF8F8F8)
    ) {
        val currentRoute = navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

data class NavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
