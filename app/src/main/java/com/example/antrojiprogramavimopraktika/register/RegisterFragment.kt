package com.example.antrojiprogramavimopraktika.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(RegisterFragmentViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.readyToClose?.hasBeenHandled?.let {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, LoginFragment())
                    .commit()
            }
            it.passwordsDontMatch?.hasBeenHandled?.let {
                edit_text_password.setText("")
                edit_text_password_repeat.setText("")
                edit_text_username.requestFocus()
                edit_text_username.error = "Passwords don't match"
            }

            it.usernameTaken?.hasBeenHandled?.let {
                edit_text_password.setText("")
                edit_text_password_repeat.setText("")
                edit_text_username.requestFocus()
                edit_text_username.error = "Username is taken"
            }

            it.registrationSuccesful?.hasBeenHandled?.let {
                Toast.makeText(activity!!.applicationContext, "Registration successful", Toast.LENGTH_LONG).show()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.myFragment, LoginFragment())
                    .commit() }

            it.passwordsInvalid?.hasBeenHandled?.let {
                edit_text_password.setText("")
                edit_text_password_repeat.setText("")
                edit_text_username.requestFocus()
                edit_text_username.error = "Password is invalid"
            }
        })

        button_register.setOnClickListener {
            viewModel.registerButtonClicked(
                edit_text_username.text.toString().trim(),
                edit_text_password.text.toString().trim(),
                edit_text_password_repeat.text.toString().trim()
            )
        }

        button_cancel.setOnClickListener {
            viewModel.cancelButtonClicked()
        }
    }
}