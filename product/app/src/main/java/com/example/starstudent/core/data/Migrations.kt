package com.example.starstudent.core.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user_info ADD COLUMN locationAccess INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS study_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user TEXT NOT NULL," +
                "startTime INTEGER NOT NULL," +
                "endTime INTEGER NOT NULL" +
                ")")
    }
}

val MIGRATION_3_4 = object : Migration(3,4){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS paused_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "sessionId INTEGER NOT NULL," +
                "startTime INTEGER NOT NULL," +
                "endTime INTEGER NOT NULL" +
                ")")
    }
}