package com.example.llyodsassignment.ui.view.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.llyodsassignment.R
import com.example.llyodsassignment.ui.view.screens.backpress.BackPressHandler
import com.example.llyodsassignment.ui.view.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navHostController: NavHostController,
    sharedViewModel: HomeViewModel
) {
    val selectedCatImagesModel = sharedViewModel.selectedCatImagesModel

    val onBack: () -> Unit = {
        navHostController.navigateUp()
    }

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                TopAppBar(
                    title = { Text(stringResource(R.string.cat_image_details)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navHostController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                            )
                        }
                    }
                )
                HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(.8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = selectedCatImagesModel?.url ?: ""),
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.image_id, selectedCatImagesModel?.id ?: ""))
            Text(text = stringResource(R.string.width, selectedCatImagesModel?.width ?: ""))
            Text(text = stringResource(R.string.height, selectedCatImagesModel?.height ?: ""))
        }
    }
}
