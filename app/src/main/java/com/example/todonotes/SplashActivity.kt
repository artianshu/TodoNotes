package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreference : SharedPreferences

    //val sharedPreference: SharedPreferences = getSharedPreferences(PrefConstants().SHARED_PREFERENCE_NAME, PRIVATE_MODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreferences()
        checkLoginStatus()
    }

    private fun setupSharedPreferences() {
        sharedPreference = getSharedPreferences(PrefConstants().SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        var isLoggedin : Boolean = sharedPreference.getBoolean(PrefConstants().IS_LOGGED_IN, false)
        if(isLoggedin){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
