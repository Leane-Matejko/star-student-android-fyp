package com.example.starstudent.planner.data

import androidx.compose.runtime.mutableStateOf
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.planner.data.entities.TaskCategories

class AccessTaskCategories {

    val taskCategoriesDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .taskCategoriesDao()

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

    suspend fun getCategoryList(
        username : String
    ): List<TaskCategories>{
        return taskCategoriesDAO.getAllCurrentCategories(username)
    }

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

    suspend fun getHiddenCategories(
        username: String
    ) : List<TaskCategories>{
        return taskCategoriesDAO.getHiddenCategories(
            username
        )
    }

    suspend fun addHiddenCategory(
        cateId : Int
    ){
        taskCategoriesDAO.addHiddenCategory(
            cateId
        )
    }
}