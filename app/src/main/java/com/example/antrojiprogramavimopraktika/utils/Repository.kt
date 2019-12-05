package com.example.antrojiprogramavimopraktika.utils

import android.content.Context
import androidx.room.Room
import com.example.antrojiprogramavimopraktika.cartDatabase.CartDatabase
import com.example.antrojiprogramavimopraktika.cartDatabase.CartEntity
import com.example.antrojiprogramavimopraktika.itemDatabase.ItemDatabase
import com.example.antrojiprogramavimopraktika.itemDatabase.ItemEntity
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginDatabase
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository private constructor(applicationContext: Context) {

    private var loginDatabase: LoginDatabase? = null
    private var itemDatabase: ItemDatabase? = null
    private var cartDatabase: CartDatabase? = null
    private var activeUser: String? = null

    init {
        this.loginDatabase =
            Room.databaseBuilder(applicationContext, LoginDatabase::class.java, "LoginDatabase")
                .build()
        this.itemDatabase =
            Room.databaseBuilder(applicationContext, ItemDatabase::class.java, "ItemDatabase")
                .build()
        this.cartDatabase =
            Room.databaseBuilder(applicationContext, CartDatabase::class.java, "CartDatabase")
                .build()
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(): Repository {
            if (INSTANCE == null)
                throw AssertionError("You have to call initialize first")
            return INSTANCE!!
        }

        fun initialize(applicationContext: Context): Repository {
            synchronized(this) {
                if (INSTANCE != null)
                    throw AssertionError("You already initialized this")
                INSTANCE = Repository(applicationContext)
                return INSTANCE!!
            }
        }
    }

    fun saveLogin(value: LoginEntity) {
        CoroutineScope(IO).launch {
            loginDatabase!!.loginDAO().saveLogin(value)
        }
    }

    suspend fun getUserList() = withContext(IO) { loginDatabase!!.loginDAO().getLogin() }

    fun saveItem(value: ItemEntity) {
        CoroutineScope(IO).launch {
            itemDatabase!!.itemDAO().saveItem(value)
        }
    }

    suspend fun getItemList() = withContext(IO) { itemDatabase!!.itemDAO().getItems() }

    fun saveCartItem(value: CartEntity) {
        CoroutineScope(IO).launch {
            cartDatabase!!.cartDAO().saveCartItem(value)
        }
    }

    suspend fun getCartItems() = withContext(IO) { cartDatabase!!.cartDAO().getCartItems() }

    suspend fun deleteCartItem(item: CartEntity) =
        withContext(IO) { cartDatabase!!.cartDAO().deleteCartItem(item) }

    suspend fun clearCart() = withContext(IO) { cartDatabase!!.cartDAO().clearCart() }

    fun setActiveUser(value: String) {
        activeUser = value
    }

    fun getActiveUser(): String = activeUser!!
}