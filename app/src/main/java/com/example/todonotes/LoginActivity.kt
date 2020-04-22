package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import android.view.View

class LoginActivity : AppCompatActivity() {

    lateinit var editTextFullName:EditText
    lateinit var editTextUserName : EditText
    lateinit var buttonLogin : Button

    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BindView()
        sharedPreferencesSetup()

    }

    private fun saveFullName(fullName : String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefConstants.FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstants.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun sharedPreferencesSetup() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun BindView() {
        buttonLogin = findViewById<Button>(R.id.bt_login)
        editTextFullName = findViewById<EditText>(R.id.et_fullname)
        editTextUserName = findViewById<EditText>(R.id.et_username)

        val clickAction = object: View.OnClickListener{
            override fun onClick(v: View?) {
                val fullname = editTextFullName.text.toString()
                val username = editTextUserName.text.toString()
                if(fullname.isNotEmpty() && username.isNotEmpty()){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(AppConstants.Full_Name, fullname)
                    startActivity(intent)
                    saveFullName(fullname)
                    saveLoginStatus()

                }
            }

        }
        buttonLogin.setOnClickListener(clickAction)
    }
}
