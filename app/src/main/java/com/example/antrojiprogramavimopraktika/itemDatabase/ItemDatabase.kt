package com.example.antrojiprogramavimopraktika.itemDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity

@Database(
    entities = [LoginEntity::class],
    version = 1
)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDAO(): ItemDAO
}