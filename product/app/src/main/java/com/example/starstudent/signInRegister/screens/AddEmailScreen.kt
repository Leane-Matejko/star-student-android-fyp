package com.example.starstudent.signInRegister.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import kotlinx.coroutines.launch

/* Responsible for populating the add email screen within the register flow.
*/
@Composable
fun AddEmailScreen(navController: NavController) {

    val viewModel = viewModel<AddEmailViewModel>()
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
            text = "Add your Email",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .testTag("emailLabel")
        )
        Spacer(modifier = Modifier)
        inputField("EMAIL",
            viewModel.emailString,
            1,
            onValueChange = {viewModel.setEmailChange(it)})
        Spacer(modifier = Modifier)
        button("Next", 1) {
            viewModel.next(navController)
        }

        //Error Window
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

//Navigate to the create password window.
fun navigateToCreatePassword(navController: NavController){
    navController.navigate(Screens.VerifyEmailScreen.route)
}