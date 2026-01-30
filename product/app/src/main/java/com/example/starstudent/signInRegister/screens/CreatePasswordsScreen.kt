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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField

/* Responsible for populating the create password screen within the register flow.
*/
@Composable
fun CreatePasswordScreen(navController: NavController){

    val viewModel = viewModel<CreatePasswordViewModel>()
    val context = LocalContext.current

    Background()
    Spacer(modifier = Modifier
        .testTag("CreatePasswordsScreen"))
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
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .testTag("setPasswordLabel")
        )
        Text(
            text = "Welcome, " + viewModel.userEmail,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .testTag("welcomeLabel")
        )
        Spacer(modifier = Modifier)
        inputField("PASSWORD",
            viewModel.firstPassword,
            1,
            onValueChange = {viewModel.setFirstPasswordValue(it)})
        Spacer(modifier = Modifier)
        inputField("RE-RENTER PASSWORD",
            viewModel.secondPassword,
            2,
            onValueChange = {viewModel.setSecondPasswordValue(it)})
        Spacer(modifier = Modifier)
        button("Next",1) {

            viewModel.verify(navController)
        }
        //Error window
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