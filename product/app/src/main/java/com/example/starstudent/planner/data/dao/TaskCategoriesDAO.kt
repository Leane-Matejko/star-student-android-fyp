package com.example.starstudent.planner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.planner.data.entities.TaskCategories

//Data access object for task categories
@Dao
interface TaskCategoriesDAO {

    //Insert a new category
    @Insert
    suspend fun addNewCategory(newCategory: TaskCategories)

    //Update the label, colour and active session of a category
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

    //Unhide a previous category
    @Query("""
        UPDATE task_categories
        SET isActive = 1
        WHERE id = :id
    """)
    suspend fun addHiddenCategory(
        id: Int
    )

    //Return a list of all active categories
    @Query("""
        SELECT * 
        FROM task_categories 
        WHERE user = :user AND isActive = 1
        ORDER BY cateLabel ASC
        """)
    suspend fun getAllCurrentCategories(
        user: String
    ) :  List<TaskCategories>

    //Return a list of all hidden categories
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