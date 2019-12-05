package com.example.antrojiprogramavimopraktika.itemDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey
    var name: String = "",

    var category: String = "",

    var price: Int = 0
)