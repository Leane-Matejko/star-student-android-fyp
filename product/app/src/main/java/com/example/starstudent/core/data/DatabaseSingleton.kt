package com.example.starstudent.core.data

import android.content.Context
import androidx.room.Room

object DatabaseSingleton {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    val migrations = arrayOf(
        MIGRATION_2_3,
        MIGRATION_3_4,
        MIGRATION_4_5,
        MIGRATION_5_6,
        MIGRATION_6_7,
        MIGRATION_7_8,
        MIGRATION_8_9,
    )

    fun getDatabase(context: Context): AppDatabase{
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .addMigrations(*migrations)
                .build()
            INSTANCE = instance
            instance
        }
    }
}