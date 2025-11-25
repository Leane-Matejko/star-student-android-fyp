package com.example.starstudent.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.starstudent.screens.uiComponents.Background

sealed class Screens(val route: String) {
    object SignInRegisterScreen: Screens("sign_in_register")
    object SignInScreen: Screens("sign_in")
    object AddEmailScreen: Screens("add_email")
}