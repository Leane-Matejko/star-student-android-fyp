package com.example.starstudent.core.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseSingleton {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    val migrations = MIGRATION_5_6

    fun getDatabase(context: Context): AppDatabase{
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .addMigrations(migrations)
                .build()
            INSTANCE = instance
            instance
        }
    }
}