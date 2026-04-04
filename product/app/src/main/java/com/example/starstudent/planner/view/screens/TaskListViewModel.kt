package com.example.starstudent.planner.view.screens

import android.util.Log
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.entities.TaskCategories
import com.example.starstudent.planner.data.entities.Tasks
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.text.replaceFirstChar
import kotlin.time.Duration

class TaskListViewModel : ViewModel() {

    val taskCategoriesDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .taskCategoriesDao()

    val tasksDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .tasksDao()

    val bannerFunctions = BannerFunctions()

    val navigationFunctions = NavigationFunctions()

    @OptIn(ExperimentalMaterial3Api::class)
    var taskDatePickerState = DatePickerState(Locale.getDefault())

    @OptIn(ExperimentalMaterial3Api::class)
    var taskTimePickerState = TimePickerState(0,0,true)

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username)
        private set

    var curDate by mutableStateOf(
        bannerFunctions.updateTime()
    )
        private set

    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var categoryList by mutableStateOf(
        listOf<TaskCategories>(
            TaskCategories(
                id = 0,
                user = "",
                cateLabel = "Pick A Category",
                labelColour = "red",
                isActive = true
            )
        )
    )
        private set

    var taskList by mutableStateOf(
        listOf<Tasks>()
    )
        private set

    var showNewOptionsDialog by mutableStateOf(
        false
    )
        private set

    var showCategoryDialog by mutableStateOf(
        false
    )
        private set

    var labelCategory by mutableStateOf(
        "red".replaceFirstChar { it.uppercase() }
    )
        private set

    var taskCategory by mutableStateOf(
        categoryList.first()
    )
        private set

    var categoryId by mutableStateOf(
        0
    )
        private set

    var categoryName by mutableStateOf(
        "Default"
    )
        private set

    var editCategory by mutableStateOf(
        false
    )
        private set

    var editTask by mutableStateOf(
        false
    )
        private set

    var showCategoryColorList by mutableStateOf(
        false
    )
        private set

    var categoryLabelColor by mutableStateOf(
        "red"
    )
        private set

    var hideCategory by mutableStateOf(
        false
    )
        private set

    var showTaskDialog by mutableStateOf(
        false
    )
        private set

    var updateTaskName by mutableStateOf(
        "Task Name"
    )
        private set

    var criticalTask by mutableStateOf(
        false
    )
        private set

    var completeTask by mutableStateOf(
        false
    )
        private set

    var completionDate by mutableStateOf(
        0L
    )
        private set

    var taskId by mutableStateOf(
        0
    )
        private set

    var colorOptions = listOf(
        "red" to {colors :  ExtendedLabelColours -> colors.red},
        "orange" to {colors :  ExtendedLabelColours -> colors.orange},
        "yellow" to {colors :  ExtendedLabelColours -> colors.yellow},
        "green" to {colors :  ExtendedLabelColours -> colors.green},
        "blue" to {colors :  ExtendedLabelColours -> colors.blue},
        "navy" to {colors :  ExtendedLabelColours -> colors.navy},
        "purple" to {colors :  ExtendedLabelColours -> colors.purple},
        "pink" to {colors :  ExtendedLabelColours -> colors.pink}
    )

    @OptIn(ExperimentalMaterial3Api::class)
    fun dueDateTask() : String  {
        return formatDateTime(
            getDateTime(
                taskDatePickerState.selectedDateMillis ?: 0L,
                taskTimePickerState.hour,
                taskTimePickerState.minute
            )
        )
    }

    fun getDateTime(
        date: Long,
        hour: Int,
        minute: Int
    ) : Long{
        val zone = ZoneId.systemDefault()

        val checkedDate = if (date >= 0L) {date} else {System.currentTimeMillis()}

        val localDate = Instant.ofEpochMilli(checkedDate)
                .atZone(zone)
                .toLocalDate()

        val dateTime = LocalDateTime.of(
            localDate,
            LocalTime.of(hour, minute)
        )

        return dateTime
            .atZone(zone)
            .toInstant()
            .toEpochMilli()
    }

    fun hideCategoryDialog(){
        showCategoryDialog = false
    }

    fun showCategoryDialog(editCate : Boolean){
        editCategory = editCate
        showCategoryDialog = true
    }

    fun showNewOptionsDialog(){
        showNewOptionsDialog = true
    }

    fun hideNewOptionsDialog(){
        showNewOptionsDialog = false
    }

    fun startNewCategory(){
        hideNewOptionsDialog()
        resetCategoryVariables(
            1,
            "Default",
            "red",
            false
        )
        showCategoryDialog(false)
    }

    fun startNewTask(){
        hideNewOptionsDialog()
        resetTaskVariables(
            0,
            TaskCategories(
                0,
                "",
                "Pick A Category",
                "red",
                true
            ),
            "Task Label",
            System.currentTimeMillis(),
            defaultCriticalTask = false,
            defaultCompleteTask = false,
            0L
        )
        showTaskDialog(false)
    }

    fun startNewTaskInCategory(
        currentTaskCategory: TaskCategories
    ){
        hideNewOptionsDialog()
        resetTaskVariables(
            0,
            currentTaskCategory,
            "Task Label",
            System.currentTimeMillis(),
            defaultCriticalTask = false,
            defaultCompleteTask = false,
            0L
        )
        showTaskDialog(false)
    }

    fun editCategory(
        currentId : Int,
        currentName : String,
        currentLabel : String
    ){
        resetCategoryVariables(
            currentId,
            currentName,
            currentLabel,
            false
        )
        showCategoryDialog(true)
    }

    fun editTask(
        currentTask: Int,
        currentTaskCategory : TaskCategories,
        currentTaskLabel: String,
        currentDueDateTime: Long,
        currentCriticalTask: Boolean,
        currentCompleteTask: Boolean,
        currentCompletionDate: Long
    ){
        resetTaskVariables(
            currentTask,
            currentTaskCategory,
            currentTaskLabel,
            currentDueDateTime,
            currentCriticalTask,
            currentCompleteTask,
            currentCompletionDate
        )
        showTaskDialog(true)
    }

    private fun showTaskDialog(currentTask: Boolean) {
        editTask = currentTask
        showTaskDialog = true
    }

    fun updateTaskName(newName : String){
        if(newName.length <= 24){
            updateTaskName = newName
        }
    }

    fun updateCriticalTask(){
        criticalTask = !criticalTask
    }

    fun updateCompleteTask(){
        completeTask = !completeTask
        completionDate = System.currentTimeMillis()
    }

    fun getCompletionDate() : String{
        return if(!completeTask){
            "Not Complete yet"
        }else{
            "Completed: ${formatDateTime(completionDate)}"
        }
    }

    fun hideTaskDialog(){
        showTaskDialog = false
    }

    fun updateTaskCategory(newTaskCategory : TaskCategories){
        taskCategory = newTaskCategory
        showCategoryColorList()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun resetTaskVariables(
        defaultTaskId: Int,
        defaultCategory: TaskCategories,
        defaultLabel: String,
        defaultDueDateTime: Long,
        defaultCriticalTask: Boolean,
        defaultCompleteTask: Boolean,
        defaultCompletionDate : Long
    ){
        taskId = defaultTaskId
        taskCategory = defaultCategory
        updateTaskName = defaultLabel
        taskDatePickerState = DatePickerState(
            locale = Locale.getDefault(),
            initialSelectedDateMillis = defaultDueDateTime
        )

        val dateTime = Instant
            .ofEpochMilli(
                defaultDueDateTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        taskTimePickerState = TimePickerState(
            dateTime.hour, dateTime.minute, true
        )

        criticalTask = defaultCriticalTask

        completeTask = defaultCompleteTask

        completionDate = defaultCompletionDate
    }

    fun resetCategoryVariables(
        defaultId : Int,
        defaultName : String,
        defaultLabelColor : String,
        defaultHide : Boolean

    ){
        categoryId = defaultId
        categoryName = defaultName
        labelCategory = defaultLabelColor.replaceFirstChar { it.uppercase() }
        if(editCategory){
            hideCategory = defaultHide
        }
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun getScrollableHeight() : Int{
        return if(categoryList.isEmpty()){
            120
        }else{
            if(taskList.isEmpty()){
                120 * categoryList.size
            }else{
                ((categoryList.size * 100) + (taskList.size * 70))
            }
        }
    }

    fun getCategoryListHeight(tasksNum : Int) : Int {
        if(tasksNum == 0){
            return 120
        }
        return (100 + (tasksNum * 70))
    }

    fun getEditCategoryDialogHeight() : Int {
        if(editCategory){
            return 620
        }
        return 520
    }

    fun getEditTaskDialogHeight() : Int {
        if(editTask){
            return 700
        }
        return 600
    }

    fun formatDateTime(dateTime : Long) : String{
        return Instant.ofEpochMilli(dateTime)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMM yyyy, HH:mm"
                    )
            )
    }

    fun showCategoryColorList() {
        showCategoryColorList = !showCategoryColorList
    }

    fun setCategoryLabelColour(colorString : String){
        labelCategory = colorString.replaceFirstChar { it.uppercase() }
        categoryLabelColor = colorString

    }

    fun updateCategoryColour(newColor : String){
        setCategoryLabelColour(newColor)
        showCategoryColorList()
    }

    fun updateCategoryName(newName : String){
        if (newName.length <= 20) {
            categoryName = newName
        }
    }

    fun updateHideCategory(newHideStatus : Boolean){
        hideCategory = newHideStatus
    }

    fun getEditCategoryDialogLabel() : String {
        if(editCategory){
            return "Edit Category"
        }
        return "Add New Category"
    }

    fun getEditTaskDialogLabel() : String {
        if(editTask){
            return "Edit Task"
        }
        return "Add New Task"
    }

    fun getCategoryButtonText() : String{
        if (editCategory){
            return "Save Changes"
        }
        return "Save"
    }

    fun getTaskButtonText() : String{
        if (editTask){
            return "Save Changes"
        }
        return "Save"
    }

    fun getCategoryColour(colour : String, colourList : ExtendedLabelColours) : Color {
        return when (colour) {
            "red" -> colourList.red
            "orange" -> colourList.orange
            "yellow" -> colourList.yellow
            "green" -> colourList.green
            "blue" -> colourList.blue
            "navy" -> colourList.navy
            "purple" -> colourList.purple
            "pink" -> colourList.pink
            else -> colourList.red
        }
    }

    suspend fun getCategoryList(){
        categoryList = taskCategoriesDAO.getAllCurrentCategories(username)
    }

    fun getRecentMonday(): Long{
        val today = LocalDate.now()

        val recentMonday = today.with(java.time.DayOfWeek.MONDAY)

        return recentMonday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    suspend fun getTaskList(){
        taskList = tasksDAO
            .getAllCurrentTasksWeek(
                username,
//                getRecentMonday()
                0L
            )
    }

    suspend fun addOrUpdateTask(){
        if(editTask){
            Log.d("TASK ACTION", "Updating existing task")
            updateExistingTask()
        }else{
            Log.d("TASK ACTION", "Creating a new task")
            addNewTask()
        }
        getTaskList()
        hideTaskDialog()
    }

    suspend fun addOrUpdateCategory(){
        if(editCategory){
            updateExistingCategory()
        }else{
            addNewCategory()
        }
        getCategoryList()
        hideCategoryDialog()
    }

    suspend fun addNewCategory(){
        taskCategoriesDAO.addNewCategory(
            TaskCategories(
                user = username,
                cateLabel = categoryName,
                labelColour = categoryLabelColor,
                isActive = true
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun addNewTask(){
        tasksDAO.addNewTask(
            Tasks(
                cateId = taskCategory.id,
                taskLabel = updateTaskName,
                isCritical = criticalTask,
                dueDate = getDateTime(
                    taskDatePickerState.selectedDateMillis ?: 0L,
                    taskTimePickerState.hour,
                    taskTimePickerState.minute
                ),
                isComplete = completeTask,
                completeDate = if(completeTask){completionDate}else{0L},
                isActive = true
            )
        )
    }

    suspend fun updateExistingCategory(){

        taskCategoriesDAO.updateCategory(
            id = categoryId,
            cateLabel = categoryName,
            labelColour = categoryLabelColor,
            isActive = !hideCategory //Reversing the hide status as is stored as the category's active status
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun updateExistingTask(){
        tasksDAO.updateTask(
            id = taskId,
            cateId = taskCategory.id,
            taskLabel = updateTaskName,
            isCritical = criticalTask,
            dueDate = getDateTime(
                taskDatePickerState.selectedDateMillis ?: 0L,
                taskTimePickerState.hour,
                taskTimePickerState.minute
            ),
            isComplete = completeTask,
            completeDate = if(completeTask){completionDate}else{0L}
        )
    }

    suspend fun updateTaskCompletion(
        taskId: Int,
        taskComplete: Boolean
    ){
        if(taskComplete){
            tasksDAO.updateTaskCompletion(
                taskId,
                false,
                0L
            )
        }else {
            tasksDAO.updateTaskCompletion(
                taskId,
                true,
                System.currentTimeMillis()
            )
        }
        getTaskList()
    }

    fun profileNav(navController: NavController){
        Log.d("NAVIGATION", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun navToPlanner(navController: NavController){
        Log.d("NAVIGATION", "Navigating to the planner...")
        navigationFunctions.goToPlanner(navController)
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
            { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Planner")
            { navigationFunctions.goToPlanner(navController) },
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }
}