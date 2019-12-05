package com.example.antrojiprogramavimopraktika.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent

class MenuFragmentViewModel : ViewModel() {
    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    fun shopButtonClicked(){
        _state.value = _state.value.copy(openShop = SimpleEvent())
    }

    fun cartButtonClicked(){
        _state.value = _state.value.copy(openCart = SimpleEvent())
    }

    fun logoutButtonClicked(){
        _state.value = _state.value.copy(logOut = SimpleEvent())
    }

    data class State(
        val openShop: SimpleEvent? = null,
        val openCart: SimpleEvent? = null,
        val logOut: SimpleEvent? = null
    )
}