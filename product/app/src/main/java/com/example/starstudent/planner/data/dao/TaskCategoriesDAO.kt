package com.example.starstudent.planner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.planner.data.entities.TaskCategories

@Dao
interface TaskCategoriesDAO {

    @Insert
    suspend fun addNewCategory(newCategory: TaskCategories)


    @Query("""
        UPDATE task_categories
        SET cateLabel = :cateLabel, labelColour = :labelColour, isActive = :isActive
        WHERE id = :id
    """)
    suspend fun updateCategory(
        id: Int,
        cateLabel: String,
        labelColour: String,
        isActive: Boolean
    )

    @Query("""
        UPDATE task_categories
        SET isActive = 1
        WHERE id = :id
    """)
    suspend fun addHiddenCategory(
        id: Int
    )

    @Query("""
        SELECT * 
        FROM task_categories 
        WHERE user = :user AND isActive = 1
        ORDER BY cateLabel ASC
        """)
    suspend fun getAllCurrentCategories(
        user: String
    ) :  List<TaskCategories>

    @Query("""
        SELECT * 
        FROM task_categories 
        WHERE user = :user AND isActive = 0
        ORDER BY cateLabel ASC
        """)
    suspend fun getHiddenCategories(
        user: String
    ) :  List<TaskCategories>

}