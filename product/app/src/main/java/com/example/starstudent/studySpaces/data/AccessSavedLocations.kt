package com.example.starstudent.studySpaces.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.entities.SavedLocations

class AccessSavedLocations {

    private val savedLocationsDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication.instance
            ).savedLocationsDao()

    var savedLocationsList = listOf(SavedLocations(
        1,
        "",
        "Default",
        0.0,
        0.0
    ))

    suspend fun updateSavedLocation(
        id: Int,
        user: String,
        longitude: Double,
        latitude: Double
    ){
        savedLocationsDAO.updateSavedLocation(
            id,
            CurrentApplication.instance.user.email.getEmail(),
            longitude,
            latitude
        )
        getSavedLocations(user)
    }

    suspend fun updateSavedLocationLabel(
        id: Int,
        user: String,
        newLabel: String
    ){
        savedLocationsDAO.updateSavedLabel(
            id,
            CurrentApplication.instance.user.email.getEmail(),
            newLabel
        )
    }

    suspend fun getSavedLocations(user : String){
        savedLocationsList = savedLocationsDAO.getSavedLocations(
            user
        )
    }

    fun getCurrentSavedLocationsList(): List<SavedLocations>{
        return savedLocationsList
    }
}


