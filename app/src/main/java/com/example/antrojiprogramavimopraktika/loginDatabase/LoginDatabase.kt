package com.example.antrojiprogramavimopraktika.loginDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LoginEntity::class],
    version = 1
)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDAO(): LoginDAO
}