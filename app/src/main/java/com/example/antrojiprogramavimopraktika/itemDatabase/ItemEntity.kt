package com.example.antrojiprogramavimopraktika.itemDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey
    var name: String = "",

    var description: String = "",

    var price: Int = 0
)