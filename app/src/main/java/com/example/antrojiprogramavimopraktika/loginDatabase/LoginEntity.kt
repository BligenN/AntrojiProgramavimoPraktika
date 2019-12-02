package com.example.android_party.loginDisplay.loginDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
    @PrimaryKey
    var token: String = ""
)