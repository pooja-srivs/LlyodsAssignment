package com.example.llyodsassignment.ui.view

sealed class Route(val route: String) {
    object List : Route("list")
    object Detail : Route("detail")
}