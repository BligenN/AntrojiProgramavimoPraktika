package com.example.antrojiprogramavimopraktika.cartDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartEntity(
    var username: String = "",

    var name: String = "",

    var category: String = "",

    var price: Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var userID: Int = 0
}