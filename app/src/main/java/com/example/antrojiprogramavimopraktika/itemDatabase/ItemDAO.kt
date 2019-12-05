package com.example.antrojiprogramavimopraktika.itemDatabase

import androidx.room.*

@Dao
interface ItemDAO {

    @Query("select * from ItemEntity")
    suspend fun getItems(): List<ItemEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: ItemEntity)
}