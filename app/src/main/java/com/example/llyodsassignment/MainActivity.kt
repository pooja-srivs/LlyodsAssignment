package com.example.llyodsassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.llyodsassignment.ui.theme.CatImagesTheme
import com.example.llyodsassignment.ui.view.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatImagesTheme {
               Surface(color = MaterialTheme.colorScheme.background) {
                   val navigation = rememberNavController()
                   NavGraph(navHostController = navigation)

               }
            }
        }
    }
}
