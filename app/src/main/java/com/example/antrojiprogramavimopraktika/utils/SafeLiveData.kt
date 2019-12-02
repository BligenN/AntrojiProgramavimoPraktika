package com.example.android_party.utils

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

// TODO should be replaced when LiveData 2.1.0 released
class SafeLiveData<T>(initData: T) : MutableLiveData<T>() {

    init {
        value = initData
    }

    @Suppress("UnsafeCallOnNullableType")
    override fun getValue(): T = super.getValue()!!
}

class SafeMediatorLiveData<T>(initData: T) : MediatorLiveData<T>() {

    init {
        value = initData
    }

    @Suppress("UnsafeCallOnNullableType")
    override fun getValue(): T = super.getValue()!!
}
