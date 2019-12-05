package com.example.antrojiprogramavimopraktika.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.login.LoginFragment
import com.example.antrojiprogramavimopraktika.results.ResultFragment
import com.example.antrojiprogramavimopraktika.shopping_cart.CartFragment
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(MenuFragmentViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.openShop?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, ResultFragment())
                    .commit()
            }
            it.openCart?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, CartFragment())
                    .commit()
            }
            it.logOut?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, LoginFragment())
                    .commit()
            }

            button_shop.setOnClickListener { viewModel.shopButtonClicked() }
            button_shopping_cart.setOnClickListener { viewModel.cartButtonClicked() }
            button_logout.setOnClickListener { viewModel.logoutButtonClicked() }
        })
    }
}