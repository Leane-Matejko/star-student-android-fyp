package com.example.starstudent.studySpaces.domain

class Timer {

    var curTime = 0L

    var pausedTime = 0L

    var isRunning = false

    var startTime = 0L

    var endTime = 0L

    var curPauseStart = 0L

    var totalPausedTime = 0L

    fun startTimer(){
        startTime = System.currentTimeMillis()
        isRunning = true
    }

    fun pauseTimer(){
        curPauseStart = System.currentTimeMillis()
        isRunning = false
    }

    fun resumeTimer(){
        if(!isRunning) {
            isRunning = true
            totalPausedTime += System.currentTimeMillis() - curPauseStart
            curPauseStart = 0L
        }
    }

    fun getHours():Long{
        return (((System.currentTimeMillis()-startTime) - totalPausedTime)/ (1000*60*60))
    }

    fun getMinutes():Long{
        return (((System.currentTimeMillis()-startTime)- totalPausedTime) / (1000*60)% 60)
    }

    fun getSeconds():Long{
        return (((System.currentTimeMillis()-startTime)- totalPausedTime) / (1000)% 60)
    }

//    fun getCurTime() : Long{
//        return (System.currentTimeMillis() - startTime) - pausedTime
//    }

    fun resetTimer(){
        startTime = 0L
        isRunning = false
        pausedTime = 0L
        totalPausedTime = 0L
    }

    fun getTimer(): Long{
        if(isRunning){
            (curTime - startTime) - totalPausedTime
        }
        return 0L //Timer has been stopped
    }
}