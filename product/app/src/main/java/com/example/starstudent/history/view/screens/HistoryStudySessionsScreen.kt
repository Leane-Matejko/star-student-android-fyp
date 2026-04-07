package com.example.starstudent.history.view.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.button
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun HistoryStudySessionsScreen(navController: NavController) {
    val viewModel = viewModel<HistoryStudySessionsViewModel>()

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
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item {
                HistoryStudySessionsContent(viewModel, navController)
            }
        }
    }
}

@Composable
fun HistoryStudySessionsContent(
    viewModel: HistoryStudySessionsViewModel,
    navController: NavController
){
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

        Text(
            text = "Study Sessions",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )

        button(
            "Back to History",
            1
        ) { viewModel.historyNav(navController) }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            button(
                "Select Month/Year",
                2
            ){ viewModel.showSelectMonthDialog()}

            Text(
                text = viewModel.getFormattedDateMonth(viewModel.monthRange.first),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            val monthRange = viewModel.getMonthStartEnd()

            if(viewModel.filteredSessionList.isEmpty()){
                Text(
                    text = "No sessions found",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))
            }else{
                val sessionDays : Set<LocalDate> = viewModel.filteredSessionList.map{
                    Instant.ofEpochMilli(it.startTime)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }.toSet()

                sessionDays.forEach { day ->
                    Text(
                        text = viewModel.getFormattedDate(day),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    val dayRange = viewModel.getDayStartEnd(day)
                    val filteredSessions = viewModel.sessionDurationList.filter { it.startTime in dayRange.first..dayRange.second }

                    filteredSessions.forEach { session ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(start = 10.dp)
                        ){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(10.dp)
                            ) {


                                Text(
                                    text = "Start Time : ${
                                        viewModel.getFormattedDate(
                                            session.startTime,
                                            "EEE d MMM, HH:mm"
                                        )
                                    }",
                                    color = MaterialTheme.colorScheme.background,
                                    fontSize = 16.sp
                                )

                                Text(
                                    text = "Duration: ${
                                        viewModel.getFormattedDuration(
                                            session.duration.toInt()
                                        )
                                    }",
                                    color = MaterialTheme.colorScheme.background,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(onClick = {
                                viewModel.showDeleteSessionDialog(session)
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .testTag("editLabelColourButton")
                                        .size(16.dp),
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.background)
                            }

                        }
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    if(viewModel.showSelectMonthDialog){
        Dialog(
            onDismissRequest = { viewModel.hideSelectMonthDialog() }
        ){
            SelectMonthDialog(viewModel)
        }
    }

    if(viewModel.showDeleteSessionDialog){
        Dialog(
            onDismissRequest = { viewModel.hideDeleteSessionDialog() }
        ){
            DeleteSessionDialog(viewModel)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getStudySessionList()
        Log.d("SESSION LIST", viewModel.sessionList.toString())
    }
}

@Composable
fun SelectMonthDialog(viewModel: HistoryStudySessionsViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {

            Text(
                text = "Select Month/Year",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "Select Year",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp)
                    .clickable(onClick = {viewModel.toggleYearList()})
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.calendarYear.toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.background
                    )

                    Icon(
                        modifier = Modifier
                            .testTag("editLabelColourButton"),
                        imageVector = if (viewModel.showYearList) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                val scrollState = rememberScrollState()

                DropdownMenu(
                    expanded = viewModel.showYearList,
                    onDismissRequest = { viewModel.toggleYearList() },
                    scrollState = scrollState,
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                ) {
                    viewModel.yearList.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(text = year.toString()) },
                            onClick = {
                                viewModel.setYear(year)
                            }
                        )
                    }
                }
            }

            Text(
                text = "Select Month",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(4.dp)
            ){
                items(viewModel.monthList.size){
                        month ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(4.dp)
                            .clickable(onClick = { viewModel.setMonth(month) })
                    ){
                        Text(
                            text = viewModel.monthList[month],
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteSessionDialog(viewModel: HistoryStudySessionsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {

            Text(
                text = "Delete session?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(start = 10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                ) {

                    Text(
                        text = "Start Time : ${
                            viewModel.getFormattedDate(
                                viewModel.deleteSession.startTime,
                                "EEE d MMM, HH:mm"
                            )
                        }",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Duration: ${
                            viewModel.getFormattedDuration(
                                viewModel.deleteSession.duration.toInt()
                            )
                        }",
                        color = MaterialTheme.colorScheme.background,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                }

            }

            Icon(
                Icons.Filled.Warning,
                contentDescription = "Delete Warning",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.secondary,
            )

            Text(
                text = "Deleting a session is a permanent action",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            button(
                "Delete",
                5
            ) {
                viewModel.viewModelScope.launch {
                    viewModel.deleteStudySession()
                }
            }
        }
    }
}