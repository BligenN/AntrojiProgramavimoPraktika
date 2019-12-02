package com.example.android_party.loginDisplay.loginDatabase

import androidx.room.*

@Dao
interface LoginDAO {

    @Query("select * from LoginEntity")
    suspend fun getLogin(): List<LoginEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLogin(login: LoginEntity)

    @Query("DELETE FROM LoginEntity")
    suspend fun deleteAll()
}