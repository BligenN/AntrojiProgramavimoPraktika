package com.example.antrojiprogramavimopraktika.utils

import android.content.Context
import androidx.room.Room
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginDatabase
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenRepository private constructor(applicationContext: Context) {

    private var activeUser: LoginEntity? = null
    private var loginDatabase: LoginDatabase? = null

    init {
        this.loginDatabase =
            Room.databaseBuilder(applicationContext, LoginDatabase::class.java, "LoginDatabase")
                .build()
    }

    companion object {
        @Volatile
        private var INSTANCE: TokenRepository? = null

        fun getInstance(): TokenRepository {
            if (INSTANCE == null)
                throw AssertionError("You have to call initialize first")
            return INSTANCE!!
        }

        fun initialize(applicationContext: Context): TokenRepository {
            synchronized(this) {
                if (INSTANCE != null)
                    throw AssertionError("You already initialized this")
                INSTANCE = TokenRepository(applicationContext)
                return INSTANCE!!
            }
        }
    }

    fun saveLogin(value: LoginEntity) {
        CoroutineScope(IO).launch {
            loginDatabase!!.loginDAO().saveLogin(value)
        }
    }

    fun getActiveUser() = activeUser!!

    fun setActiveUser(value: LoginEntity) {
        activeUser = value
    }

    suspend fun getUserList() = withContext(IO) { loginDatabase!!.loginDAO().getLogin() }
}