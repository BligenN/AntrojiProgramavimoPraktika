package com.example.antrojiprogramavimopraktika.loginDatabase

import androidx.room.*
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity

@Dao
interface LoginDAO {

    @Query("select * from LoginEntity")
    suspend fun getLogin(): List<LoginEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLogin(login: LoginEntity)
}