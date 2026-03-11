package com.example.starstudent.userAccounts.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.textField
import com.example.starstudent.core.view.uiComponents.toggle

@Composable
fun ProfileInformationScreen(navController: NavController){
    BannerFormat (
        "leane",
        date = "11 Wed Mar",
        {}
    ){
        padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                ProfileInformationContent(navController)
            }
        }
    }
}

@Composable
fun ProfileInformationContent(navController: NavController) {
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
                    .height(500.dp),
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
                        "Leane",
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Birthday",
                        "12/10/2003",
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    textField(
                        "Email",
                        "leane@gmail.com",
                        50
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    toggle(
                        header = "Location Access",
                        isChecked = true,

                    )

                    button("Update Info",
                        seqNumber = 1
                    ) { }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                            "Blanche, age, tired"
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

}