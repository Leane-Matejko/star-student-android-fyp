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
//            getTimerClock()
        }else{
            endSession(accessStudySessions.getCurrentStudySession(user)[0])
            startCurrentSession(user)
        }
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