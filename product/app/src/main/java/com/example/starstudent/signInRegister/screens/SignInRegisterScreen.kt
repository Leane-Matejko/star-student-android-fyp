package com.example.starstudent.signInRegister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.button
//import kotlinx.coroutines.runBlocking

@Composable
    fun SignInRegisterScreen(navController: NavHostController) {

        val viewModel: SignInRegisterViewModel = viewModel()

        Background()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(40.dp)
        ) {

            Text(
                text = "Sign In to StarStudent",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            button("Sign In") {
                navController.navigate(Screens.SignInScreen.route)
            }
            button("Register") {
                viewModel.testDB()
//                navController.navigate(Screens.AddEmailScreen.route)
            }
        }
    }