package com.example.starstudent.planner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.planner.data.entities.Tasks


@Dao
interface TasksDAO {

    @Insert
    suspend fun addNewTask(newTask: Tasks)

    @Query("""
        UPDATE tasks
        SET 
            cateId = :cateId, 
            taskLabel = :taskLabel, 
            isCritical = :isCritical, 
            dueDate = :dueDate, 
            isComplete = :isComplete, 
            completeDate = :completeDate
        WHERE id = :id
    """)
    suspend fun updateTask(
        id: Int,
        cateId: Int,
        taskLabel: String,
        isCritical: Boolean,
        dueDate : Long,
        isComplete: Boolean,
        completeDate: Long
    )

    @Query("""
        UPDATE tasks
        SET 
            isComplete = :isComplete, 
            completeDate = :completeDate
        WHERE id = :id
    """)
    suspend fun updateTaskCompletion(
        id: Int,
        isComplete: Boolean,
        completeDate: Long
    )

    @Query("""
        SELECT tasks.id, tasks.cateId,tasks.taskLabel, tasks.isCritical, tasks.dueDate, tasks.isComplete, tasks.completeDate, tasks.isActive 
        FROM tasks , task_categories
        WHERE user = :user AND tasks.isActive = 1 AND tasks.cateId == task_categories.id
        ORDER BY dueDate ASC
        """)
    suspend fun getAllCurrentTasks(
        user: String
    ) :  List<Tasks>
}