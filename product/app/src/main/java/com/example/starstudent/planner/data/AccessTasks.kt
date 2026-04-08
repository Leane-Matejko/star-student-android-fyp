package com.example.starstudent.planner.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.data.entities.Tasks

class AccessTasks {

    val tasksDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .tasksDao()

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

    suspend fun hideTasks(
        cateId: Int
    ){
        tasksDAO.hideTasks(
            cateId
        )
    }

    suspend fun showTasks(
        cateId: Int
    ){
        tasksDAO.showTasks(
            cateId
        )
    }

    suspend fun hideCompletedTasks(
        user : String,
        recentMonday : Long
    ){
        tasksDAO.hideCompletedTasks(
            user,
            recentMonday
        )
    }

    suspend fun getAllCurrentTasks(
        user: String
    ): List<TaskWithCategory>{
        return tasksDAO.getAllCurrentTasks(user)
    }
}