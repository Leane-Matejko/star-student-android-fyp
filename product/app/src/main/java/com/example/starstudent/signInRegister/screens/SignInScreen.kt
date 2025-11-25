package com.example.starstudent.signInRegister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.starstudent.navigation.Screens
import com.example.starstudent.signInRegister.screens.uiComponents.Background
import com.example.starstudent.signInRegister.screens.uiComponents.button
import com.example.starstudent.signInRegister.screens.uiComponents.inputField

@Composable
fun SignInScreen(navController: NavController){

    Background()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(40.dp)
    ) {
        Text(
            text = "Sign In",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier)
        inputField("EMAIL")
        Spacer(modifier = Modifier)
        inputField("PASSWORD")
        Spacer(modifier = Modifier)
        button("Next") {
            navController.navigate(Screens.HomeScreen.route)
        }
    }



}