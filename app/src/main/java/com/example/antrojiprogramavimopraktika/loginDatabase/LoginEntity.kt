package com.example.antrojiprogramavimopraktika.loginDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
    @PrimaryKey
    var username: String = "",

    var password: String = ""
)