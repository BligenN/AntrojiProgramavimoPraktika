package com.example.antrojiprogramavimopraktika.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.loginDatabase.LoginEntity
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent
import com.example.antrojiprogramavimopraktika.utils.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterFragmentViewModel : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Main + parentJob)

    fun registerButtonClicked(username: String, password: String, passwordRepeat: String) {
        if (password != passwordRepeat)
            _state.value = _state.value.copy(passwordsDontMatch = SimpleEvent())
        else
            coroutineScope.launch(Main) {
                val userList = Repository.getInstance().getUserList()
                val usernameList = arrayListOf<String>()
                for (user in userList)
                    usernameList.add(user.username)
                if (usernameList.contains(username))
                    _state.value = _state.value.copy(usernameTaken = SimpleEvent())
                else if(password.length < 5)
                    _state.value = _state.value.copy(passwordsInvalid = SimpleEvent())
                else {
                    Repository.getInstance().saveLogin(LoginEntity(username, password))
                    _state.value = _state.value.copy(registrationSuccesful = SimpleEvent())
                }
            }
    }

    fun cancelButtonClicked() {
        _state.value = _state.value.copy(readyToClose = SimpleEvent())
    }

    data class State(
        val username: String = "",
        val password: String = "",
        val readyToClose: SimpleEvent? = null,
        val usernameTaken: SimpleEvent? = null,
        val passwordsInvalid: SimpleEvent? = null,
        val passwordsDontMatch: SimpleEvent? = null,
        val registrationSuccesful: SimpleEvent? = null
    )
}