package com.example.starstudent.planner.view.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
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
import com.example.starstudent.core.view.uiComponents.CalendarGrid
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.simpleToggle
import com.example.starstudent.ui.theme.LocalExtendedLabelColours
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//Planner banner and scrollable region for the planner content
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlannerScreen(navController: NavController){
    
    val viewModel = viewModel<PlannerViewModel>()
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
                PlannerContent(viewModel, navController)
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

//Content of the planner
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlannerContent(viewModel : PlannerViewModel, navController : NavController){

    val viewModel = viewModel

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ){
        Text(
            text = "Planner",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        val dates = remember(viewModel.calendarYear, viewModel.calendarMonth) {
            viewModel.generateMonthDates(viewModel.calendarYear, viewModel.calendarMonth)
        }

        Column(
            modifier = Modifier
                .height(400.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(all = 20.dp)
        ) {
            CalendarGrid(
                dates = dates,
                tasks = viewModel.taskList,
                onClick = { clickedDate ->
                    viewModel.setNewTaskDate(clickedDate)
                    viewModel.viewModelScope.launch {
                        viewModel.getSelectDayTaskList(
                            viewModel.getDayStart(clickedDate),
                            viewModel.getDayEnd(clickedDate)
                        )

                        viewModel.updateTaskListType(
                            viewModel.formatDateTime(
                                clickedDate.toInstant().toEpochMilli()
                            )
                        )
                        viewModel.showSeeTasksDialog(clickedDate.toInstant().toEpochMilli())
                    }
                },
                prevButtonClick = {viewModel.getPreviousMonth()},
                nextButtonClick = {viewModel.getNextMonth()},
                monthButtonClick = {viewModel.showSelectMonthDialog()}
            )
        }

        Column(
            modifier = Modifier
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(all = 20.dp)
        ) {
            button("All Tasks",
                1) { viewModel.navTaskList(navController) }
        }

        val overdueTasks = viewModel.getTotalAndCriticalTasksNumOverdue()

        largeNavWidget(
            Icons.Filled.Warning,
            "Overdue",
            "${overdueTasks.first} Tasks Remaining, ${overdueTasks.second} Critical",
            Modifier.fillMaxWidth()
        ){
            viewModel.viewModelScope.launch {
                viewModel.getSelectDayTaskList(
                    viewModel.getDayStart(0L),
                    viewModel.getDayEnd(System.currentTimeMillis())
                )

                viewModel.updateTaskListType("overdue")
                viewModel.showSeeTasksDialog(System.currentTimeMillis())
            }
        }

        val todayTasks = viewModel.getTotalAndCriticalTasksNumToday()

        largeNavWidget(
            Icons.Filled.DateRange,
            "Today",
            "${todayTasks.first} Tasks Remaining, ${todayTasks.second} Critical",
            Modifier.fillMaxWidth()
        ){
            viewModel.viewModelScope.launch {
                viewModel.getSelectDayTaskList(
                    viewModel.getDayStart(System.currentTimeMillis()),
                    viewModel.getDayEnd(System.currentTimeMillis())
                )

                viewModel.updateTaskListType("today")
                viewModel.showSeeTasksDialog(System.currentTimeMillis())
            }
        }

        val weekTasks = viewModel.getTotalAndCriticalTasksNumWeek()

        largeNavWidget(
            Icons.Filled.DateRange,
            "This Week",
            "${weekTasks.first} Tasks Remaining, ${weekTasks.second} Critical",
            Modifier.fillMaxWidth()
        ){ viewModel.viewModelScope.launch {
                viewModel.getSelectDayTaskList(
                    viewModel.getWeekStart(),
                    viewModel.getWeekEnd()
                )
                viewModel.updateTaskListType("week")
                viewModel.showSeeTasksDialog(System.currentTimeMillis())
            }
        }

        val monthTasks = viewModel.getTotalAndCriticalTasksNumMonth()

        largeNavWidget(
            Icons.Filled.DateRange,
            "This Month",
            "${monthTasks.first} Tasks Remaining, ${monthTasks.second} Critical",
            Modifier.fillMaxWidth()
        ){
            viewModel.viewModelScope.launch {
                viewModel.getSelectDayTaskList(
                    viewModel.getMonthStart(),
                    viewModel.getMonthEnd()
                )

                viewModel.updateTaskListType("month")
                viewModel.showSeeTasksDialog(System.currentTimeMillis())
            }
        }

    }

    LaunchedEffect(Unit) {
        viewModel.getTaskList()
        viewModel.getCategoryList()
    }

    if(viewModel.showSelectMonthDialog){
        Dialog(
            onDismissRequest = { viewModel.hideSelectMonthDialog() }
        ){
            SelectMonthDialog(viewModel)
        }
    }

    if(viewModel.showSeeTasksDialog){
        Dialog(
            onDismissRequest = { viewModel.hideSeeTasksDialog() }
        ){
            SeeTasksDialog(viewModel)
        }
    }

    if(viewModel.showAddOrEditTaskDialog){
        Dialog(onDismissRequest = {
            viewModel.hideAddOrEditTaskDialog()
        }){
            AddOrUpdateTaskDialog(viewModel)
        }
    }
}


//Dialog for the select month and year window
@Composable
fun SelectMonthDialog(viewModel: PlannerViewModel){
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

//Dialog for showing the selected tasks window
@Composable
fun SeeTasksDialog(viewModel: PlannerViewModel){

    val colors = LocalExtendedLabelColours.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(viewModel.getSelectedTaskListHeight().dp)
            .padding(10.dp)
    ) {

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(20.dp, 20.dp)
        ) {
            Text(
                text = viewModel.getTaskListTitle(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusable(true)
            )

            if(viewModel.selectedDayTaskList.isEmpty()){
                Text(
                    text = "No tasks found",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                button(
                    "Add New Task",
                    3
                ) {
                    if(viewModel.categoryList.isNotEmpty()){
                        viewModel.startNewTask()
                    }
                }
            }else{

                val today = System.currentTimeMillis()

                viewModel.selectedDayTaskList.forEach { task ->


                    val isOverdue = !((task.dueDate <= today) && (!task.isComplete))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isOverdue) {MaterialTheme.colorScheme.primary}
                                else {MaterialTheme.colorScheme.tertiary},
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(6.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        simpleToggle(
                            0.6f,
                            task.isComplete,
                        ) {

                            viewModel.viewModelScope.launch {
                                viewModel.updateTaskCompletion(
                                    task.id,
                                    task.isComplete,
                                    viewModel.getDayStart(task.dueDate),
                                    viewModel.getDayEnd(task.dueDate)
                                ) }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable(onClick = {
                                    if(viewModel.categoryList.isNotEmpty()){
                                        viewModel.editTask(
                                            task.id,
                                            viewModel.getCategoryWithId(task.cateId),
                                            task.taskLabel,
                                            task.dueDate,
                                            task.isCritical,
                                            task.isComplete,
                                            task.completeDate
                                        )
                                    }
                                })
                                .weight(1f)

                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = viewModel.getCategoryColour(task.taskCategoryLabelColour, colors),
                                        shape = RoundedCornerShape(24.dp),
                                    )
                                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                                    .width(200.dp)
                            ){
                                Text(
                                    text = task.taskCategoryLabel,
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 8.sp
                                )

                            }

                            Text(
                                text = task.taskLabel,
                                color = if (isOverdue){MaterialTheme.colorScheme.background}
                                else {MaterialTheme.colorScheme.primary},
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                            )

                            Text(
                                text = "Due: ${viewModel.formatDateTime(task.dueDate, "EEE d MMM yy")}",
                                color = if (isOverdue){MaterialTheme.colorScheme.background}
                                else {MaterialTheme.colorScheme.primary},
                                fontSize = 12.sp,
                                modifier = Modifier
                            )
                        }

                        if (task.isCritical) {
                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Critical Task",
                                tint = if (isOverdue){MaterialTheme.colorScheme.background}
                                else {MaterialTheme.colorScheme.primary},
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.2f)
                            )
                        }else{
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Critical Task",
                                tint = if (isOverdue){MaterialTheme.colorScheme.primary}
                                else {MaterialTheme.colorScheme.tertiary},
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.2f)
                            )
                        }
                    }

                }

                button(
                    "Add New Task",
                    3
                ) { viewModel.startNewTask()}
            }
        }
    }
}

