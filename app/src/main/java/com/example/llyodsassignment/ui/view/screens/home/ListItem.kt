package com.example.llyodsassignment.ui.view.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

@Composable
fun ListItem(item: CatImagesUiModel, onClick: (CatImagesUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onClick(item) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
           Image(
               painter = rememberAsyncImagePainter(model = item.url),
               contentDescription = null,
               modifier = Modifier
                   .fillMaxWidth()
                   .size(200.dp)
                   .clip(RectangleShape),
               contentScale = ContentScale.Crop
           )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "CatImage ID : ${item.id}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}