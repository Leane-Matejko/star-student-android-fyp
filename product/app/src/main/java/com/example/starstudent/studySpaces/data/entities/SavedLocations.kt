package com.example.starstudent.studySpaces.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user's saved study space locations
@Entity(tableName = "saved_locations")
data class SavedLocations(
    @PrimaryKey(
        autoGenerate = true
    )
    val id : Int = 0,
    val user: String,
    val label: String,
    val longitude: Double,
    val latitude: Double
)
