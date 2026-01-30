package com.example.starstudent.signInRegister.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import androidx.compose.runtime.LaunchedEffect

/* Responsible for populating the sign in screen within the existing user sign in flow.
*/
@Composable
fun SignInScreen(navController: NavController){

    val viewModel = viewModel<SignInViewModel>()
    val context = LocalContext.current

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
        inputField("EMAIL",
            viewModel.emailString,
            1,
         onValueChange = {viewModel.setEmailChange(it)})
        Spacer(modifier = Modifier)
        inputField("PASSWORD",
            viewModel.passwordString,
            2,
            onValueChange = {viewModel.setPasswordChange(it)})
        Spacer(modifier = Modifier)
        button("Next",1) {
            viewModel.next(navController)
        }

        //Error window pop-up
        LaunchedEffect(viewModel.errorWindow) {
            if (viewModel.errorWindow) {
                Toast.makeText(
                    context,
                    viewModel.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}