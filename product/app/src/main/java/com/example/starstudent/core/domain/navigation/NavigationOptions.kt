package com.example.starstudent.core.domain.navigation

data class NavigationOptions(
    val label: String,
    val navigation: () -> Unit
)
