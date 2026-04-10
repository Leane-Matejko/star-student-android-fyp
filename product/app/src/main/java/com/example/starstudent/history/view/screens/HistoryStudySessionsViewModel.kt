package com.example.starstudent.history.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.domain.CalendarFunctions
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.studySpaces.data.AccessPausedSessions
import com.example.starstudent.studySpaces.data.AccessStudySessions
import com.example.starstudent.studySpaces.data.entities.StudySessionDuration
import com.example.starstudent.studySpaces.data.entities.StudySessions
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

class HistoryStudySessionsViewModel : ViewModel() {
    val bannerFunctions = BannerFunctions()
    val navigationFunctions = NavigationFunctions()
    val calendarFunctions = CalendarFunctions()
    val categoryTaskFormatting = CategoryTaskFormatting()
    val accessStudySessions = AccessStudySessions()
    val accessPausedSessions = AccessPausedSessions()
    val monthList = calendarFunctions.getMonthList()
    val yearList = calendarFunctions.getYearList()

    var showNavMenu by mutableStateOf(
        false
    )
        private set

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

    var curDate by mutableStateOf(
        bannerFunctions.updateTime()
    )
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var sessionList by mutableStateOf(
        listOf<StudySessions>()
    )
        private set

    var sessionDurationList by mutableStateOf(
        listOf<StudySessionDuration>()
    )
        private set

    var showYearList by mutableStateOf(
        false
    )
        private set

    var showSelectMonthDialog by mutableStateOf(
        false
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

    var monthRange by mutableStateOf(
        getMonthStartEnd()
    )
        private set

    var filteredSessionList by mutableStateOf(
        getUpdateSessionListSelection()
    )
        private set

    var showDeleteSessionDialog by mutableStateOf(
        false
    )
        private set

    var deleteSession by mutableStateOf(
        StudySessionDuration(
            0,
            "",
            0L,
            0
        )
    )
        private set

    //Show the select month/year dialog menu
    fun showSelectMonthDialog()
    {showSelectMonthDialog = true}

    //Hide the select month/year dialog menu
    fun hideSelectMonthDialog()
    {showSelectMonthDialog = false }

    //Set the selected month
    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
        monthRange = getMonthStartEnd()
        filteredSessionList = getUpdateSessionListSelection()
    }

    //Set the selected year
    fun setYear(newYear: Int){
        calendarYear = newYear
        monthRange = getMonthStartEnd()
        filteredSessionList = getUpdateSessionListSelection()
    }

    //Show or hide the year list
    fun toggleYearList(){
        showYearList = !showYearList
    }

    //Update the time on the top banner
    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    //Show the navigation menu
    fun showNavMenu()
    {showNavMenu = true}

    //Hide the navigation menu
    fun dismissNavMenu()
    {showNavMenu = false }

    //Return the long of the beginning of a specific date
    fun LocalDate.getLocalDateStart() : Long{
        return this
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    //Return the long of the end of a specific date
    fun LocalDate.getLocalDateEnd() : Long{
        return this
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    //Return the long of the start and end of the selected month and year
    fun getMonthStartEnd() : Pair<Long, Long>{
        val month = LocalDate.of(calendarYear, calendarMonth + 1,1)
        val monthStart = month.getLocalDateStart()
        val monthEnd = LocalDate.of(
            calendarYear,
            calendarMonth + 1,
            month.lengthOfMonth()).getLocalDateEnd()

        return Pair(monthStart, monthEnd)
    }


    //Return the start and end of a chosen date
    fun getDayStartEnd(
        day : LocalDate
    ) : Pair<Long, Long>{
        return Pair(
            day.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            day.atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    //Return the list of tasks that are due within the selected month
    fun getUpdateSessionListSelection() : List<StudySessions>{
        return sessionList.filter{
            it.startTime in monthRange.first..monthRange.second
        }
    }

    //Return the string of a session total duration (H:m)
    fun getFormattedDuration(
        duration : Int
    ) : String{
        return categoryTaskFormatting.formatDuration(duration)
    }

    //Retrieve the most up to date completed study session list and refresh variables
    suspend fun getStudySessionList(){
        sessionList = accessStudySessions.getRecentStudySessionsAll(userId)
        filteredSessionList = getUpdateSessionListSelection()
        sessionDurationList = accessStudySessions.getRecentSessionsWithDuration(userId)
        Log.d("SESSION LIST", sessionList.toString())
    }

    //Delete a study session from the database
    suspend fun deleteStudySession(){
        if(deleteSession.id != 0) {
            accessStudySessions.deleteSession(deleteSession.id)
            accessPausedSessions.deletePausedSessions(deleteSession.id)
            hideDeleteSessionDialog()
            sessionList = accessStudySessions.getRecentStudySessionsAll(userId)
            filteredSessionList = getUpdateSessionListSelection()
            sessionDurationList = accessStudySessions.getRecentSessionsWithDuration(userId)
        }
    }

    //Return the string of a formatted date
    fun getFormattedDate(
        date : LocalDate
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, "EEE d MMM")
    }

    //Return the string of a formatted date in a specific pattern
    fun getFormattedDate(
        date : Long,
        pattern : String
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, pattern)
    }

    //Return the string of a month in a month - year pattern
    fun getFormattedDateMonth(
        date : Long
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, "MMMM yyyy")
    }

    //Show the warning delete session dialog
    fun showDeleteSessionDialog(session : StudySessionDuration){
        deleteSession = session
        showDeleteSessionDialog = true
    }

    //Hide the warning delete session dialog and refresh storage variables
    fun hideDeleteSessionDialog(){
        deleteSession = StudySessionDuration(
            0,
            "",
            0L,
            0
        )
        showDeleteSessionDialog = false
    }

    //Navigates to the user's profile
    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    //Navigates to the history
    fun historyNav(navController: NavController){
        Log.d("TEST", "Navigating to the history...")
        navigationFunctions.goToHistory(navController)
    }

    //Get the navigation options for the history - session page
    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
            { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Planner")
            {navigationFunctions.goToPlanner(navController)},
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("History")
            { navigationFunctions.goToHistory(navController) },
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }

    //Close error window
    fun resetErrorWindow(){
        errorWindow = false
    }
}