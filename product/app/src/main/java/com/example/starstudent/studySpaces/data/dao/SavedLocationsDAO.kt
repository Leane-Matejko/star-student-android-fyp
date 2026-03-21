package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.studySpaces.data.entities.SavedLocations

@Dao
interface SavedLocationsDAO {

    @Insert
    suspend fun addInitialLocations(locations: SavedLocations)

    @Query("""
        SELECT * 
        FROM saved_locations 
        WHERE user = :user
        """)
    suspend fun checkUserExists(
        user: String
    ) :  List<SavedLocations>

    @Query("""
        SELECT * 
        FROM saved_locations 
        WHERE user = :user
        """)
    suspend fun getSavedLocations(
        user: String
    ) :  List<SavedLocations>

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

    @Query("""
        UPDATE saved_locations
        SET label = :label
        WHERE id = :id AND user = :user
    """)
    suspend fun updateSavedLabel(
        id: Int,
        user: String,
        label: String
    )

}