package com.example.starstudent.planner.view.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.domain.CalendarFunctions
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.planner.domain.TaskNotCompleteException
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PlannerViewModel : ViewModel(){
    val calendarFunctions = CalendarFunctions()
    val accessTaskCategories = AccessTaskCategories()
    val categoryTaskFormatting = CategoryTaskFormatting()
    val accessTasks = AccessTasks()
    val bannerFunctions = BannerFunctions()
    val navigationFunctions = NavigationFunctions()
    val monthList = calendarFunctions.getMonthList()
    val yearList = calendarFunctions.getYearList()

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var showSeeTasksDialog by mutableStateOf(
        false
    )
        private set

    var showYearList by mutableStateOf(
        false
    )
        private set

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

    var taskList by mutableStateOf(
        listOf<Tasks>()
    )
        private set

    var calendarMonth by mutableIntStateOf(
        Calendar.getInstance().get(Calendar.MONTH)
    )
        private set

    var calendarYear by mutableIntStateOf(
        Calendar.getInstance().get(Calendar.YEAR)
    )
        private set

    var selectedDay by mutableStateOf(
        System.currentTimeMillis()
    )
        private set

    var selectedDate by mutableStateOf(
        Date()
    )
        private set

    var selectedDayTaskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    var taskListType by mutableStateOf(
        ""
    )
        private set

    var showSelectMonthDialog by mutableStateOf(
        false
    )
        private set

    var showAddOrEditTaskDialog by mutableStateOf(
        false
    )
        private set

    var editTask by mutableStateOf(
        false
    )
        private set

    var taskCategory by mutableStateOf(
        TaskCategories(
            user = userId,
            cateLabel = "Pick a Category",
            labelColour = "red",
            isActive = true
        )
    )
        private set
    var showCategoryColorList by mutableStateOf(
        false
    )
        private set

    var categoryList by mutableStateOf(
        listOf<TaskCategories>()
    )

    var updateTaskName by mutableStateOf(
        "Task Name"
    )

    @OptIn(ExperimentalMaterial3Api::class)
    var taskDatePickerState = DatePickerState(Locale.getDefault())

    @OptIn(ExperimentalMaterial3Api::class)
    var taskTimePickerState = TimePickerState(0,0,true)

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

    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    fun toggleYearList(){
        showYearList = !showYearList
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun showSelectMonthDialog()
    {showSelectMonthDialog = true}

    fun hideSelectMonthDialog()
    {showSelectMonthDialog = false }

    fun showSeeTasksDialog(
        newDate : Long
    )
    {
        selectedDay = newDate
        showSeeTasksDialog = true
    }

    fun hideSeeTasksDialog()
    {showSeeTasksDialog = false }

    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
    }

    fun setYear(newYear: Int){
        calendarYear = newYear
    }

    fun setNewTaskDate(
        newDate : Date
    ){
        selectedDate = newDate
    }

    fun updateTaskListType(taskType : String){
        taskListType = taskType
    }

    fun LocalDate.getLocalDateStart() : Long{
        return this
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun LocalDate.getLocalDateEnd() : Long{
        return this
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getWeekStart() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.MONDAY)
            .getLocalDateStart()
    }

    fun getWeekEnd() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.SUNDAY)
            .getLocalDateEnd()
    }

    fun getMonthStart() : Long{
        return LocalDate
            .now()
            .withDayOfMonth(1)
            .getLocalDateStart()
    }

    fun getMonthEnd() : Long{
        val currentDay = LocalDate.now()

        return currentDay
            .withDayOfMonth(currentDay.lengthOfMonth())
            .getLocalDateEnd()
    }

    fun getTaskListTitle() : String{
        return when (taskListType){
            "overdue" -> categoryTaskFormatting.getTaskTitle("overdue")
            "today" -> categoryTaskFormatting.getTaskTitle("today")
            "week" -> categoryTaskFormatting.getTaskTitle("week")
            "month" -> categoryTaskFormatting.getTaskTitle("month")
            else -> categoryTaskFormatting.getTaskTitle(getSelectedDateFormatted())
        }
    }

    fun getSelectedDateFormatted() : String{
        return categoryTaskFormatting.formatDateTime(
            selectedDay,
            "EEE d MMM yyyy")
    }

    fun getSelectedTaskListHeight() : Int{
        return categoryTaskFormatting.getSelectedTaskListHeight(
            selectedDayTaskList.size
        )
    }

    fun formatDateTime(date : Long) : String{
        return categoryTaskFormatting.formatDateTime(date)
    }

    fun formatDateTime(date : Long, pattern: String) : String{
        return categoryTaskFormatting.formatDateTime(date, pattern)
    }

    fun getCategoryColour(color: String, colorList: ExtendedLabelColours): Color{
        return categoryTaskFormatting.getCategoryColour(color, colorList)
    }

    suspend fun getTaskList(){
        taskList = accessTasks.getTaskList(
            userId,
            0L
        )
    }

    fun getPreviousMonth(){
        if (calendarMonth == 0){
            calendarMonth = 11
            calendarYear -= 1
        }else{
            calendarMonth -= 1
        }
    }

    fun getNextMonth(){
        if (calendarMonth == 11){
            calendarMonth = 0
            calendarYear += 1
        }else{
            calendarMonth += 1
        }
    }

    fun getDayStart(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayStart(day)
    }

    fun getDayStart(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 0, 0)
    }

    fun getDayEnd(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayEnd(day)
    }

    fun getDayEnd(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 23, 59)
    }

    fun getEditTaskDialogHeight() : Int {
        return categoryTaskFormatting.getEditTaskDialogHeight(editTask)
    }

    fun getEditTaskDialogLabel() : String {
        return categoryTaskFormatting.getEditTaskDialogLabel(editTask)
    }

    fun showCategoryColorList(){
        showCategoryColorList = !showCategoryColorList
    }

    fun showAddOrEditTaskDialog(
        currentTask : Boolean
    ){
        editTask = currentTask
        showAddOrEditTaskDialog = true
    }

    fun hideAddOrEditTaskDialog(){
        showAddOrEditTaskDialog = false
    }

    fun updateTaskCategory(newTaskCategory : TaskCategories){
        taskCategory = newTaskCategory
        acceptedTask = true
        showCategoryColorList()
    }

    suspend fun getCategoryList(){
        categoryList = accessTaskCategories.getCategoryList(userId)
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

    fun getTaskButtonText() : String{
        return categoryTaskFormatting.getTaskButtonText(editTask)
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

    fun startNewTask(){
        hideSeeTasksDialog()
        resetTaskVariables(
            0,
            TaskCategories(
                user = userId,
                cateLabel = "Pick a Category",
                labelColour = "red",
                isActive = true
            ),
            defaultLabel = "Task Label",
            defaultDueDateTime = System.currentTimeMillis(),
            defaultCriticalTask = false,
            defaultCompleteTask = false,
            defaultCompletionDate = 0L
        )
        showAddOrEditTaskDialog(false)
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
        hideSeeTasksDialog()
        resetTaskVariables(
            currentTask,
            currentTaskCategory,
            currentTaskLabel,
            currentDueDateTime,
            currentCriticalTask,
            currentCompleteTask,
            currentCompletionDate
        )
        showAddOrEditTaskDialog(true)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addOrUpdateTask(){
        if(editTask){
            updateExistingTask()
            getTaskList()
            hideAddOrEditTaskDialog()
        }else{
            try {
                if (!acceptedTask || ((taskDatePickerState.selectedDateMillis ?: 0L) == 0L)) {
                    throw TaskNotCompleteException()
                }
                addNewTask()
                getTaskList()
                hideAddOrEditTaskDialog()
            }catch (e: Exception){
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                resetErrorWindow()
                errorWindow = true
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun addNewTask(){
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

    fun getCategoryWithId(
        cateId : Int
    )  : TaskCategories{
        return categoryList.find{it.id == cateId} ?:
        TaskCategories(
            user = userId,
            cateLabel = "Pick a Category",
            labelColour = "red",
            isActive = true
        )
    }

    suspend fun updateTaskCompletion(
        taskId: Int,
        taskComplete: Boolean,
        intervalStart: Long,
        intervalEnd: Long
    ){
        accessTasks.updateTaskCompletion(
            taskId,
            taskComplete
        )
        getTaskList()
        getSelectDayTaskList(intervalStart, intervalEnd)
    }

    fun getTotalAndCriticalTasksNumOverdue() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate <= System.currentTimeMillis() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
            )

    }

    fun getTotalAndCriticalTasksNumToday() : Pair<Int, Int>{
        val today = System.currentTimeMillis()
        val totalTasks = taskList.filter { it.dueDate >= getDayStart(today) && it.dueDate <= getDayEnd(today) }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }

    fun getTotalAndCriticalTasksNumWeek() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate >= getWeekStart() && it.dueDate <= getWeekEnd() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }

    fun getTotalAndCriticalTasksNumMonth() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate >= getMonthStart() && it.dueDate <= getMonthEnd() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }

    suspend fun getSelectDayTaskList(
        intervalStart: Long,
        intervalEnd: Long
    ){
        selectedDayTaskList = accessTasks.getTasksWithCategories(
            userId,
            intervalStart,
            intervalEnd
        )
    }

    fun generateMonthDates(year: Int, month: Int): List<Date> {
        return calendarFunctions.generateMonthDates(
            year,
            month
        )
    }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
            { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("History")
            {navigationFunctions.goToHistory(navController)},
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }

    fun navTaskList(navController: NavController){
        Log.d("TEST", "Navigating to the Task List...")
        navigationFunctions.goToTaskList(navController)
    }

    fun resetErrorWindow(){
        errorWindow = false
    }

}