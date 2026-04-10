package com.example.starstudent.core.view.screens


import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
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
import com.example.starstudent.core.domain.Themes
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.customisation.data.entities.HomepageSettings
import com.example.starstudent.customisation.domain.AccessHomepageSettings
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.studySpaces.data.AccessStudySessions
import com.example.starstudent.studySpaces.data.entities.StudySessionDuration
import com.example.starstudent.ui.theme.ExtendedLabelColours
import com.example.starstudent.userAccounts.data.AccessUserTheme
import com.example.starstudent.userAccounts.data.entities.UserInfo
import com.example.starstudent.userAccounts.domain.FormatProfile
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/* ViewModel for the homepage.
*/
class HomepageViewModel : ViewModel() {

    private val navigationFunctions = NavigationFunctions()
    private val formatHomepage = FormatHomepage()
    private val categoryTaskFormatting = CategoryTaskFormatting()
    private val accessTasks = AccessTasks()
    private val accessStudySessions = AccessStudySessions()
    private val accessUserTheme = AccessUserTheme()
    private val accessHomepageSettings = AccessHomepageSettings()

    val formatProfile = FormatProfile()

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

    var totalSessionList by mutableStateOf(
        listOf<StudySessionDuration>()
    )
        private set

    var previousWeekSessionsList by mutableStateOf(
        listOf<StudySessionDuration>()
    )
        private set

    var currentWeekSessionsList by mutableStateOf(
        listOf<StudySessionDuration>()
    )
        private set

    var studyComparisonIcon by mutableStateOf(
        Icons.Rounded.Menu
    )
        private set

    var showHomepageCustomisationDialog by mutableStateOf(
        false
    )
        private set

    var homepageSettings by mutableStateOf(
        HomepageSettings(
            userId,
            avatarWindow = true,
            studyProgress = true,
            sleepProgress = true,
            overdueTasks = true,
            weeklyTasks = true,
            allTasks = true,
            studyCentreNav = true,
            plannerNav = true,
            taskListNav = true,
            historyNav = true,
            profileNav = true,
        )
    )
        private set

    //Get the navigation options for the homepage (all windows wo/ homepage)
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

    //Get the user's information and set a default if the user does not exist
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

    //Show the navigation menu
    fun showNavMenu() {
        showNavMenu = true
    }

    //Hide the navigation menu
    fun dismissNavMenu() {
        showNavMenu = false
    }

    //Update the banner to a formatted string
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

    //Refresh the task lists and completion number into their overdue, weekly or all category
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

    //Return the long for the most recent monday
    fun getRecentMonday() : Long{
        return formatHomepage.getRecentMonday()
    }

    //Return the end of the current week
    fun getEndOfWeek() : Long{
        return formatHomepage.getEndOfWeek()
    }

    //Return the long for the current time and date
    fun getNowLong() : Long{
        return formatHomepage.getNowLong()
    }

    //Return if it is currently monday
    fun checkIsMonday() : Boolean{
        return formatHomepage.checkIsMonday()
    }

    //Show tasks dialog with specific category type
    fun showTasksDialog(
        tasksTypeInput : String
    ){
        tasksType = tasksTypeInput
        setCurrentTaskList()
        showTasksDialog = true
    }

    //Hide tasks dialog
    fun hideTasksDialog(){
        showTasksDialog = false
    }

    //Set the type of task list viewed on the show tasks dialog
    fun setCurrentTaskList(){
        currentTaskList = when (tasksType){
            "overdue" -> overdueTaskList
            "weekly" -> weekTaskList
            else -> allTaskList
        }
    }

    //Get the color from the extended color palette
    fun getCategoryColour(
        color : String,
        colorList : ExtendedLabelColours
    ): Color {
        return categoryTaskFormatting.getCategoryColour(
            color,
            colorList
        )
    }

    //Return a long date into a formatted string using a specific pattern
    fun formatDateTime(
        date : Long,
        pattern : String
    ) : String{
        return categoryTaskFormatting.formatDateTime(
            date,
            pattern
        )
    }

    // Return the long for the start and end of the current week
    fun getCurrentWeek() : Pair<Long, Long>{
        return Pair(formatHomepage.getRecentMonday(),
            formatHomepage.getEndOfWeek()
        )
    }

    // Return the long for the start and end of the previous week
    fun getPreviousWeek() : Pair<Long, Long>{
        return Pair(formatHomepage.getPreviousMonday(),
            formatHomepage.getPreviousEndOfWeek()
        )
    }

    //Get and filter completed study sessions from this week and the previous week
    fun resetStudySession(){
        val previousWeek = getPreviousWeek()
        val currentWeek = getCurrentWeek()

        previousWeekSessionsList = totalSessionList.filter {
            it.startTime >= previousWeek.first &&
                    it.startTime <= previousWeek.second }

        currentWeekSessionsList = totalSessionList.filter {
            it.startTime >= currentWeek.first &&
                    it.startTime <= currentWeek.second }
    }

    //Compare this week and the previous week's overall study time
    fun getStudyComparison(){
        studyComparisonIcon = formatHomepage.compareStudySessions(
            previousWeekSessionsList,
            currentWeekSessionsList
        )
    }

