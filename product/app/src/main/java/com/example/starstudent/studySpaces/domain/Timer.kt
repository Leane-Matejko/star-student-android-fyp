package com.example.starstudent.studySpaces.domain

class Timer {
    var pausedTime = 0L
    var isRunning = false

    var startTime = 0L

    var endTime = 0L

    var curPauseStart = 0L

    var totalPausedTime = 0L

    //Start current study session timer from now
    fun startTimer(){
        startTime = System.currentTimeMillis()
        isRunning = true
    }

    //Start current study session timer from an existing session
    fun startTimer(duration : Long){
        startTime = System.currentTimeMillis() - duration
        isRunning = true
    }

    //Pause current study session timer
    fun pauseTimer(){
        curPauseStart = System.currentTimeMillis()
        isRunning = false
    }

    //Set paused session timer
    fun setPauseStartTimer(start : Long){
        curPauseStart = start
        isRunning = false
    }

    //Resume timer and refresh paused timer variables
    fun resumeTimer(){
        if(!isRunning) {
            isRunning = true
            totalPausedTime += System.currentTimeMillis() - curPauseStart
            curPauseStart = 0L
        }
    }

    //Retrieve time hours
    fun getHours():Long{
        return (((System.currentTimeMillis()-startTime) - totalPausedTime)/ (1000*60*60))
    }

    //Retrieve time minutes
    fun getMinutes():Long{
        return (((System.currentTimeMillis()-startTime)- totalPausedTime) / (1000*60)% 60)
    }

    //Retrieve time seconds
    fun getSeconds():Long{
        return (((System.currentTimeMillis()-startTime)- totalPausedTime) / (1000)% 60)
    }

    //Reset timer completely
    fun resetTimer(){
        startTime = 0L
        isRunning = false
        pausedTime = 0L
        totalPausedTime = 0L
    }
}