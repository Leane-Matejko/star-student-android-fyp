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

    //Update the banner to a formatted string
    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    //Show or hide the year list options
    fun toggleYearList(){
        showYearList = !showYearList
    }

    //Show the navigation menu
    fun showNavMenu()
    {showNavMenu = true}

    //Hide the navigation menu
    fun dismissNavMenu()
    {showNavMenu = false }

    //Show the select month/year dialog
    fun showSelectMonthDialog()
    {showSelectMonthDialog = true}

    //Hide the select month/year dialog
    fun hideSelectMonthDialog()
    {showSelectMonthDialog = false }

    //Show the chosen task list dialog
    fun showSeeTasksDialog(
        newDate : Long
    )
    {
        selectedDay = newDate
        showSeeTasksDialog = true
    }

    //Hide the select month/year dialog
    fun hideSeeTasksDialog()
    {showSeeTasksDialog = false }

    //Set the new selected month
    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
    }

    //Set the new selected year
    fun setYear(newYear: Int){
        calendarYear = newYear
    }

    //Update the temporary new date for the task
    fun setNewTaskDate(
        newDate : Date
    ){
        selectedDate = newDate
    }

    //Update the task list type selected
    fun updateTaskListType(taskType : String){
        taskListType = taskType
    }

    //Return the long of the start of the local date
    fun LocalDate.getLocalDateStart() : Long{
        return this
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    //Return the long of the end of the local date
    fun LocalDate.getLocalDateEnd() : Long{
        return this
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    //Return the long of the start of the current week
    fun getWeekStart() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.MONDAY)
            .getLocalDateStart()
    }

    //Return the long of the end of the current week
    fun getWeekEnd() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.SUNDAY)
            .getLocalDateEnd()
    }

    //Return the long of the start of the current month
    fun getMonthStart() : Long{
        return LocalDate
            .now()
            .withDayOfMonth(1)
            .getLocalDateStart()
    }

    //Return the long of the end of the current month
    fun getMonthEnd() : Long{
        val currentDay = LocalDate.now()

        return currentDay
            .withDayOfMonth(currentDay.lengthOfMonth())
            .getLocalDateEnd()
    }

    //Return the string selected task group
    fun getTaskListTitle() : String{
        return when (taskListType){
            "overdue" -> categoryTaskFormatting.getTaskTitle("overdue")
            "today" -> categoryTaskFormatting.getTaskTitle("today")
            "week" -> categoryTaskFormatting.getTaskTitle("week")
            "month" -> categoryTaskFormatting.getTaskTitle("month")
            else -> categoryTaskFormatting.getTaskTitle(getSelectedDateFormatted())
        }
    }

    //Return the string of the selected day
    fun getSelectedDateFormatted() : String{
        return categoryTaskFormatting.formatDateTime(
            selectedDay,
            "EEE d MMM yyyy")
    }

    //Return the container length for the selected task list
    fun getSelectedTaskListHeight() : Int{
        return categoryTaskFormatting.getSelectedTaskListHeight(
            selectedDayTaskList.size
        )
    }

    //Return the string of formatted date of a long
    fun formatDateTime(date : Long) : String{
        return categoryTaskFormatting.formatDateTime(date)
    }

    //Return the string of formatted date of a long in a specific pattern
    fun formatDateTime(date : Long, pattern: String) : String{
        return categoryTaskFormatting.formatDateTime(date, pattern)
    }

    //Return the extended color option of a category
    fun getCategoryColour(color: String, colorList: ExtendedLabelColours): Color{
        return categoryTaskFormatting.getCategoryColour(color, colorList)
    }

    //Set the overall task list of all active tasks
    suspend fun getTaskList(){
        taskList = accessTasks.getTaskList(
            userId,
            0L
        )
    }

    //Update the selected month to the previous month
    fun getPreviousMonth(){
        if (calendarMonth == 0){
            calendarMonth = 11
            calendarYear -= 1
        }else{
            calendarMonth -= 1
        }
    }

    //Update the selected month to the next month
    fun getNextMonth(){
        if (calendarMonth == 11){
            calendarMonth = 0
            calendarYear += 1
        }else{
            calendarMonth += 1
        }
    }

    //Return the long of the start of a selected date
    fun getDayStart(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayStart(day)
    }

    //Return the long of the start of a selected long date
    fun getDayStart(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 0, 0)
    }

    //Return the long of the end of a selected date
    fun getDayEnd(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayEnd(day)
    }

    //Return the long of the end of a selected long date
    fun getDayEnd(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 23, 59)
    }

    //Return the height of the edit task dialog
    fun getEditTaskDialogHeight() : Int {
        return categoryTaskFormatting.getEditTaskDialogHeight(editTask)
    }

    //Return the label of the edit task dialog
    fun getEditTaskDialogLabel() : String {
        return categoryTaskFormatting.getEditTaskDialogLabel(editTask)
    }

    //Show or hide the color options for the categories
    fun showCategoryColorList(){
        showCategoryColorList = !showCategoryColorList
    }

    //Show the add or edit task dialog
    fun showAddOrEditTaskDialog(
        currentTask : Boolean
    ){
        editTask = currentTask
        showAddOrEditTaskDialog = true
    }

    //Hide the add or edit task dialog
    fun hideAddOrEditTaskDialog(){
        showAddOrEditTaskDialog = false
    }

    //Update the selected task category
    fun updateTaskCategory(newTaskCategory : TaskCategories){
        taskCategory = newTaskCategory
        acceptedTask = true
        showCategoryColorList()
    }

    //Refresh the list of categories from the user
    suspend fun getCategoryList(){
        categoryList = accessTaskCategories.getCategoryList(userId)
    }

    //Update the temporary label for the new task name
    fun updateTaskName(newName : String){
        if(newName.length <= 20){
            updateTaskName = newName
        }
    }

    //Update the temporary critical status for the new task
    fun updateCriticalTask(){
        criticalTask = !criticalTask
    }

    //Update the temporary completion status for the new task
    fun updateCompleteTask(){
        completeTask = !completeTask
        completionDate = System.currentTimeMillis()
    }

    //Return the string of the completion date, depending on the current completion status
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCompletionDate() : String{
        return if(!completeTask){
            "Not Complete yet"
        }else{
            "Completed: ${formatDateTime(completionDate)}"
        }
    }

    //Return the label for the add/edit task button
    fun getTaskButtonText() : String{
        return categoryTaskFormatting.getTaskButtonText(editTask)
    }

    //Refresh the temporary variable for a new or updated task
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

    //Refresh variables for a new task
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

    //Refresh temporary variables for an existing task
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

    //Add or update a task within the database
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

    //Insert a new task
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

    //Update an existing task
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

    //Return the category from its id
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

    //Update database to set inactive tasks to tasks complete before the recent monday
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

    //Return the number of all overdue tasks and all with a critical status
    fun getTotalAndCriticalTasksNumOverdue() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate <= System.currentTimeMillis() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
            )

    }

    //Return the number of all today's tasks and all with a critical status
    fun getTotalAndCriticalTasksNumToday() : Pair<Int, Int>{
        val today = System.currentTimeMillis()
        val totalTasks = taskList.filter { it.dueDate >= getDayStart(today) && it.dueDate <= getDayEnd(today) }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }

    //Return the number of all of this week's tasks and all with a critical status
    fun getTotalAndCriticalTasksNumWeek() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate >= getWeekStart() && it.dueDate <= getWeekEnd() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }

    //Return the number of all of this month's tasks and all with a critical status
    fun getTotalAndCriticalTasksNumMonth() : Pair<Int, Int>{
        val totalTasks = taskList.filter { it.dueDate >= getMonthStart() && it.dueDate <= getMonthEnd() }

        val criticalTasks = totalTasks.filter { it.isCritical }

        return Pair(
            totalTasks.size,
            criticalTasks.size
        )

    }


    //Update the selected tasks list to a specific group
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

    //Return the list of dates for a specific month and year
    fun generateMonthDates(year: Int, month: Int): List<Date> {
        return calendarFunctions.generateMonthDates(
            year,
            month
        )
    }

    //Navigates to the user's profile
    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    //Get the navigation options for the planner (all windows wo/ planner)
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

    //Navigates to the task list
    fun navTaskList(navController: NavController){
        Log.d("TEST", "Navigating to the Task List...")
        navigationFunctions.goToTaskList(navController)
    }

    //Closes the error window
    fun resetErrorWindow(){
        errorWindow = false
    }

}