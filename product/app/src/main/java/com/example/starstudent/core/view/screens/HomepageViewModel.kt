package com.example.starstudent.core.view.screens


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.FormatHomepage
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.ui.theme.ExtendedLabelColours
import com.example.starstudent.userAccounts.data.entities.UserInfo
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.Locale

/* ViewModel for the homepage.
*/
class HomepageViewModel : ViewModel() {

    private val navigationFunctions = NavigationFunctions()

    private val formatHomepage = FormatHomepage()

    private val categoryTaskFormatting = CategoryTaskFormatting()

    private val accessTasks = AccessTasks()

    val userInfo = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance
        )
        .userInfoDao()

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username
    )
        private set

    var userId by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .id
    )
        private set

    var curUserInfo: List<UserInfo> by mutableStateOf(
        listOf(
            (UserInfo
                (
                CurrentApplication
                    .instance
                    .user.email
                    .getEmail(),
                "default",
                0,
                false
            )
                    )
        )
    )
        private set

    var curDate by mutableStateOf(
        LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    )
        private set

    var taskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    var overdueTaskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    var overdueToDo by mutableStateOf(
        0
    )
        private set

    var weekTaskList by mutableStateOf(
    listOf<TaskWithCategory>()
    )
    private set

    var weekToDo by mutableStateOf(
        0
    )
        private set

    var allTaskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    var allToDo by mutableStateOf(
        0
    )
        private set

    var showTasksDialog by mutableStateOf(
        false
    )
        private set

    var tasksType by mutableStateOf(
        "Overdue"
    )
        private set

    var currentTaskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    fun getNavigationMenu(navController: NavController): List<NavigationOptions> {

        return listOf(
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Planner")
            { navigationFunctions.goToPlanner(navController) },
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("History")
            { navigationFunctions.goToHistory(navController) },
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) },


            )
    }

    fun getUser() {
        viewModelScope.launch {
            curUserInfo = userInfo.getUserInfo(
                CurrentApplication
                    .instance
                    .user
                    .email
                    .getEmail()
            )!!

            if (curUserInfo.isNotEmpty()) {
                username = curUserInfo[0].username
            } else {
                username = CurrentApplication.instance.user.email.getEmail()
                curUserInfo = listOf(
                    (UserInfo
                        (
                        CurrentApplication
                            .instance
                            .user.email
                            .getEmail(),
                        "default",
                        0,
                        false
                    )
                            )
                )
            }
        }
    }

    fun showNavMenu() {
        showNavMenu = true
    }

    fun dismissNavMenu() {
        showNavMenu = false
    }

    fun updateTime() {
        curDate = LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    }

    fun resetTasksLists(){
        overdueTaskList = taskList.filter { it.dueDate <= getNowLong() && !it.isComplete }
        overdueToDo = overdueTaskList.filter { it.isComplete }.size
        weekTaskList = taskList.filter { it.dueDate >= getRecentMonday() && it.dueDate < getEndOfWeek()}
        weekToDo = weekTaskList.filter { it.isComplete }.size
        allTaskList = taskList
        allToDo = allTaskList.filter { it.isComplete }.size
        currentTaskList = when (tasksType){
            "overdue" -> overdueTaskList
            "weekly" -> weekTaskList
            else -> allTaskList
        }
    }

    fun getRecentMonday() : Long{
        return formatHomepage.getRecentMonday()
    }

    fun getEndOfWeek() : Long{
        return formatHomepage.getEndOfWeek()
    }

    fun getNowLong() : Long{
        return formatHomepage.getNowLong()
    }

    fun checkIsMonday() : Boolean{
        return formatHomepage.checkIsMonday()
    }

    fun showTasksDialog(
        tasksTypeInput : String
    ){
        tasksType = tasksTypeInput
        setCurrentTaskList()
        showTasksDialog = true
    }

    fun hideTasksDialog(){
        showTasksDialog = false
    }

    fun setCurrentTaskList(){
        currentTaskList = when (tasksType){
            "overdue" -> overdueTaskList
            "weekly" -> weekTaskList
            else -> allTaskList
        }
    }

    fun getCategoryColour(
        color : String,
        colorList : ExtendedLabelColours
    ): Color {
        return categoryTaskFormatting.getCategoryColour(
            color,
            colorList
        )
    }

    fun formatDateTime(
        date : Long,
        pattern : String
    ) : String{
        return categoryTaskFormatting.formatDateTime(
            date,
            pattern
        )
    }

    suspend fun updateTasksList(){
        accessTasks.hideCompletedTasks(
            userId,
            getRecentMonday())
    }

    suspend fun getAllCurrentTasks(){
        taskList = accessTasks.getAllCurrentTasks(userId)
        resetTasksLists()
    }

    suspend fun updateTaskCompletion(
        taskId: Int,
        taskComplete: Boolean
    ){
        accessTasks.updateTaskCompletion(
            taskId,
            taskComplete
        )
        getAllCurrentTasks()
        resetTasksLists()
    }




    fun profileNav(navController: NavController) {
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun navStudyCentre(navController: NavController) {
        Log.d("TEST", "Navigating to the Study Centre...")
        navigationFunctions.goToStudyCentre(navController)
    }

    fun navPlanner(navController: NavController) {
        Log.d("TEST", "Navigating to the Planner...")
        navigationFunctions.goToPlanner(navController)
    }

    fun navTaskList(navController: NavController) {
        Log.d("TEST", "Navigating to the Task List...")
        navigationFunctions.goToTaskList(navController)
    }

    fun navHistory(navController: NavController) {
        Log.d("TEST", "Navigating to the History...")
        navigationFunctions.goToHistory(navController)
    }
}