package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.studySpaces.data.entities.SavedLocations

//Data access object for saved locations
@Dao
interface SavedLocationsDAO {

    //Insert a new saved location
    @Insert
    suspend fun addInitialLocations(locations: SavedLocations)

    //Return list of saved locations
    @Query("""
        SELECT * 
        FROM saved_locations 
        WHERE user = :user
        """)
    suspend fun checkUserExists(
        user: String
    ) :  List<SavedLocations>

    //Return list of saved locations
    @Query("""
        SELECT * 
        FROM saved_locations 
        WHERE user = :user
        """)
    suspend fun getSavedLocations(
        user: String
    ) :  List<SavedLocations>

    //Update the longitude and latitude of an existing saved location
    @Query("""
        UPDATE saved_locations
        SET longitude = :longitude, latitude = :latitude
        WHERE id = :id AND user = :user
    """)
    suspend fun updateSavedLocation(
        id: Int,
        user: String,
        longitude: Double,
        latitude: Double
    )

    //Update the label of an existing saved location
    @Query("""
        UPDATE saved_locations
        SET label = :label
        WHERE id = :id
    """)
    suspend fun updateSavedLabel(
        id: Int,
        label: String
    )

}