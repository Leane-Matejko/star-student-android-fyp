package com.example.starstudent.studySpaces.view.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.studySpaces.data.AccessPausedSessions
import com.example.starstudent.studySpaces.data.AccessSavedLocations
import com.example.starstudent.studySpaces.data.AccessStudySessions
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions
import com.example.starstudent.studySpaces.domain.Timer
import com.example.starstudent.userAccounts.data.AccessUserData
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.example.starstudent.studySpaces.domain.LocationDetector
import com.example.starstudent.studySpaces.domain.StudySession
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class StudyCentreViewModel : ViewModel() {


    //Banner Variables
    private val bannerFunctions = BannerFunctions()

    //Location Detector Variable
    private val locationDetector = LocationDetector()

    //Study Session Variables
    private val accessSavedLocations = AccessSavedLocations()

    private val accessPausedSessions = AccessPausedSessions()

    private val accessStudySessions = AccessStudySessions()

    private val accessUserData = AccessUserData()

    private val studySession = StudySession()

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

    var pausedTime = 0L

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
//        var checked = false
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

    suspend fun getPausedSessions(sessionId: Int) : List<PausedSessions>{
        return accessPausedSessions.getPausedSessions(sessionId)
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

    fun getLocation() {
        locationDetector.getLocation()
    }
}