//Dialog to add or update a task
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateTaskDialog(viewModel: PlannerViewModel){

    val colors = LocalExtendedLabelColours.current

    LaunchedEffect(Unit) {
        viewModel.getCategoryList()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(viewModel.getEditTaskDialogHeight().dp)
            .padding(10.dp)
    ) {
        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(20.dp, 10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = viewModel.getEditTaskDialogLabel(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "Category",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = viewModel.taskCategory.cateLabel,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .background(
                            viewModel.getCategoryColour(
                                viewModel.taskCategory.labelColour,
                                colors
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(10.dp)
                )

                IconButton(onClick = {
                    viewModel.showCategoryColorList()
                }) {
                    Icon(
                        modifier = Modifier
                            .testTag("editLabelColourButton"),
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.primary)
                }

                DropdownMenu(
                    expanded = viewModel.showCategoryColorList,
                    shape = RoundedCornerShape(20.dp),
                    onDismissRequest = {
                        viewModel.showCategoryColorList()
                    }
                ) {

                    viewModel.categoryList.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option.cateLabel,
                                    color = MaterialTheme.colorScheme.surface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                )
                            },
                            modifier = Modifier
                                .background(viewModel.getCategoryColour(
                                    option.labelColour,
                                    colors)),
                            onClick = {
                                viewModel.updateTaskCategory(option)
                            }
                        )
                    }
                }
            }

            Text(
                text = "Task Label",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            inputField(
                "",
                viewModel.updateTaskName,
                4
            ) { viewModel.updateTaskName(it) }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Due Date",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            DatePicker(
                state = viewModel.taskDatePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredSize(400.dp)
                    .scale(0.7f),
                showModeToggle = false,
                headline = null ,
                title = null
            )

            TimeInput(
                state = viewModel.taskTimePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .scale(1f)
                    .focusable(false)
            )

            Text(
                text = " Selected Due Date: \n" + viewModel.dueDateTask(),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
            )

            Text(
                text = "Critical Task",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            simpleToggle(
                0.8f,
                isChecked = viewModel.criticalTask
            ) {
                viewModel.updateCriticalTask()
            }

            if(viewModel.editTask){
                Text(
                    text = "Complete",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                simpleToggle(
                    0.8f,
                    isChecked = viewModel.completeTask
                ) {
                    viewModel.updateCompleteTask()
                }

                Text(
                    text = viewModel.getCompletionDate(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            button(
                viewModel.getTaskButtonText(),
                3
            ) {
                    viewModel.viewModelScope.launch {  viewModel.addOrUpdateTask()}
            }
        }
    }
}