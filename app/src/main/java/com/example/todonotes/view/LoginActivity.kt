package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.todonotes.utils.AppConstants
import com.example.todonotes.R
import com.example.todonotes.utils.StoreSession

class LoginActivity : AppCompatActivity() {

    lateinit var editTextFullName:EditText
    lateinit var editTextUserName : EditText
    lateinit var buttonLogin : Button

    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferencesSetup()
        BindView()

    }

    private fun saveFullName(fullName : String) {
        editor = sharedPreferences.edit()
        editor.putString(AppConstants.Full_Name, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(AppConstants.IS_LOGGED_IN, true)
        editor.apply()
      //  StoreSession.write(AppConstants.IS_LOGGED_IN, true)
    }

    private fun sharedPreferencesSetup() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        //StoreSession.init(this)
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

        val name = sharedPreferences.getString(AppConstants.Full_Name,"None")

        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show()

    }
}
