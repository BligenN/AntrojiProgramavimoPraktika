package com.example.antrojiprogramavimopraktika.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.menu.MenuFragment
import com.example.antrojiprogramavimopraktika.register.RegisterFragment
import com.example.antrojiprogramavimopraktika.results.ResultFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.startRegisterFragment?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, RegisterFragment())
                    .commit()
            }

            it.startMenuFragment?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, MenuFragment())
                    .commit()
            }

            it.usernameEmpty?.getContentIfNotHandled()?.let {
                edit_text_username.error = "Please enter your username!"
                edit_text_username.requestFocus()
            }

            it.passwordEmpty?.getContentIfNotHandled()?.let {
                edit_text_password.error = "Please enter your password!"
                edit_text_password.requestFocus()
            }

            it.failedLogin?.getContentIfNotHandled()?.let {
                edit_text_password.setText("")
                edit_text_username.requestFocus()
                edit_text_username.error = "Invalid login credentials"
            }
        })

        text_view_register.setOnClickListener {
            viewModel.registerButtonClicked()
        }

        button_login.setOnClickListener {
            viewModel.loginButtonClicked(
                edit_text_username.text.toString().trim(),
                edit_text_password.text.toString().trim()
            )
        }
    }
}