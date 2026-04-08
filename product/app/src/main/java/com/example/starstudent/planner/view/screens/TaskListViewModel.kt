package com.example.starstudent.planner.view.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.AccessTaskCategories
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.TaskCategories
import com.example.starstudent.planner.data.entities.Tasks
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.planner.domain.TaskNotCompleteException
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.Instant
import java.time.ZoneId
import java.util.Locale
import kotlin.text.replaceFirstChar

class TaskListViewModel : ViewModel() {

    val accessTaskCategories = AccessTaskCategories()
    val accessTasks = AccessTasks()

    val categoryTaskFormatting = CategoryTaskFormatting()

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

    private var userId by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .id
    )

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
        listOf(
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

    var acceptedTask = false

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var showHiddenCategoryDialog by mutableStateOf(
        false
    )
        private set

    var hiddenCategoryList by mutableStateOf(
        listOf<TaskCategories>()
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

    //Functions for menus and Dialogs
    fun showCategoryDialog(editCate : Boolean){
        editCategory = editCate
        showCategoryDialog = true
    }
    fun hideCategoryDialog(){
        showCategoryDialog = false
    }

    fun showNewOptionsDialog(){
        showNewOptionsDialog = true
    }

    fun hideNewOptionsDialog(){
        showNewOptionsDialog = false
    }

    private fun showTaskDialog(currentTask: Boolean) {
        editTask = currentTask
        showTaskDialog = true
    }

    fun hideTaskDialog(){
        showTaskDialog = false
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false}

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(
        date: Long,
        hour: Int,
        minute: Int
    ) : Long{
        return categoryTaskFormatting.getDateTime(
            date,
            hour,
            minute
        )
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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

    fun updateTaskName(newName : String){
        if(newName.length <= 20){
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCompletionDate() : String{
        return if(!completeTask){
            "Not Complete yet"
        }else{
            "Completed: ${formatDateTime(completionDate)}"
        }
    }

    fun updateTaskCategory(newTaskCategory : TaskCategories){
        taskCategory = newTaskCategory
        acceptedTask = true
        showCategoryColorList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        acceptedTask = false
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
        categoryLabelColor = defaultLabelColor
        if(editCategory){
            hideCategory = defaultHide
        }
    }

    fun getCategoryListHeight(tasksNum : Int) : Int {
        return categoryTaskFormatting.getCategoryListHeight(tasksNum)
    }

    fun getEditCategoryDialogHeight() : Int {
        return categoryTaskFormatting.getEditCategoryDialogHeight(editCategory)
    }

    fun getEditTaskDialogHeight() : Int {
        return categoryTaskFormatting.getEditTaskDialogHeight(editTask)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime : Long) : String{
        return categoryTaskFormatting.formatDateTime(dateTime)
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
        return categoryTaskFormatting.getEditCategoryDialogLabel(editCategory)
    }

    fun getEditTaskDialogLabel() : String {
        return categoryTaskFormatting.getEditTaskDialogLabel(editTask)
    }

    fun getCategoryButtonText() : String{
        return categoryTaskFormatting.getCategoryButtonText(editCategory)
    }

    fun getTaskButtonText() : String{
        return categoryTaskFormatting.getTaskButtonText(editTask)
    }

    fun getCategoryColour(colour : String, colourList : ExtendedLabelColours) : Color {
        return categoryTaskFormatting.getCategoryColour(
            colour,
            colourList
        )
    }

    fun showHiddenCategoryDialog(){
        showHiddenCategoryDialog = true
    }

    fun hideHiddenCategoryDialog(){
        showHiddenCategoryDialog = false
    }

    suspend fun getCategoryList(){
        categoryList = accessTaskCategories.getCategoryList(userId)
    }

    suspend fun getTaskList(){
        taskList = accessTasks.getTaskList(
            userId,
            0L
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addOrUpdateTask(){
        if(editTask){
            updateExistingTask()
            getTaskList()
            hideTaskDialog()
        }else{
            try {
                if (!acceptedTask || ((taskDatePickerState.selectedDateMillis ?: 0L) == 0L)) {
                    throw TaskNotCompleteException()
                }
                addNewTask()
                getTaskList()
                hideTaskDialog()
            }catch (e: Exception){
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                resetErrorWindow()
                errorWindow = true
            }
        }
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
        accessTaskCategories.addNewCategory(
            userId,
            categoryName,
            categoryLabelColor
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun addNewTask() {
        accessTasks.addNewTask(
            cateId = taskCategory.id,
            taskLabel = updateTaskName,
            isCritical = criticalTask,
            dueDate = getDateTime(
                taskDatePickerState.selectedDateMillis ?: 0L,
                taskTimePickerState.hour,
                taskTimePickerState.minute
            ),
            isComplete = completeTask,
            completeDate = if (completeTask) {
                completionDate
            } else {
                0L
            },
            isActive = true
        )
    }

    fun resetErrorWindow(){
        errorWindow = false
    }

    suspend fun updateExistingCategory(){

        val hideStatus = !hideCategory

        accessTaskCategories.updateExistingCategory(
            categoryId,
            categoryName,
            categoryLabelColor,
            hideStatus
        )
        if(!hideStatus){
            hideTasks(categoryId)
        }else{
            showTasks(categoryId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun updateExistingTask(){

        accessTasks.updateExistingTask(
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
        accessTasks.updateTaskCompletion(
            taskId,
            taskComplete
        )
        getTaskList()
    }

    suspend fun getHiddenCategories(){
        hiddenCategoryList = accessTaskCategories.getHiddenCategories(
            userId
        )
    }

    suspend fun hideTasks(
        cateId : Int
    ){
        accessTasks.hideTasks(
            cateId
        )
    }

    suspend fun showTasks(
        cateId : Int
    ){
        accessTasks.showTasks(
            cateId
        )
    }

    suspend fun addHiddenCategory(
        cateId : Int
    ){
        accessTaskCategories.addHiddenCategory(
            cateId
        )
        showTasks(cateId)
        getHiddenCategories()
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
            NavigationOptions("History")
            {navigationFunctions.goToHistory(navController)},
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }
}