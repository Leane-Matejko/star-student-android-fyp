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
import com.example.starstudent.core.navigation.Screens
import com.example.starstudent.core.uiComponents.Background
import com.example.starstudent.core.uiComponents.button
import com.example.starstudent.core.uiComponents.inputField

@Composable
fun CreatePassword(navController: NavController){
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
            text = "Set Password",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier)
        inputField("PASSWORD")
        Spacer(modifier = Modifier)
        inputField("RE-RENTER PASSWORD")
        Spacer(modifier = Modifier)
        button("Next") {
            navController.navigate(Screens.HomeScreen.route)
        }

    }
}