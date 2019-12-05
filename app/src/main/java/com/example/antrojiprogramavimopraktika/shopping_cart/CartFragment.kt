package com.example.antrojiprogramavimopraktika.shopping_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.cartDatabase.cartRecyclerView.CartAdapter
import com.example.antrojiprogramavimopraktika.menu.MenuFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_results.recycler_view_item_list
import kotlinx.android.synthetic.main.toolbar_cart.*

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    private val cartAdapter = CartAdapter()

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
        recycler_view_item_list.adapter = cartAdapter

        val viewModel = ViewModelProviders.of(this).get(CartFragmentViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.openMenu?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, MenuFragment())
                    .commit()
            }

            it.purchaseComplete?.hasBeenHandled?.let {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Purchase succesful!",
                    Toast.LENGTH_LONG
                ).show()
            }

            showLoadingScreen(it.showLoadingScreen)

            cartAdapter.setData(it.items)
        })

        image_button_back.setOnClickListener { viewModel.backButtonPressed() }
        button_checkout.setOnClickListener { viewModel.checkoutButtonPressed() }
    }

    fun showLoadingScreen(value: Boolean) {

    }
}