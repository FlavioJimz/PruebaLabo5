package com.falconteam.laboratorio_5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falconteam.laboratorio_5.screens.MainScreen
import com.falconteam.laboratorio_5.ui.theme.Laboratorio5Theme
import com.falconteam.laboratorio_5.utils.BlogsitoViewModel

sealed class GRAPH(val graph: String) {
    data object MAIN: GRAPH(graph = "MAIN")
}

sealed class MainScreens(val route: String) {
    data object MainScreen: MainScreens(route = "main_screen")
    data object DetailsScreen: MainScreens(route = "details_screen")
}

@Composable
fun RootNavigation(
    viewModel: BlogsitoViewModel
) {
    val navController = rememberNavController()
    Laboratorio5Theme {
        NavHost(
            navController = navController,
            route = GRAPH.MAIN.graph,
            startDestination = MainScreens.MainScreen.route
        ) {
            composable(MainScreens.MainScreen.route) {
                MainScreen(
                    viewModel = viewModel,
                    onClick = { navController.navigate(MainScreens.DetailsScreen.route) }
                )
            }
        }
    }
}