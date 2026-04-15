package com.example.starstudent.studySpaces.domain

import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.AccessPausedSessions
import com.example.starstudent.studySpaces.data.AccessStudySessions
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions

class StudySession {

    private var sessionStatus = false
    private var showSessionPause = false
    private var isSessionPause = false
    var timer = Timer()
    private var sessionCountDown = 1
    private val accessStudySessions = AccessStudySessions()
    private val accessPausedSessions = AccessPausedSessions()

    //Return the study session status
    fun getSessionStatus(): Boolean{
        return sessionStatus
    }

    //Returns if the session pause is shown
    fun getShowSessionPause(): Boolean{
        return showSessionPause
    }

    //Returns the session pause status
    fun getIsSessionPause(): Boolean{
        return isSessionPause
    }

    //Return the string of study session timer
    fun getTimerClock(): String{
        return "%02d:%02d:%02d".format(
            timer.getHours(), timer.getMinutes(), timer.getSeconds()
        )
    }

    //Return session start count down
    fun getSessionCountDown(): Int{
        return sessionCountDown
    }

    //Increment the session count down
    fun increaseSessionCountdown(){
        sessionCountDown += 1
    }

    //Reset the session count down
    fun resetSessionCountdown(){
        sessionCountDown = 1
    }

    //Update the session status (begin or end a session)
    suspend fun updateSessionStatus(
        user: String
    ){
        if(!sessionStatus){
            startSession(user)
            timer.resetTimer()
            timer.startTimer()
        }
        else{
            if(isSessionPause){
                unpauseSession()
                isSessionPause = !isSessionPause
            }
            endSession(accessStudySessions.getCurrentStudySession(user)[0])
        }
        sessionStatus = !sessionStatus
        showSessionPause =! showSessionPause
    }

    //Start a new study session
    suspend fun startSession(user: String){
        if(accessStudySessions.getCurrentStudySession(user).isEmpty()){
            startCurrentSession(user)
        }else{
            endSession(accessStudySessions.getCurrentStudySession(user)[0])
            startCurrentSession(user)
        }
    }

    //Continue an active study session
    suspend fun continueSession(
        session: StudySessions
    ){
        val pausedSessions = getPausedSessions(session.id)

        val now = System.currentTimeMillis()
        val pausedTime = pausedSessions.sumOf {
            val end  = if (it.endTime == 0L) { now } else {it.endTime}
            end - it.startTime
            }

        val duration = (now - session.startTime) - pausedTime
        timer.startTimer(duration)

        val activePause = pausedSessions.find { it.endTime == 0L }

        if(activePause != null){
            timer.pauseTimer()
            timer.setPauseStartTimer(activePause.startTime)
        }

        isSessionPause = activePause != null
        sessionStatus = true
        showSessionPause = true
    }

    //Return a list of paused sessions from a study session
    suspend fun getPausedSessions(
        sessionId : Int
    ) : List<PausedSessions>{
        return accessPausedSessions.getPausedSessions(
            sessionId
        )
    }

    //Insert a new study session
    suspend fun startCurrentSession(
        user:String
    ){
        accessStudySessions.startSession(
            user,
            System.currentTimeMillis()
        )
    }

    //Update the end time of the current active session
    suspend fun endSession(
        currentSession: StudySessions
    ){
        accessStudySessions.endSession(
            currentSession,
            System.currentTimeMillis()
        )

        timer.resetTimer()
        resetSessionCountdown()
    }

    //Update end time for the current active paused session
    suspend fun unpauseSession(){
        val currentPausedSession = getCurrentPausedSession()
        if(currentPausedSession.isNotEmpty()){
            accessPausedSessions.endPauseSession(
                currentPausedSession,
                getCurDateTime()
            )
        }
    }

    //Return the current time as a long
    fun getCurDateTime() : Long{
        return System.currentTimeMillis()
    }

    //Return a list of paused sessions
    suspend fun getCurrentPausedSession(): List<PausedSessions>{
        return accessPausedSessions.getCurrentPausedSession(
            getCurrentSession()
        )
    }

    //Return a list of all incompleted study sessions
    suspend fun getCurrentSession(): List<StudySessions>{
        return accessStudySessions.getCurrentStudySession(
            getUser()
        )
    }

    //Return the user id
    fun getUser(): String{
        return CurrentApplication.instance.user.email.getEmail()
    }

    //Pause or unpause a study session
    suspend fun updatePauseSession(){
        if(!isSessionPause){
            val currentSession = getCurrentSession()
            if(currentSession.isNotEmpty()){
                accessPausedSessions.startPauseSession(
                    currentSession,
                    getCurDateTime()
                )
                timer.pauseTimer()
            }
        }else{
            unpauseSession()
            timer.resumeTimer()
        }
        isSessionPause = !isSessionPause
    }
}