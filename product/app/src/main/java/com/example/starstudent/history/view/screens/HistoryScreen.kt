package com.example.starstudent.history.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(navController: NavController){
    val viewModel = viewModel<HistoryViewModel>()
    val context = LocalContext.current

    BannerFormat(
        username = viewModel.username,
        date = viewModel.curDate,
        profileOnClick = {
            viewModel.profileNav(navController)
        },
        list = viewModel.getNavigationMenu(navController),
        showNav = viewModel.showNavMenu,
        navOnClick = {
            viewModel.showNavMenu()
        },
        onDismissNav = {
            viewModel.dismissNavMenu()
        }
    ) {
            padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                HistoryContent(viewModel, navController)
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

    //Error window pop-up
    LaunchedEffect(viewModel.errorWindow) {
        if (viewModel.errorWindow) {
            Toast.makeText(
                context,
                viewModel.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetErrorWindow()
        }
    }
}

@Composable
fun HistoryContent(viewModel: HistoryViewModel, navController: NavController){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(
            text = "History",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(6.dp)
        ){
            largeNavWidget(
                Icons.Filled.Create,
                "Study Sessions",
                "Study Session History",
                Modifier.fillMaxWidth()
            ) {
                viewModel.historyStudySessionNav(navController)
            }
        }
    }

}