package com.example.antrojiprogramavimopraktika

import android.app.Application
import com.example.antrojiprogramavimopraktika.utils.Repository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}