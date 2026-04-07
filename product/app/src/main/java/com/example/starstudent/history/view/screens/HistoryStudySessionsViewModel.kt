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

    fun showSelectMonthDialog()
    {showSelectMonthDialog = true}

    fun hideSelectMonthDialog()
    {showSelectMonthDialog = false }

    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
        monthRange = getMonthStartEnd()
        filteredSessionList = getUpdateSessionListSelection()
    }
    fun setYear(newYear: Int){
        calendarYear = newYear
        monthRange = getMonthStartEnd()
        filteredSessionList = getUpdateSessionListSelection()
    }
    fun toggleYearList(){
        showYearList = !showYearList
    }

    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

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

    fun getMonthStartEnd() : Pair<Long, Long>{
        val month = LocalDate.of(calendarYear, calendarMonth + 1,1)
        val monthStart = month.getLocalDateStart()
        val monthEnd = LocalDate.of(
            calendarYear,
            calendarMonth + 1,
            month.lengthOfMonth()).getLocalDateEnd()

        return Pair(monthStart, monthEnd)
    }

    fun getDayStartEnd(
        day : LocalDate
    ) : Pair<Long, Long>{
        return Pair(
            day.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            day.atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    fun getUpdateSessionListSelection() : List<StudySessions>{
        return sessionList.filter{
            it.startTime in monthRange.first..monthRange.second
        }
    }

    fun getFormattedDuration(
        duration : Int
    ) : String{
        return categoryTaskFormatting.formatDuration(duration)
    }

    suspend fun getStudySessionList(){
        sessionList = accessStudySessions.getRecentStudySessionsAll(userId)
        filteredSessionList = getUpdateSessionListSelection()
        sessionDurationList = accessStudySessions.getRecentSessionsWithDuration(userId)
        Log.d("SESSION LIST", sessionList.toString())
    }

    suspend fun deleteStudySession(
        id : Int
    ){
        accessStudySessions.deleteSession(id)
        accessPausedSessions.deletePausedSessions(id)
        sessionList = accessStudySessions.getRecentStudySessionsAll(userId)
        filteredSessionList = getUpdateSessionListSelection()
        sessionDurationList = accessStudySessions.getRecentSessionsWithDuration(userId)
    }

    fun getFormattedDate(
        date : LocalDate
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, "EEE d MMM")
    }

    fun getFormattedDate(
        date : Long
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, "EEE d MMM")
    }

    fun getFormattedDate(
        date : Long,
        pattern : String
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, pattern)
    }

    fun getFormattedDateMonth(
        date : Long
    ) : String{
        return categoryTaskFormatting.formatDateTime(date, "MMMM yyyy")
    }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun historyNav(navController: NavController){
        Log.d("TEST", "Navigating to the history...")
        navigationFunctions.goToHistory(navController)
    }

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
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }

    fun resetErrorWindow(){
        errorWindow = false
    }
}