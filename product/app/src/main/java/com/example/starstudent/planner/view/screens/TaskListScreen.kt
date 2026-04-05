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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
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
import com.example.starstudent.core.view.uiComponents.BannerFormatAndFloatingButtons
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import com.example.starstudent.core.view.uiComponents.simpleToggle
import com.example.starstudent.ui.theme.LocalExtendedLabelColours
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListScreen(navController: NavController){

    val viewModel = viewModel<TaskListViewModel>()
    val context = LocalContext.current

    BannerFormatAndFloatingButtons(
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
        },
        floatingAddOnClick = {viewModel.showNewOptionsDialog()}
    ) {
            padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                TaskListContent(viewModel, navController)
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

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListContent(viewModel : TaskListViewModel, navController : NavController){

    val colors = LocalExtendedLabelColours.current

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ){

        Text(
            text = "Task List",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        button(
            "Back to Planner",
            1
        ) { viewModel.navToPlanner(navController)}

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            if (viewModel.categoryList.isEmpty()){
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(0.4f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        text = "No categories found",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }else{
                viewModel.categoryList.forEach { category ->

                    val tasks = viewModel.taskList.filter { it.cateId == category.id }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(viewModel.getCategoryListHeight(tasks.size).dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary.copy(0.4f),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(10.dp)
                    ) {

                        Row(

                        ) {

                            Text(
                                text = category.cateLabel,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .background(
                                        color = viewModel.getCategoryColour(
                                            category.labelColour,
                                            colors
                                        ),
                                        shape = RoundedCornerShape(36.dp)
                                    )
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    )
                                    .clickable(onClick = {
                                        viewModel.editCategory(
                                            category.id,
                                            category.cateLabel,
                                            category.labelColour.lowercase()
                                        )
                                    }),
                                color = MaterialTheme.colorScheme.surface
                            )

                            IconButton(onClick = {viewModel.startNewTaskInCategory(category)}) {
                                Icon(
                                    modifier = Modifier
                                        .testTag("editLabelColourButton"),
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.primary)
                            }
                        }

                        if (tasks.isEmpty()) {
                            Text(
                                text = "No tasks found",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        } else {

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(1),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                val today = System.currentTimeMillis()

                                items(tasks) { task ->

                                    val isOverdue = task.dueDate >= today

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = if (isOverdue) {MaterialTheme.colorScheme.primary}
                                                        else {MaterialTheme.colorScheme.tertiary},
                                                shape = RoundedCornerShape(24.dp)
                                            )
                                            .padding(6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)

                                    ) {
                                        simpleToggle(
                                            0.7f,
                                            task.isComplete,
                                        ) {viewModel.viewModelScope.launch {
                                            viewModel.updateTaskCompletion(
                                                task.id,
                                                task.isComplete
                                        ) }}

                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .clickable(onClick = {
                                                    viewModel.editTask(
                                                        task.id,
                                                        category,
                                                        task.taskLabel,
                                                        task.dueDate,
                                                        task.isCritical,
                                                        task.isComplete,
                                                        task.completeDate
                                                    )
                                                })

                                        ) {

                                            Text(
                                                text = task.taskLabel,
                                                color = if (isOverdue){MaterialTheme.colorScheme.background}
                                                        else {MaterialTheme.colorScheme.primary},
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 16.sp,
                                                modifier = Modifier
                                                    .padding(2.dp)
                                            )

                                            Text(
                                                text = "Due: ${viewModel.formatDateTime(task.dueDate)}",
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
                                                tint = MaterialTheme.colorScheme.background,
                                                modifier = Modifier
                                                    .size(20.dp)
                                            )
                                        }

                                    }
                                }

                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                }
            }
        }

    }

    if(viewModel.showCategoryDialog){
        Dialog(onDismissRequest = {
            viewModel.hideCategoryDialog()
        }){
            AddCategoryDialog(viewModel)
        }
    }

    if(viewModel.showNewOptionsDialog){
        Dialog(onDismissRequest = {
            viewModel.hideNewOptionsDialog()
        }){
            NewOptionsDialog(viewModel)
        }
    }

    if(viewModel.showTaskDialog){
        Dialog(onDismissRequest = {
            viewModel.hideTaskDialog()
        }){
            TaskDialog(viewModel)
        }
    }

    viewModel.viewModelScope.launch {
        viewModel.getCategoryList()
        viewModel.getTaskList()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewOptionsDialog(viewModel: TaskListViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp)
        ) {
            Text(
                text = "Add New Category/Tasks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            button(
                "New Category",
                3
            ) { viewModel.startNewCategory()}

            if(!(viewModel.categoryList.isEmpty())) {
                button(
                    "New Task",
                    4
                ) { viewModel.startNewTask()}
            }
        }
    }
}

@Composable
fun AddCategoryDialog(viewModel: TaskListViewModel){

    val colors = LocalExtendedLabelColours.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(viewModel.getEditCategoryDialogHeight().dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = viewModel.getEditCategoryDialogLabel(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Preview",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = viewModel.categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .background(
                        viewModel.getCategoryColour(
                            viewModel.labelCategory.lowercase(),
                            colors
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Set Category Name",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            inputField(
                "Category Name",
                viewModel.categoryName,
                1
            ) {
                viewModel.updateCategoryName(it)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Set Label",
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
                    text = viewModel.labelCategory,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .background(
                            viewModel.getCategoryColour(
                                viewModel.labelCategory.lowercase(),
                                colors
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(10.dp)
                )

                IconButton(onClick = {viewModel.showCategoryColorList()}) {
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
                    onDismissRequest = {viewModel.showCategoryColorList()}
                ) {

                    viewModel.colorOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option.first.replaceFirstChar { it.uppercase() },
                                    color = MaterialTheme.colorScheme.surface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                )
                            },
                            modifier = Modifier
                                .background(viewModel.getCategoryColour(
                                    option.first.lowercase(),
                                    colors)),
                            onClick = {
                                viewModel.updateCategoryColour(option.first.lowercase())
                            }
                        )
                    }
                }
            }

            if(viewModel.editCategory) {
                Text(
                    text = "Hide Category",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                simpleToggle(
                    0.8f,
                    isChecked = viewModel.hideCategory
                ) { viewModel.updateHideCategory(!viewModel.hideCategory) }
            }

            button(
                viewModel.getCategoryButtonText(),
                2
            ) { viewModel.viewModelScope.launch {
                    viewModel.addOrUpdateCategory()
                }
            }

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(viewModel : TaskListViewModel){

    val colors = LocalExtendedLabelColours.current

    viewModel.viewModelScope.launch {
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

                IconButton(onClick = {viewModel.showCategoryColorList()}) {
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
                    onDismissRequest = {viewModel.showCategoryColorList()}
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
//                    .align(Alignment.CenterHorizontally)
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)

            ) {
                Text(
                    text = " Selected Due Date: \n" + viewModel.dueDateTask(),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                )

            }

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