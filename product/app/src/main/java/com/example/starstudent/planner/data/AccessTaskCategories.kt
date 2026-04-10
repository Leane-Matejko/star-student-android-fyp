package com.example.starstudent.planner.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.planner.data.entities.TaskCategories

class AccessTaskCategories {

    //Data access object for task categories
    val taskCategoriesDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .taskCategoriesDao()

    //Add a new category to the database
    suspend fun addNewCategory(
        username: String,
        categoryName : String,
        categoryLabelColor : String){
        taskCategoriesDAO.addNewCategory(
            TaskCategories(
                user = username,
                cateLabel = categoryName,
                labelColour = categoryLabelColor,
                isActive = true
            )
        )
    }

    //Get all categories for a user
    suspend fun getCategoryList(
        username : String
    ): List<TaskCategories>{
        return taskCategoriesDAO.getAllCurrentCategories(username)
    }

    //Update the name, label and color of a category
    suspend fun updateExistingCategory(
        categoryId : Int,
        categoryName : String,
        categoryLabelColor : String,
        categoryActive : Boolean
    ){
        taskCategoriesDAO.updateCategory(
            id = categoryId,
            cateLabel = categoryName,
            labelColour = categoryLabelColor,
            isActive = categoryActive
        )
    }

    //Get all hidden categories from a user
    suspend fun getHiddenCategories(
        username: String
    ) : List<TaskCategories>{
        return taskCategoriesDAO.getHiddenCategories(
            username
        )
    }

    //Unhide a hidden category
    suspend fun addHiddenCategory(
        cateId : Int
    ){
        taskCategoriesDAO.addHiddenCategory(
            cateId
        )
    }
}