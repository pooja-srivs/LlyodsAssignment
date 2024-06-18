package com.example.llyodsassignment.ui.view.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.llyodsassignment.R
import com.example.llyodsassignment.ui.view.Route
import com.example.llyodsassignment.ui.view.effect.HomeEffect
import com.example.llyodsassignment.ui.view.viewmodel.HomeViewModel
import com.example.llyodsassignment.ui.view.intent.HomeIntent
import com.example.llyodsassignment.ui.view.state.HomeState

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val state by viewModel.catImagesStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> {
                    navController.navigate(Route.Detail.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            ) {
                TopAppBar(title = { Text(stringResource(R.string.cat_image_listing)) })
                HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {

                is HomeState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeState.Success -> {
                    HomeList(data = (state as HomeState.Success).value) { catImage ->
                        viewModel.handleIntents(HomeIntent.SelectCatImage(catImage))
                    }
                }

                is HomeState.Failure -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (state as HomeState.Failure).throwable.message
                                ?: stringResource(R.string.failed_to_load_the_data),
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}
