package com.example.starstudent.userAccounts.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.screens.ApplicationViewModel
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import com.example.starstudent.core.view.uiComponents.numInputField
import com.example.starstudent.core.view.uiComponents.spacer
import com.example.starstudent.core.view.uiComponents.textField
import com.example.starstudent.core.view.uiComponents.toggle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileInformationScreen(
    navController: NavController,
    applicationViewModel: ApplicationViewModel
){

    val viewModel = viewModel<ProfileInformationViewModel>()

    BannerFormat (
        viewModel.profileUsername,
        date = viewModel.curDate,
        profileOnClick = { },
        list = viewModel.getNavigationMenu(navController),
        showNav = viewModel.showNavMenu,
        navOnClick = {viewModel.showNavMenu()},
        onDismissNav = {viewModel.dismissNavMenu()}
    ){
        padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                ProfileInformationContent(navController, viewModel, applicationViewModel)
            }
        }
    }

    viewModel.viewModelScope.launch{

        viewModel.updateTime()
        delay(60000 - System.currentTimeMillis() % 60000)

        while(true){
            viewModel.updateTime()
            delay(60000)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentTheme()
    }
}



@Composable
fun ProfileInformationContent(
    navController: NavController,
    viewModel: ProfileInformationViewModel,
    applicationViewModel: ApplicationViewModel) {

    Column{

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 10.dp)
        ) {
            Text(
                text = "App Settings",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 10.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 10.dp)
                ) {
                    Text(
                        text = "Profile Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Name",
                        viewModel.profileUsername,
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Birthday",
                        viewModel.profileBirthday,
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Email",
                        viewModel.profileEmail,
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Location Access",
                        viewModel.profileLocationAccessFormatted(),
                        50
                    )

                    button("Update Info",
                        seqNumber = 1
                    ) {
                        viewModel.showDialog()
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 10.dp)
            ) {
                Text(
                    text = "Theme Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .height(100.dp)
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Current Theme",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Text(
                            text = viewModel.currentTheme.replaceFirstChar { it.uppercase() },
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .weight(1f)
                        )

                        IconButton(
                            onClick = {
                                viewModel.showThemeOptions()
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Icon(
                                Icons.Filled.Create,
                                contentDescription = "CustomiseHomepage",
                                modifier = Modifier,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }

                        DropdownMenu(
                            expanded = viewModel.showThemeOptions,
                            shape = RoundedCornerShape(20.dp),
                            onDismissRequest = {
                                viewModel.showThemeOptions()
                            }
                        ) {

                            viewModel.themeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            option.replaceFirstChar { it.uppercase() },
                                            color = MaterialTheme.colorScheme.background,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 5.dp)
                                        )
                                    },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.primary),
                                    onClick = {
                                        viewModel.viewModelScope.launch {
                                            viewModel.updateTheme(
                                                option,
                                                applicationViewModel
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(460.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 10.dp)
                    ) {

                        Text(
                            text = "Avatar Settings",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        avatarWindow(
                            viewModel.username + ", Age,"
                                    + " Status "
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        button(
                            "Edit Avatar",
                            seqNumber = 1
                        ) { }
                    }
                }
            }
        }
    }

    if(viewModel.enableSettingUpdate){
        Dialog(onDismissRequest = {
                viewModel.closeDialog()
        }){
            SettingUpdateDialog(
                viewModel
            )
        }
    }

}

@Composable
fun SettingUpdateDialog(viewModel: ProfileInformationViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
        ) {

            Text(
                text = "Update Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            inputField(
                "Profile Name",
                viewModel.updateUsername,
                1
            ) { viewModel.updateUsernameChange(it) }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .height(260.dp)
                    .padding(20.dp)
            ) {

                Text(
                    text = "Birthday",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                numInputField(
                    "Day",
                    viewModel.updateDD.toString(),
                    4,
                    KeyboardType.Number
                ) {
                    if (it.isEmpty()){
                        viewModel.setDayNull()
                    }
                    else if ((it.length <= 2) && (viewModel.withinDayRange(it))){
                        viewModel.updateDDChange(it)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                numInputField(
                    "Month",
                    viewModel.updateMM.toString(),
                    5,
                    KeyboardType.Number
                ) {
                    if(it.isEmpty()){
                        viewModel.setMonthNull()
                    }
                    else if ((it.length <= 2) && (viewModel.withinMonthRange(it))){
                        viewModel.updateMMChange(it)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                numInputField(
                    "Year",
                    viewModel.updateYYYY.toString(),
                    6,
                    KeyboardType.Number
                ) {
                    if(it.isEmpty()){
                        viewModel.setYearNull()
                    }
                    else if((it.length <= 4) && (viewModel.isRealYear(it))){
                        viewModel.updateYYYYChange(it)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            toggle(
                header = "Location Access",
                isChecked = viewModel.updateLocationAccess
            ) {viewModel.updateLocationAccess()}

            Spacer(modifier = Modifier.height(20.dp))

            button(
                "Save",
                1
            ) {
                viewModel.saveChanges()
            }

        }
    }

}