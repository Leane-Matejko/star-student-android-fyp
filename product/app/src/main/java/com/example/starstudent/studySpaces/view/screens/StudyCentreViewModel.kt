package com.example.starstudent.studySpaces.view.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.FormatHomepage
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.data.entities.Tasks
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.studySpaces.data.AccessSavedLocations
import com.example.starstudent.studySpaces.data.AccessStudySessions
import com.example.starstudent.studySpaces.data.RecentSessionsFormat
import com.example.starstudent.studySpaces.data.entities.StudySessions
import com.example.starstudent.userAccounts.data.AccessUserData
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.example.starstudent.studySpaces.domain.FormatStudyCentre
import com.example.starstudent.studySpaces.domain.LocationDetector
import com.example.starstudent.studySpaces.domain.StudySession
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class StudyCentreViewModel : ViewModel() {

    //Banner Variables
    private val bannerFunctions = BannerFunctions()

    //Location Detector Variable
    private val locationDetector = LocationDetector()

    //Study Session Variables
    private val accessSavedLocations = AccessSavedLocations()

    private val accessStudySessions = AccessStudySessions()

    private val accessUserData = AccessUserData()

    private val accessTasks = AccessTasks()

    private val formatHomepage = FormatHomepage()

    private val studySession = StudySession()

    private val categoryTaskFormatting = CategoryTaskFormatting()

    //Private Formatted Variables
    private val formatStudyCentre = FormatStudyCentre()

    var locationAccess by mutableStateOf(
        accessUserData.getUserLocationAccess()
    )

    var sessionStatus by mutableStateOf(
        false
    )

    var timerClock by mutableStateOf(
        "00:00:00"
    )

    private var showSessionPause by mutableStateOf(
        false
    )

    var isSessionPause by mutableStateOf(
        false
    )

    var recentStudySessions by mutableStateOf(
        listOf(StudySessions(
            0,
            "" ,
            0L,
            0L
        ))
    )

    var recentStudySessionsFormatted by mutableStateOf(
        listOf(RecentSessionsFormat(
            "",
            ""
        ))
    )

    var savedLocations by mutableStateOf(
        listOf(SavedLocations(
            1,
            "",
            "Default",
            0.0,
            0.0
        ))
    )

    var studySessionContainerLength by mutableStateOf(
        480
    )

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username)
        private set

    var studyingStatus by mutableStateOf(
        "Let's Study!!")
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

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var showUpdateLocationDialog by mutableStateOf(
        false
    )
        private set

    var updateLocation by mutableStateOf(
        false
    )

    var sessionCountDown by mutableStateOf(
        1
    )

    var withinStudySpace by mutableStateOf(
        false
    )

    var studySpaceDetector by mutableStateOf(
        "Not Detected"
    )

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

    fun showUpdateLocationDialog(){
        if(locationAccess){
            showUpdateLocationDialog = true
        }
    }

    fun dismissUpdateLocationDialog(){
        showUpdateLocationDialog = false
    }

    fun updateLocationAccess(){
        locationAccess = accessUserData.getUserLocationAccess()
    }

    fun updateLabel(id: Int, newLabel: String) {
        savedLocations = savedLocations.map { location ->
            if (location.id == id) {
                location.copy(label = newLabel)
            } else {
                location
            }
        }
    }
    fun getUpdatedLabel(id: Int): String {
        savedLocations.forEach { location ->
            if (location.id == id) {
                return location.label
            }
        }
        return "Default"
    }

    suspend fun getSavedLocations(){
        accessSavedLocations.getSavedLocations(getUser())
        savedLocations = accessSavedLocations.getCurrentSavedLocationsList()
    }

    suspend fun updateSavedLocationLabel(
        id: Int
    ){

        val newLabel = getUpdatedLabel(id)
        if(newLabel.isNotEmpty()){
            accessSavedLocations.updateSavedLocationLabel(
                id,
                getUser(),
                newLabel
            )
            getSavedLocations()
        }
    }

    suspend fun updateSavedLocation(
        id: Int
    ){
        locationDetector.getLocation()
        accessSavedLocations.updateSavedLocation(
            id,
            getUser(),
            locationDetector.getLongitude(),
            locationDetector.getLatitude()
        )
        getSavedLocations()
    }

    fun updateTimer(){
        timerClock = studySession.getTimerClock()
    }

    fun increaseSessionCountdown(){
        studySession.increaseSessionCountdown()
        sessionCountDown = studySession.getSessionCountDown()
    }


    //Banner Logic
    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{
        return bannerFunctions.getNavigationMenu(navController)
    }

    fun profileNav(navController: NavController){
        bannerFunctions.profileNav(navController)
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun updateStudyingStatus(){
        studyingStatus = if(studySession.getSessionStatus()){
            "Studying..." +
                    "\n " + studySession.getTimerClock()
        }else{
            "Let's Study"
        }
    }

    suspend fun updateSessionStatus(){
        studySession.updateSessionStatus(getUser())
        sessionStatus = studySession.getSessionStatus()
        updateStudyingStatus()
        showSessionPause = studySession.getShowSessionPause()
        studySessionContainerLength = if(sessionStatus){
            540
        }else{
            480
        }
    }

    fun formatPauseSessionButton() : String{
        if(!isSessionPause){
            return "Pause Session"
        }
        return "Unpause Session"
    }

    fun showPauseButton() : Boolean{
        return showSessionPause
    }

    suspend fun updatePauseSession(){
        studySession.updatePauseSession()
        isSessionPause = studySession.getIsSessionPause()
    }

    fun formatSessionButton() : String{
        if(!sessionStatus){
            return "Start Session"
        }
        return "End Session"
    }

    fun getUser(): String{
        return CurrentApplication.instance.user.email.getEmail()
    }

    suspend fun checkLocation(){
        getSavedLocations()
        locationDetector.checkLocation(savedLocations)
        withinStudySpace = locationDetector.getWithinStudySpace()
        studySpaceDetector = locationDetector.studyDetectorFormatted()
    }

    suspend fun getRecentStudySessions(){
        recentStudySessions = accessStudySessions.getRecentStudySessions(
            getUser()
        )
    }

    suspend fun updateFormattedRecentSessions(){
        getRecentStudySessions()
        recentStudySessionsFormatted = formatStudyCentre.formatRecentSessions(recentStudySessions)
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

    fun setCurrentTaskList(){
        currentTaskList = when (tasksType){
            "overdue" -> overdueTaskList
            "weekly" -> weekTaskList
            else -> allTaskList
        }
    }

    suspend fun getAllCurrentTasks(){
        taskList = accessTasks.getAllCurrentTasks(getUser())
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

    fun getLocation() {
        locationDetector.getLocation()
    }
}