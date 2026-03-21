package com.example.starstudent.studySpaces.view.screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions
import com.example.starstudent.studySpaces.domain.Timer
import com.example.starstudent.userAccounts.data.AccessUserData
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class StudyCentreViewModel : ViewModel() {

    private val navigationFunctions = NavigationFunctions()

    val timer = Timer()

    val locationManager = LocationServices.getFusedLocationProviderClient(CurrentApplication.instance)

    private val accessUserData = AccessUserData()

    private val savedLocationsDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication.instance
            ).savedLocationsDao()

    var locationAccess by mutableStateOf(
        accessUserData.getUserLocationAccess()
    )

    var sessionStatus by mutableStateOf(
        false
    )

    var longitude  by mutableStateOf(
        0.0
    )

    var latitude  by mutableStateOf(
        0.0
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

    var savedLocations by mutableStateOf(
        listOf(SavedLocations(
            1,
            "",
            "Default",
            0.0,
            0.0
        ))
    )

    var studySessionLength by mutableStateOf(
        480
    )

    private val studySessionsDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .studySessionsDao()

    private val pausedSessionsDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .pausedSessionsDao()

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

    var pausedTime = 0L

    var updateLocation by mutableStateOf(
        false
    )

    var sessionCountDown by mutableStateOf(
        1
    )

    var newLabel by mutableStateOf(
        ""
    )

    var withinStudySpace by mutableStateOf(
        false
    )

    var studySpaceDetector by mutableStateOf(
        "Not Detected"
    )

    fun showUpdateLocationDialog(){
        if(locationAccess){
            showUpdateLocationDialog = true
        }
    }

    fun hideUpdateLocationDialog(){
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
        savedLocations = savedLocationsDAO.getSavedLocations(
            getUser()
        )
    }

    suspend fun updateSavedLocationLabel(
        id: Int
    ){

        val newLabel = getUpdatedLabel(id)
        if(newLabel.isNotEmpty()){
            savedLocationsDAO.updateSavedLabel(
                id,
                CurrentApplication.instance.user.email.getEmail(),
                newLabel
            )
            getSavedLocations()
        }
    }

    suspend fun updateSavedLocation(
        id: Int
    ){
        getLocation()
        savedLocationsDAO.updateSavedLocation(
            id,
            CurrentApplication.instance.user.email.getEmail(),
            longitude,
            latitude
        )
        getSavedLocations()
    }

    fun getLocationData() : String{
        if(locationAccess){
            updateLocation = true
            return "Location"
        }
        updateLocation = false
        return "Location Access: Off"
    }

    fun updateTimer(){
        timerClock =
            "%02d:%02d:%02d".format(
                timer.getHours(), timer.getMinutes(), timer.getSeconds()
            )
    }

    fun increaseSessionCountdown(){
        sessionCountDown += 1
        Log.d("COUNTDOWN", sessionCountDown.toString())
    }

    fun updateTime(){
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

    fun getCurDateTime() : Long{
        return System.currentTimeMillis()
    }

    fun updateStudyingStatus(){
        studyingStatus = if(sessionStatus){
            "Studying..." +
                    "\n " + timerClock
        }else{
            "Let's Study"
        }
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    suspend fun updateSessionStatus(){
        if(!sessionStatus){
            startSession()
            timer.resetTimer()
            timer.startTimer()
            studySessionLength = 540
        }
        else{
            if(isSessionPause){
                unpauseSession()
                isSessionPause = !isSessionPause
            }
            endSession(getCurrentSession()[0])
            studySessionLength = 480
        }
        sessionStatus = !sessionStatus
        updateStudyingStatus()
        showSessionPause =! showSessionPause
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
        if(!isSessionPause){
            val currentSession = getCurrentSession()
            if(currentSession.isNotEmpty()){
                pausedSessionsDAO.pauseSession(
                    PausedSessions(
                        sessionId = currentSession[0].id,
                        startTime = getCurDateTime(),
                        endTime = 0L,
                    )
                )
                timer.pauseTimer()
            }
        }else{
            unpauseSession()
            timer.resumeTimer()
        }
        isSessionPause = !isSessionPause
    }

    suspend fun unpauseSession(){
        val currentPausedSession = getCurrentPausedSession()
        if(currentPausedSession.isNotEmpty()){
            pausedSessionsDAO.unpauseSession(
                currentPausedSession[0].id,
                currentPausedSession[0].sessionId,
                currentPausedSession[0].startTime,
                endTime = getCurDateTime()
            )
        }
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

//    var checked = false

    suspend fun checkLocation(){
        var checked = false
        getSavedLocations()
        for (location in savedLocations){
            if((longitude <= (location.longitude + 0.0000350)) &&
                (longitude >= (location.longitude - 0.0000350)) &&
                (latitude <= (location.latitude + 0.0000350)) &&
                (latitude >= (location.latitude - 0.0000350))
                ){
                checked = true
                break
            }
        }

        withinStudySpace = checked

        studySpaceDetector = if (withinStudySpace){
            "Study Space Detected"
        }else{
            "Not Detected"
        }
    }

    suspend fun getCurrentSession(): List<StudySessions>{
        return studySessionsDAO.getCurrentSession(
            getUser()
        )
    }

    suspend fun getCurrentPausedSession(): List<PausedSessions>{
        return pausedSessionsDAO.getCurrentPausedSession(
            getCurrentSession()[0].id
        )
    }

    suspend fun startCurrentSession(){
        studySessionsDAO.startSession(
            StudySessions(
                user = getUser(),
                startTime = getCurDateTime(),
                endTime = 0L
            )
        )
    }

    suspend fun endSession(
        currentSession: StudySessions
    ){
        studySessionsDAO.endSession(
            currentSession.id,
            currentSession.user,
            currentSession.startTime,
            endTime = getCurDateTime()
        )

        timer.resetTimer()
        sessionCountDown = 1
    }

    suspend fun startSession(){
        if(getCurrentSession().isEmpty()){
            startCurrentSession()
            updateTimer()
        }else {
            endSession(getCurrentSession()[0])
            startCurrentSession()
        }
    }

    suspend fun getRecentStudySessions(){
        recentStudySessions = studySessionsDAO.getRecentSessions(
            getUser()
        )
    }

    suspend fun getPausedSessions(sessionId: Int) : List<PausedSessions>{
        return pausedSessionsDAO.getRecentSessions(sessionId)
    }

    fun calculateTotalStudyTime(
        sessionId: Int,
        startTime: Long,
        endTime: Long
    ) : String{
        return "%02d".format(getHours(endTime, startTime)) + " Hours, " + "%02d".format(getMinutes(endTime, startTime))+ " Minutes "
    }

    fun getHours(endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - pausedTime
        if(session >= 1){
            return (session / (1000*60*60))
        }
        return 0
    }

    suspend fun totalPausedTime(sessionID: Int) : Long{
        val pausedSessionList = pausedSessionsDAO.getCurrentPausedSession(sessionID)
        if (pausedSessionList.isNotEmpty()){
            var totalPausedTime = 0L
            pausedSessionList.forEach { session ->
                totalPausedTime += session.endTime - session.startTime
            }
            return totalPausedTime
        }
        return 0L
    }

    fun getMinutes(endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - pausedTime
        if(session >= 1){
            return ((session / (1000*60))% 60)
        }
        return 0
    }

    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(longDate))
    }

    fun getLongToDate(startTime: Long) : String{
        return convertLongToDate(
            startTime,
            "EEEE d MMMM yyyy")
    }

    fun getTotalPausedTime(pausedSessionList : List<PausedSessions>){
        pausedSessionList.forEach { option ->
            if (option.endTime != 0L){
                pausedTime += (option.endTime - option.startTime)
            }
        }
    }

    fun getLocation(){
        locationManager.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
            .addOnFailureListener {
                locationAccess = false
                Log.d("GPS LAT", "Failed to get location.")
            }
    }
    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        return listOf(
            NavigationOptions("Homepage")
            {navigationFunctions.goToHomepage(navController)},
            NavigationOptions("Profile Settings")
            {navigationFunctions.goToProfile(navController)}
        )
    }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }
}