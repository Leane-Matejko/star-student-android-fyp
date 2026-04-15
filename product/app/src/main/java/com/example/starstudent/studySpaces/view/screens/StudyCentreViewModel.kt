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

    var userId by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .id)
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

    var longitude by mutableStateOf(
        0.0
    )
        private set
    var latitude by mutableStateOf(
        0.0
    )
        private set

    //Shows update location dialog if location permission is granted
    fun showUpdateLocationDialog(){
        if(locationAccess){
            showUpdateLocationDialog = true
        }
    }

    //Hide update location dialog
    fun dismissUpdateLocationDialog(){
        showUpdateLocationDialog = false
    }

    //Update the user's location access
    fun updateLocationAccess(){
        locationAccess = accessUserData.getUserLocationAccess()
    }

    //Update the temporary label for the saved locations
    fun updateLabel(id: Int, newLabel: String) {
        savedLocations = savedLocations.map { location ->
            if (location.id == id) {
                location.copy(label = newLabel)
            } else {
                location
            }
        }
    }

    //Return the temporary label for the saved location
    fun getUpdatedLabel(id: Int): String {
        savedLocations.forEach { location ->
            if (location.id == id) {
                return location.label
            }
        }
        return "Default"
    }

    //Retrieve the user's saved locations
    suspend fun getSavedLocations(){
        accessSavedLocations.getSavedLocations(getUser())
        savedLocations = accessSavedLocations.getCurrentSavedLocationsList()
    }

    //Update the label of the saved location
    suspend fun updateSavedLocationLabel(
        id: Int
    ){

        val newLabel = getUpdatedLabel(id)
        if(newLabel.isNotEmpty()){
            accessSavedLocations.updateSavedLocationLabel(
                id,
                newLabel
            )
            getSavedLocations()
        }
    }

    //Update the longitude and latitude of a saved location
    suspend fun updateSavedLocation(
        id: Int
    ){
        accessSavedLocations.updateSavedLocation(
            id,
            getUser(),
            locationDetector.getLongitude(),
            locationDetector.getLatitude()
        )
        getSavedLocations()
    }

    //Update the study session timer
    fun updateTimer(){
        timerClock = studySession.getTimerClock()
    }

    //Increment the session count down
    fun increaseSessionCountdown(){
        studySession.increaseSessionCountdown()
        sessionCountDown = studySession.getSessionCountDown()
    }


    //Banner Logic
    //Update the date time on the banner
    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    //Get the navigation options for the study centre (all windows wo/ study centre)
    fun getNavigationMenu(navController: NavController,
                          locationDetector: LocationDetector): List<NavigationOptions>{
        return bannerFunctions.getNavigationMenu(navController)
    }

    //Navigates to the user's profile
    fun profileNav(navController: NavController){
        locationDetector.stopLocationUpdates()
        bannerFunctions.profileNav(navController)
    }

    //Show the navigation menu
    fun showNavMenu()
    {showNavMenu = true}

    //Hide the navigation menu
    fun dismissNavMenu()
    {showNavMenu = false }

    //Update the session widget w/ timer or prompt
    fun updateStudyingStatus(){
        studyingStatus = if(studySession.getSessionStatus()){
            "Studying..." +
                    "\n " + studySession.getTimerClock()
        }else{
            "Let's Study"
        }
    }

    //Update the current session status (start or end a session)
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

    //Format the pause study session button
    fun formatPauseSessionButton() : String{
        if(!isSessionPause){
            return "Pause Session"
        }
        return "Unpause Session"
    }

    //Show the pause session button
    fun showPauseButton() : Boolean{
        return showSessionPause
    }

    //Update the pause session status
    suspend fun updatePauseSession(){
        studySession.updatePauseSession()
        isSessionPause = studySession.getIsSessionPause()
    }

    //Format the study session button
    fun formatSessionButton() : String{
        if(!sessionStatus){
            return "Start Session"
        }
        return "End Session"
    }

    //Return the user id
    fun getUser(): String{
        return CurrentApplication.instance.user.email.getEmail()
    }

    //Retrieve if the user is within a study space
    suspend fun checkLocation(){
//        getSavedLocations()
        locationDetector.checkLocation(savedLocations)
        withinStudySpace = locationDetector.getWithinStudySpace()
        studySpaceDetector = locationDetector.studyDetectorFormatted()
    }

    //Retrieve recent study session
    suspend fun getRecentStudySessions(){
        recentStudySessions = accessStudySessions.getRecentStudySessions(
            getUser()
        )
    }

    //Retrieve list of formatted recent study sessions
    suspend fun updateFormattedRecentSessions(){
        getRecentStudySessions()
        recentStudySessionsFormatted = formatStudyCentre.formatRecentSessions(recentStudySessions)
    }

    //Return the long for the most recent monday
    fun getRecentMonday() : Long{
        return formatHomepage.getRecentMonday()
    }

    //Return the long for the end of the week
    fun getEndOfWeek() : Long{
        return formatHomepage.getEndOfWeek()
    }

    //Return the long for now
    fun getNowLong() : Long{
        return formatHomepage.getNowLong()
    }

    //Retrieve and refresh the task lists of different groups and their completion status
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

    //Show dialog for group of tasks
    fun showTasksDialog(
        tasksTypeInput : String
    ){
        tasksType = tasksTypeInput
        setCurrentTaskList()
        showTasksDialog = true
    }

    //Hide dialog for group of tasks
    fun hideTasksDialog(){
        showTasksDialog = false
    }

    //Return extended color option from string
    fun getCategoryColour(
        color : String,
        colorList : ExtendedLabelColours
    ): Color {
        return categoryTaskFormatting.getCategoryColour(
            color,
            colorList
        )
    }

    //Return the strong of a long in a specific format
    fun formatDateTime(
        date : Long,
        pattern : String
    ) : String{
        return categoryTaskFormatting.formatDateTime(
            date,
            pattern
        )
    }

    //Set the current task list for the task dialog
    fun setCurrentTaskList(){
        currentTaskList = when (tasksType){
            "overdue" -> overdueTaskList
            "weekly" -> weekTaskList
            else -> allTaskList
        }
    }

    //Retrieve all active tasks
    suspend fun getAllCurrentTasks(){
        taskList = accessTasks.getAllCurrentTasks(getUser())
        resetTasksLists()
    }

    //Update the completion status and completion date of a task
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

    //Get the user's most recent location from their GPS

    fun startLocationTracking(){
        locationDetector.startLocationUpdates { lat, long ->
            latitude = lat
            longitude = long

        }
    }

    fun getLocationDetector(): LocationDetector{
        return locationDetector
    }

    //Check if a session is already active
    suspend fun checkActiveSession(){
        val currentSession = getMostRecentActiveSessions()
        if(currentSession.isNotEmpty()){
            studySession.continueSession(currentSession[0])
            studyingStatus
        }

        showSessionPause = studySession.getShowSessionPause()
        sessionStatus = studySession.getSessionStatus()
        isSessionPause = studySession.getIsSessionPause()
        timerClock = studySession.getTimerClock()

        updateTimer()
        updateStudyingStatus()
    }

    //Return list of the most recent incomplete study sessions
    suspend fun getMostRecentActiveSessions() : List<StudySessions>{
        return  accessStudySessions.getMostRecentActiveSessions(
            userId
        )
    }
}