package com.example.jikanapp.utils

sealed class Screen(val route: String) {
    object Home : Screen("homeScreen")
    object Detail : Screen("detailScreen")
}