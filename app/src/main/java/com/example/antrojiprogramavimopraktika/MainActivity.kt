package com.example.antrojiprogramavimopraktika

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.antrojiprogramavimopraktika.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.state.observe(this, Observer {
            it.startLoginFragment?.hasBeenHandled?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myFragment, LoginFragment())
                    .commit()
            }
        })
    }
}
