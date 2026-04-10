package com.example.starstudent.planner.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.data.entities.Tasks

class AccessTasks {

    //Data access object for tasks
    val tasksDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .tasksDao()

    //Add a new task to database
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun addNewTask(
        cateId : Int,
        taskLabel : String,
        isCritical: Boolean,
        dueDate: Long,
        isComplete: Boolean,
        completeDate: Long,
        isActive : Boolean
    ){
        tasksDAO.addNewTask(
            Tasks(
                cateId = cateId,
                taskLabel = taskLabel,
                isCritical = isCritical,
                dueDate = dueDate,
                isComplete = isComplete,
                completeDate = completeDate,
                isActive = isActive
            )
        )
    }

    //Update the completion status and completion date of a task
    suspend fun updateTaskCompletion(
        taskId: Int,
        taskComplete: Boolean
    ){
        if(taskComplete){
            tasksDAO.updateTaskCompletion(
                taskId,
                false,
                0L
            )
        }else {
            tasksDAO.updateTaskCompletion(
                taskId,
                true,
                System.currentTimeMillis()
            )
        }
    }

    //Get all active tasks of a user within a specific date range
    suspend fun getTaskList(
        username : String,
        interval : Long
    ) : List<Tasks>{
        return tasksDAO
            .getAllCurrentTasksWeek(
                username,
                interval
            )
    }

    /*Update the category, label, critical status, due date,
     completion status and the completion date of a task */
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun updateExistingTask(
        id: Int,
        cateId : Int,
        taskLabel : String,
        isCritical: Boolean,
        dueDate: Long,
        isComplete: Boolean,
        completeDate: Long
    ){
        tasksDAO.updateTask(
            id,
            cateId,
            taskLabel,
            isCritical,
            dueDate,
            isComplete,
            completeDate
        )
    }

    //Return a list of all tasks with their categories within a specific range
    suspend fun getTasksWithCategories(
        username : String,
        intervalStart: Long,
        intervalEnd: Long
    ) : List<TaskWithCategory>{

        return tasksDAO.getAllCurrentTasksForInterval(
            username,
            intervalStart,
            intervalEnd
        )

    }

    //Set a task to inactive
    suspend fun hideTasks(
        cateId: Int
    ){
        tasksDAO.hideTasks(
            cateId
        )
    }

    //Set a task to active
    suspend fun showTasks(
        cateId: Int
    ){
        tasksDAO.showTasks(
            cateId
        )
    }

    //Hide tasks that have been completed before the most recent monday
    suspend fun hideCompletedTasks(
        user : String,
        recentMonday : Long
    ){
        tasksDAO.hideCompletedTasks(
            user,
            recentMonday
        )
    }

    //Get a list of all active tasks
    suspend fun getAllCurrentTasks(
        user: String
    ): List<TaskWithCategory>{
        return tasksDAO.getAllCurrentTasks(user)
    }
}