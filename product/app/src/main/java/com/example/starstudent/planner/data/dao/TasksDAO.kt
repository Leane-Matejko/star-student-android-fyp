package com.example.starstudent.planner.data.dao

import androidx.compose.ui.graphics.Interval
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.planner.data.entities.TaskWithCategory
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
        UPDATE tasks
        SET 
            isActive = 0
        WHERE cateId = :cateId
    """)
    suspend fun hideTasks(
        cateId: Int
    )

    @Query("""
        UPDATE tasks
        SET 
            isActive = 1
        WHERE cateId = :cateId AND isComplete = 0
    """)
    suspend fun showTasks(
        cateId: Int
    )

    @Query("""
        UPDATE tasks
        SET 
            isActive = 0
        WHERE id IN (
            SELECT tasks.id
            FROM tasks
            INNER JOIN task_categories cate
            ON tasks.cateId = cate.id 
            WHERE cate.user = :user AND tasks.isComplete = 1 AND tasks.completeDate < :recentMonday
        
        )
    """)
    suspend fun hideCompletedTasks(
        user: String,
        recentMonday : Long
    )

    @Query("""
        SELECT tasks.id,tasks.cateId,tasks.taskLabel, tasks.isCritical, tasks.dueDate, tasks.isComplete, tasks.completeDate, tasks.isActive 
        FROM tasks 
        INNER JOIN task_categories
        ON tasks.cateId = task_categories.id
        WHERE task_categories.user = :user AND tasks.isActive = 1
            AND (tasks.completeDate >= :acceptableInterval) 
        ORDER BY dueDate ASC
        """)
    suspend fun getAllCurrentTasksWeek(
        user: String,
        acceptableInterval : Long
    ) :  List<Tasks>

    @Query("""
        SELECT tasks.id,
                tasks.cateId,
                task_categories.cateLabel AS taskCategoryLabel,
                task_categories.labelColour AS taskCategoryLabelColour,
                tasks.taskLabel,tasks.isCritical, 
                tasks.dueDate, 
                tasks.isComplete,
                tasks.completeDate, 
                tasks.isActive 
        FROM tasks 
        INNER JOIN task_categories 
        ON tasks.cateId = task_categories.id
        WHERE task_categories.user = :user AND tasks.isActive = 1
            AND (tasks.dueDate BETWEEN :intervalStart AND :intervalEnd) 
        ORDER BY dueDate ASC
        """)
    suspend fun getAllCurrentTasksForInterval(
        user: String,
        intervalStart: Long,
        intervalEnd: Long
    ) :  List<TaskWithCategory>
}