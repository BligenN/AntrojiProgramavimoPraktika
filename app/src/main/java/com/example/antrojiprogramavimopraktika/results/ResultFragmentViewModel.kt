package com.example.antrojiprogramavimopraktika.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.antrojiprogramavimopraktika.itemDatabase.ItemEntity
import com.example.antrojiprogramavimopraktika.utils.Repository
import com.example.antrojiprogramavimopraktika.utils.SafeLiveData
import com.example.antrojiprogramavimopraktika.utils.SimpleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResultFragmentViewModel : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Main + parentJob)

    init {
        coroutineScope.launch(Main) {
            //SIMULATION PURPOSES - FILL DATABASE
            Repository.getInstance().saveItem(ItemEntity("Blue shirt", "Shirts", 399))
            Repository.getInstance().saveItem(ItemEntity("Red shirt", "Shirts", 200))
            Repository.getInstance().saveItem(ItemEntity("Blue jeans", "Pants", 399))
            Repository.getInstance().saveItem(ItemEntity("Adidas sneakers", "Shoes", 399))
            //SIMULATION PURPOSES - FILL DATABASE
            val itemList = Repository.getInstance().getItemList()
            if (itemList.isNotEmpty()) {
                _state.value = _state.value.copy(
                    items = itemList,
                    searchItems = itemList,
                    showLoadingScreen = false
                )
            }
        }
    }

    fun backButtonPressed() {
        _state.value = _state.value.copy(openMenu = SimpleEvent())
    }
    fun cartButtonPressed() {
        _state.value = _state.value.copy(openCart = SimpleEvent())
    }
    fun searchButtonPressed(minPrice: Int?, maxPrice: Int?, category: String) {
        _state.value = _state.value.copy(showLoadingScreen = true)
        val filteredByCategory: MutableList<ItemEntity> = mutableListOf()
        if (category != "All") {
            for (item in _state.value.items) {
                if (item.category == category)
                    filteredByCategory.add(item)
            }
        }
        else
            for(item in _state.value.items)
                filteredByCategory.add(item)
        val filteredList: MutableList<ItemEntity> = mutableListOf()
        when {
            minPrice != null && maxPrice != null -> {
                for (item in filteredByCategory)
                    if (item.price > minPrice && item.price < maxPrice)
                        filteredList.add(item)
            }
            minPrice != null && maxPrice == null -> {
                for (item in filteredByCategory)
                    if (item.price > minPrice)
                        filteredList.add(item)
            }
            minPrice == null && maxPrice != null -> {
                for (item in filteredByCategory)
                    if (item.price < maxPrice)
                        filteredList.add(item)
            }
            else -> {
                for (item in filteredByCategory)
                    filteredList.add(item)
            }
        }
        _state.value = _state.value.copy(searchItems = filteredList)
    }

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    data class State(
        val items: List<ItemEntity> = listOf(),
        val searchItems: List<ItemEntity> = listOf(),
        val openMenu: SimpleEvent? = null,
        val openCart: SimpleEvent? = null,
        val showLoadingScreen: Boolean = true
    )
}