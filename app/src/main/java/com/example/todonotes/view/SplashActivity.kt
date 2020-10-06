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
import com.example.todonotes.utils.StoreSession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId


class SplashActivity : AppCompatActivity(){
    private var signInButton: SignInButton? = null
    private var googleSignInClient : GoogleSignInClient? = null
    private val requestCode:Int = 1
    val TAG:String = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences()
        signInButton = findViewById(R.id.btn_google_sign_in)
        checkLoginStatus()
    }
    private fun setupSharedPreferences() { StoreSession.init(this)}
    private fun checkLoginStatus() {
        var isLoggedin = StoreSession.read(AppConstants.IS_LOGGED_IN)
        if (isLoggedin!!) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            googleOptions()
        }
      //  finish()
    }
    private fun googleOptions() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        signInButton?.setOnClickListener(View.OnClickListener {
            signIn()
            saveLoginStatus()
        })
    }
    private fun saveLoginStatus() { StoreSession.write(AppConstants.IS_LOGGED_IN, true) }

    private fun signIn() {
        var intent = googleSignInClient?.getSignInIntent()
        startActivityForResult(intent, requestCode)
    }
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (reqCode == requestCode){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            saveUserData(account)
            Toast.makeText(this, ""+StoreSession.readString(AppConstants.USER_NAME), Toast.LENGTH_SHORT).show()
            gotoMainActivity()
        }else{
            Toast.makeText(this, "Error in signing In", Toast.LENGTH_SHORT).show()
        }

    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveUserData(account: GoogleSignInAccount?) {
        StoreSession.write(AppConstants.USER_NAME, account?.displayName.toString())
        StoreSession.write(AppConstants.USER_EMAIL, account?.email.toString())
    }

}
