package com.example.antrojiprogramavimopraktika.cartDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDAO {

    @Query("select * from CartEntity")
    suspend fun getCartItems(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCartItem(item: CartEntity)

    @Delete
    suspend fun deleteCartItem(item: CartEntity)

    @Query("DELETE FROM CartEntity")
    suspend fun clearCart()
}