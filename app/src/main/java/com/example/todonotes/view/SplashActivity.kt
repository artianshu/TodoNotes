package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.todonotes.utils.AppConstants
import com.example.todonotes.R
import com.example.todonotes.onBoarding.OnBoardingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


class SplashActivity : AppCompatActivity(){

    lateinit var sharedPreference : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    val TAG:String = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences()
        checkLoginStatus()
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast

                Log.d(TAG, token)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }

    private fun setupSharedPreferences() {
            sharedPreference =
                getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }

        private fun checkLoginStatus() {
            val isBoardingSuccess=sharedPreference.getBoolean(AppConstants.ON_BOARDED_SUCCESSFULLY,false)
            var isLoggedin = sharedPreference.getBoolean(AppConstants.IS_LOGGED_IN, false)
            if (isLoggedin) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }else if(isBoardingSuccess) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
}
