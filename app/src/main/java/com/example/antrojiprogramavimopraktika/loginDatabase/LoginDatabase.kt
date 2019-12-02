package com.example.android_party.loginDisplay.loginDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LoginEntity::class],
    version = 3
)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDAO(): LoginDAO
}