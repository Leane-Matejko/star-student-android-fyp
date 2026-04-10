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

    val accessStudySessions = AccessStudySessions()

    val accessPausedSessions = AccessPausedSessions()

    fun getSessionStatus(): Boolean{
        return sessionStatus
    }

    fun getShowSessionPause(): Boolean{
        return showSessionPause
    }

    fun getIsSessionPause(): Boolean{
        return isSessionPause
    }

    fun getTimerClock(): String{
        return "%02d:%02d:%02d".format(
            timer.getHours(), timer.getMinutes(), timer.getSeconds()
        )
    }

    fun getSessionCountDown(): Int{
        return sessionCountDown
    }

    fun increaseSessionCountdown(){
        sessionCountDown += 1
    }

    fun resetSessionCountdown(){
        sessionCountDown = 1
    }

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

    suspend fun startSession(user: String){
        if(accessStudySessions.getCurrentStudySession(user).isEmpty()){
            startCurrentSession(user)
        }else{
            endSession(accessStudySessions.getCurrentStudySession(user)[0])
            startCurrentSession(user)
        }
    }

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

//        val hasSessionPaused = pausedSessions.any { it.endTime == 0L }

        val activePause = pausedSessions.find { it.endTime == 0L }

        if(activePause != null){
            timer.pauseTimer()
            timer.setPauseStartTimer(activePause.startTime)
        }

        isSessionPause = activePause != null
        sessionStatus = true
        showSessionPause = true
    }

    suspend fun getPausedSessions(
        sessionId : Int
    ) : List<PausedSessions>{
        return accessPausedSessions.getPausedSessions(
            sessionId
        )
    }

    suspend fun resetSessionStart(
        id : Int,
        duration : Long
    ) {
        accessStudySessions.resetSessionStart(
            id,
            duration
        )
        accessPausedSessions.deletePausedSessions(
            id
        )
    }

    suspend fun startCurrentSession(
        user:String
    ){
        accessStudySessions.startSession(
            user,
            System.currentTimeMillis()
        )
    }

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

    suspend fun unpauseSession(){
        val currentPausedSession = getCurrentPausedSession()
        if(currentPausedSession.isNotEmpty()){
            accessPausedSessions.endPauseSession(
                currentPausedSession,
                getCurDateTime()
            )
        }
    }

    fun getCurDateTime() : Long{
        return System.currentTimeMillis()
    }

    suspend fun getCurrentPausedSession(): List<PausedSessions>{
        return accessPausedSessions.getCurrentPausedSession(
            getCurrentSession()
        )
    }

    suspend fun getCurrentSession(): List<StudySessions>{
        return accessStudySessions.getCurrentStudySession(
            getUser()
        )
    }

    fun getUser(): String{
        return CurrentApplication.instance.user.email.getEmail()
    }

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