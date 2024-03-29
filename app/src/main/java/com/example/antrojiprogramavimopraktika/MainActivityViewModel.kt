package com.example.antrojiprogramavimopraktika

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent

class MainActivityViewModel : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    init{
        _state.value = _state.value.copy(startLoginFragment = SimpleEvent())
    }

    data class State(
        val startLoginFragment: SimpleEvent? = null
    )
}