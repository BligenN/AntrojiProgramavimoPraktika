package com.example.antrojiprogramavimopraktika.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity
import com.example.antrojiprogramavimopraktika.utils.Repository
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Main + parentJob)

    fun registerButtonClicked() {
        _state.value = _state.value.copy(startRegisterFragment = SimpleEvent())
    }

    fun loginButtonClicked(username: String, password: String) {
        _state.value = _state.value.copy(username = username, password = password)
        when {
            _state.value.username.isEmpty() -> {
                _state.value = _state.value.copy(usernameEmpty = SimpleEvent())
            }
            _state.value.password.isEmpty() -> {
                _state.value = _state.value.copy(passwordEmpty = SimpleEvent())
            }
            else -> attemptLogin(_state.value.username, _state.value.password)
        }
    }

    private fun attemptLogin(username: String, password: String) {
        coroutineScope.launch(Main) {
            if (Repository.getInstance().getUserList().contains(
                    LoginEntity(
                        username,
                        password
                    )
                )
            ) {
                _state.value = _state.value.copy(startMenuFragment = SimpleEvent())
                Repository.getInstance().setActiveUser(username)
            } else
                _state.value = _state.value.copy(failedLogin = SimpleEvent())
        }
    }

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    data class State(
        val username: String = "",
        val password: String = "",
        val startRegisterFragment: SimpleEvent? = null,
        val startMenuFragment: SimpleEvent? = null,
        val usernameEmpty: SimpleEvent? = null,
        val passwordEmpty: SimpleEvent? = null,
        val failedLogin: SimpleEvent? = null
    )

}