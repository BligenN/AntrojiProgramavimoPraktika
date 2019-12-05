package com.example.antrojiprogramavimopraktika.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.itemDatabase.shopRecyclerView.ShopAdapter
import com.example.antrojiprogramavimopraktika.menu.MenuFragment
import com.example.antrojiprogramavimopraktika.shopping_cart.CartFragment
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.toolbar_result.*

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    private val shopAdapter = ShopAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setup RecyclerView
        recycler_view_item_list.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_item_list.addItemDecoration(
            DividerItemDecoration(
                activity!!,
                OrientationHelper.VERTICAL
            )
        )
        recycler_view_item_list.adapter = shopAdapter

        //ADD DEFAULT SPINNER CATEGORIES
        spinner_category.adapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            arrayOf("All", "Shirts", "Pants", "Shoes")
        )

        val viewModel = ViewModelProviders.of(this).get(ResultFragmentViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.openMenu?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, MenuFragment())
                    .commit()
            }

            it.openCart?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, CartFragment())
                    .commit()
            }

            showLoadingScreen(it.showLoadingScreen)

            shopAdapter.setData(it.searchItems)
        })

        image_button_back.setOnClickListener { viewModel.backButtonPressed() }
        image_button_cart.setOnClickListener { viewModel.cartButtonPressed() }
        image_button_search.setOnClickListener {
            viewModel.searchButtonPressed(
                edit_text_price_min.text.toString().trim().toIntOrNull(),
                edit_text_price_max.text.toString().trim().toIntOrNull(),
                spinner_category.selectedItem.toString()
            )
        }
    }

    fun showLoadingScreen(value: Boolean) {
        //true - hide recycler show loading.
    }
}