    //Show the homepage customisation window
    fun showHomepageCustomisationDialog(){
        showHomepageCustomisationDialog = true
    }

    //Hide the homepage customisation window
    fun hideHomepageCustomisationDialog(){
        showHomepageCustomisationDialog = false
    }

    //Get the sessions with their start times and active duration
    suspend fun getSessionList(){
        totalSessionList = accessStudySessions.getRecentSessionsWithDuration(userId)
        resetStudySession()
        getStudyComparison()
    }

    //Get the enumerated value of the saved theme
    fun getThemeColor(
        currentTheme: String
    ) : Themes{
        return formatProfile.getThemeFromString(currentTheme)
    }

    //Return the user's theme saved to the database
    suspend fun getCurrentTheme() : String{
        return accessUserTheme.getUserTheme(
            userId
        )
    }

    //Update the user's theme to the saved value
    suspend fun updateTheme(
        applicationViewModel: ApplicationViewModel
    ) {
        val currentTheme = getCurrentTheme()
        val theme = getThemeColor(currentTheme)
        applicationViewModel.setTheme(theme)
    }

    //Updates tasks completed before the most recent monday to inactive
    suspend fun updateTasksList(){
        accessTasks.hideCompletedTasks(
            userId,
            getRecentMonday())
    }

    //Gets all current tasks and refreshes the task list options
    suspend fun getAllCurrentTasks(){
        taskList = accessTasks.getAllCurrentTasks(userId)
        resetTasksLists()
    }

    //Updates the complete status and completion date of a task
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

    //Get the current homepage customisation settings
    suspend fun getHomepageSettings(){
        homepageSettings = accessHomepageSettings.getHomepageSettings(userId)
    }

    //Updates the visibility of the avatar window widget
    suspend fun updateAvatarWindow(){
        accessHomepageSettings.updateAvatarWindow(
            userId,
            !homepageSettings.avatarWindow
        )
        getHomepageSettings()
    }

    //Updates the visibility of the study progress widget
    suspend fun updateStudyProgress(){
        accessHomepageSettings.updateStudyProgress(
            userId,
            !homepageSettings.studyProgress
        )
        getHomepageSettings()
    }

    //Updates the visibility of the sleep progress widget
    suspend fun updateSleepProgress(){
        accessHomepageSettings.updateSleepProgress(
            userId,
            !homepageSettings.sleepProgress
        )
        getHomepageSettings()
    }

    //Updates the visibility of the overdue tasks widget
    suspend fun updateOverdueTasks(){
        accessHomepageSettings.updateOverdueTasks(
            userId,
            !homepageSettings.overdueTasks
        )
        getHomepageSettings()
    }

    //Updates the visibility of the weekly tasks widget
    suspend fun updateWeeklyTasks(){
        accessHomepageSettings.updateWeeklyTasks(
            userId,
            !homepageSettings.weeklyTasks
        )
        getHomepageSettings()
    }

    //Updates the visibility of the all tasks widget
    suspend fun updateAllTasks(){
        accessHomepageSettings.updateAllTasks(
            userId,
            !homepageSettings.allTasks
        )
        getHomepageSettings()
    }

    //Updates the visibility of the study centre navigation widget
    suspend fun updateStudyCentreNav(){
        accessHomepageSettings.updateStudyCentreNav(
            userId,
            !homepageSettings.studyCentreNav
        )
        getHomepageSettings()
    }

    //Updates the visibility of the planner navigation widget
    suspend fun updatePlannerNav(){
        accessHomepageSettings.updatePlannerNav(
            userId,
            !homepageSettings.plannerNav
        )
        getHomepageSettings()
    }

    //Updates the visibility of the task list widget
    suspend fun updateTaskListNav(){
        accessHomepageSettings.updateTaskListNav(
            userId,
            !homepageSettings.taskListNav
        )
        getHomepageSettings()
    }

    //Updates the visibility of the history navigation widget
    suspend fun updateHistoryNav(){
        accessHomepageSettings.updateHistoryNav(
            userId,
            !homepageSettings.historyNav
        )
        getHomepageSettings()
    }

    //Updates the visibility of the profile navigation widget
    suspend fun updateProfileNav(){
        accessHomepageSettings.updateProfileNav(
            userId,
            !homepageSettings.profileNav
        )
        getHomepageSettings()
    }

    //Navigates to the user's profile
    fun profileNav(navController: NavController) {
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    //Navigates to the study centre
    fun navStudyCentre(navController: NavController) {
        Log.d("TEST", "Navigating to the Study Centre...")
        navigationFunctions.goToStudyCentre(navController)
    }

    //Navigates to the planner
    fun navPlanner(navController: NavController) {
        Log.d("TEST", "Navigating to the Planner...")
        navigationFunctions.goToPlanner(navController)
    }

    //Navigates to the task list
    fun navTaskList(navController: NavController) {
        Log.d("TEST", "Navigating to the Task List...")
        navigationFunctions.goToTaskList(navController)
    }

    //Navigates to the history
    fun navHistory(navController: NavController) {
        Log.d("TEST", "Navigating to the History...")
        navigationFunctions.goToHistory(navController)
    }
}