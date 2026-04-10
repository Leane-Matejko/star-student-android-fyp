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

    //Default saved location
    var savedLocationsList = listOf(SavedLocations(
        1,
        "",
        "Default",
        0.0,
        0.0
    ))

    //Update the longitude and latitude of a saved location
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

    //Update the label of the saved location
    suspend fun updateSavedLocationLabel(
        id: Int,
        newLabel: String
    ){
        savedLocationsDAO.updateSavedLabel(
            id,
            newLabel
        )
    }

    //Update saved location list of a user
    suspend fun getSavedLocations(user : String){
        savedLocationsList = savedLocationsDAO.getSavedLocations(
            user
        )
    }

    //Return the saved locations
    fun getCurrentSavedLocationsList(): List<SavedLocations>{
        return savedLocationsList
    }
}


