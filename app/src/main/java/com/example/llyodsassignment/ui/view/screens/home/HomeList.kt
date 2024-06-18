package com.example.llyodsassignment.ui.view.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

@Composable
fun HomeList(data: List<CatImagesUiModel>, onClick: (CatImagesUiModel) -> Unit) {
    LazyColumn(
        content = {
            items(data) { item ->
                ListItem(item, onClick)
            }
        }
    )
}