package com.example.starstudent.core.data

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/*
* All current database migrations, currently on version 9
*/
val MIGRATION_2_3 = object : Migration(2,3){

    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 2 → 3")
        db.execSQL("ALTER TABLE user_info ADD COLUMN locationAccess INTEGER NOT NULL DEFAULT 0")
        Log.d("MIGRATION", "Finished MIGRATION 2 → 3")
    }
}

val MIGRATION_3_4 = object : Migration(3,4){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 3 → 4")
        db.execSQL("CREATE TABLE IF NOT EXISTS study_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user TEXT NOT NULL," +
                "startTime INTEGER NOT NULL," +
                "endTime INTEGER NOT NULL" +
                ")")
        Log.d("MIGRATION", "Finished MIGRATION 3 → 4")
    }
}

val MIGRATION_4_5 = object : Migration(4,5){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 4 → 5")
        db.execSQL("CREATE TABLE IF NOT EXISTS paused_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "sessionId INTEGER NOT NULL," +
                "startTime INTEGER NOT NULL," +
                "endTime INTEGER NOT NULL" +
                ")")
        Log.d("MIGRATION", "Finished MIGRATION 4 → 5")
    }
}

val MIGRATION_5_6 = object : Migration(5,6){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 5 → 6")
        db.execSQL("CREATE TABLE IF NOT EXISTS saved_locations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user TEXT NOT NULL," +
                "label TEXT NOT NULL," +
                "longitude REAL NOT NULL," +
                "latitude REAL NOT NULL" +
                ")")
        Log.d("MIGRATION", "Finished MIGRATION 5 → 6")
    }
}

val MIGRATION_6_7 = object : Migration(6,7){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 6 → 7")
        db.execSQL("CREATE TABLE IF NOT EXISTS task_categories (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "user TEXT NOT NULL," +
            "cateLabel TEXT NOT NULL," +
            "labelColour TEXT NOT NULL," +
            "isActive INTEGER NOT NULL DEFAULT 1" +
            ")")

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS tasks (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "cateId INTEGER NOT NULL," +
            "taskLabel TEXT NOT NULL," +
            "isCritical INTEGER NOT NULL DEFAULT 0," +
            "dueDate INTEGER NOT NULL," +
            "isComplete INTEGER NOT NULL DEFAULT 0," +
            "completeDate INTEGER NOT NULL DEFAULT 0," +
            "isActive INTEGER NOT NULL DEFAULT 1," +
                    "FOREIGN KEY(cateId) REFERENCES task_categories(id)" +
            ")"
        )

        db.execSQL(
            "CREATE INDEX index_tasks_cateId ON tasks(cateId)"
        )
        Log.d("MIGRATION", "Finished MIGRATION 6 → 7")
    }
}

val MIGRATION_7_8 = object : Migration(7,8){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 7 → 8")
        db.execSQL("CREATE TABLE IF NOT EXISTS user_theme (" +
                "id TEXT PRIMARY KEY NOT NULL," +
                "theme TEXT NOT NULL" +
                ")")
        Log.d("MIGRATION", "Finished MIGRATION 7 → 8")
    }
}

val MIGRATION_8_9 = object : Migration(8,9){
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("MIGRATION", "Running MIGRATION 8 → 9")
        db.execSQL("CREATE TABLE IF NOT EXISTS homepage_settings (" +
                "id TEXT PRIMARY KEY NOT NULL," +
                "avatarWindow INTEGER NOT NULL DEFAULT 1," +
                "studyProgress INTEGER NOT NULL DEFAULT 1," +
                "sleepProgress INTEGER NOT NULL DEFAULT 1," +
                "overdueTasks INTEGER NOT NULL DEFAULT 1," +
                "weeklyTasks INTEGER NOT NULL DEFAULT 1," +
                "allTasks INTEGER NOT NULL DEFAULT 1," +
                "studyCentreNav INTEGER NOT NULL DEFAULT 1," +
                "plannerNav INTEGER NOT NULL DEFAULT 1," +
                "taskListNav INTEGER NOT NULL DEFAULT 1," +
                "historyNav INTEGER NOT NULL DEFAULT 1," +
                "profileNav INTEGER NOT NULL DEFAULT 1" +
                ")")
        Log.d("MIGRATION", "Finished MIGRATION 8 → 9")
    }
}