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

val MIGRATION_4_5 = object : Migration(4,5){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS saved_locations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "user TEXT NOT NULL," +
                "label TEXT NOT NULL," +
                "longitude REAL NOT NULL," +
                "latitude REAL NOT NULL" +
                ")")
    }
}

val MIGRATION_5_6 = object : Migration(5,6){
    override fun migrate(db: SupportSQLiteDatabase) {
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
            "completeDate INTEGER NOT NULL," +
            "isActive INTEGER NOT NULL DEFAULT 1," +
                    "FOREIGN KEY(cateId) REFERENCES task_categories(id)" +
            ")"
        )

        db.execSQL(
            "CREATE INDEX index_tasks_cateId ON tasks(cateId)"
        )
    }
}

val MIGRATION_6_7 = object : Migration(6,7){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user_theme (" +
                "id TEXT PRIMARY KEY NOT NULL," +
                "theme TEXT NOT NULL" +
                ")")
    }
}

val MIGRATION_7_8 = object : Migration(7,8){
    override fun migrate(db: SupportSQLiteDatabase) {
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
    }
}