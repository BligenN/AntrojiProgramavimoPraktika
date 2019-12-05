package com.example.antrojiprogramavimopraktika.shopping_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.cartDatabase.CartEntity
import com.example.antrojiprogramavimopraktika.utils.Repository
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CartFragmentViewModel : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Main + parentJob)

    init {
        coroutineScope.launch(Main) {
            val cartList = Repository.getInstance().getCartItems()
            if (cartList.isNotEmpty()) {
                for(item in cartList){
                    if(item.username == Repository.getInstance().getActiveUser()){
                        _state.value = _state.value.copy(
                            items = cartList,
                            showLoadingScreen = false
                        )
                    }
                }
            }
        }
    }

    fun checkoutButtonPressed() {
        coroutineScope.launch(Main) {
            _state.value = _state.value.copy(showLoadingScreen = true)
            Repository.getInstance().clearCart()
            _state.value = _state.value.copy(
                items = listOf(),
                showLoadingScreen = false,
                purchaseComplete = SimpleEvent()
            )
        }
    }

    fun backButtonPressed() {
        _state.value = _state.value.copy(openMenu = SimpleEvent())
    }

    data class State(
        val items: List<CartEntity> = listOf(),
        val openMenu: SimpleEvent? = null,
        val showLoadingScreen: Boolean = true,
        val purchaseComplete: SimpleEvent? = null
    )
}