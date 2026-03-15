package com.example.starstudent.studySpaces.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.textField

@Preview
@Composable
fun StudyCentre(){
//    Background()
    BannerFormat(
        username = "viewModel.username",
        date = "viewModel.curDate",
        profileOnClick = {
//            viewModel.profileNav(navController)
                         },
        list = listOf(NavigationOptions("Homepage"){}),// viewModel.getNavigationMenu(navController),
        showNav = false,
//            viewModel.showNavMenu,
        navOnClick = {
//            viewModel.showNavMenu()
                     },
        onDismissNav = {
//            viewModel.dismissNavMenu()
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                StudyCentreContent()
            }
        }

    }
}

@Composable
fun StudyCentreContent(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(30.dp)
    ){

        Text(
            text = "Home",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            avatarWindow(
                "Leane, Actions",
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.width(160.dp )
            ){
                button(
                    "Start Session",
                    1
                ) { }

                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Study Time",
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            smallProgressWidget(
                12,
                16,
                "All Tasks",
                12
            )

            smallProgressWidget(
                12,
                16,
                "Incomplete",
                10
            )

            smallProgressWidget(
                12,
                16,
                "Overdue",
                12
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        largeNavWidget(
            Icons.Filled.Star,
            "Study Centre",
            "Testingld;kjflkd;sjfld;asjflkd;sjf",
            Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        Column (
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(24.dp)
                )
                .height(300.dp)
                .padding(all = 20.dp)
        ){
            textField(
                "askjhdfkjdshf",
                "dskf;lsdkf;lskf",
                50
            )

            Spacer(modifier = Modifier.height(10.dp))

            textField(
                "askjhdfkjdshf",
                "dskf;lsdkf;lskf",
                50
            )

            Spacer(modifier = Modifier.height(10.dp))

            textField(
                "askjhdfkjdshf",
                "dskf;lsdkf;lskf",
                50
            )

            Spacer(modifier = Modifier.height(10.dp))

            textField(
                "askjhdfkjdshf",
                "dskf;lsdkf;lskf",
                50
            )

            Spacer(modifier = Modifier.height(10.dp))

            textField(
                "askjhdfkjdshf",
                "dskf;lsdkf;lskf",
                50
            )
        }

    }
}