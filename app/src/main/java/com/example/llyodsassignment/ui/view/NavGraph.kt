package com.example.llyodsassignment.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.llyodsassignment.ui.view.screens.detail.DetailScreen
import com.example.llyodsassignment.ui.view.screens.home.HomeScreen
import com.example.llyodsassignment.ui.view.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navHostController: NavHostController) {

    val sharedViewModel: HomeViewModel = viewModel()

    NavHost(navController = navHostController, startDestination = Route.List.route) {

        composable(Route.List.route) {
            HomeScreen(navController = navHostController, sharedViewModel)
        }

        composable(route = Route.Detail.route) {
            DetailScreen(navHostController = navHostController, sharedViewModel = sharedViewModel)
        }
    }
}