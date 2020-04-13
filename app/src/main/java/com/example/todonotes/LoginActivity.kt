package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var FN:String?= null
    var UN:String?= null
    lateinit var fullName:EditText
    lateinit var username : EditText
    lateinit var login : Button


    lateinit var sharedPreferences : SharedPreferences

    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferencesSetup()
        BindView()

        login.setOnClickListener {
            if (fullName.text.toString() != " " && username.text.toString() != " "){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(AppConstants().Full_Name, fullName.text.toString())
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullName.text.toString())
            }
            else {
                Log.d("Nothing", "Not logged in")
            }
        }
    }

    private fun saveFullName(fullName : String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefConstants().FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstants().IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun sharedPreferencesSetup() {
        sharedPreferences = getSharedPreferences(PrefConstants().SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun BindView() {
        login = findViewById<Button>(R.id.bt_login)
        fullName = findViewById<EditText>(R.id.et_fullname)
        username = findViewById<EditText>(R.id.et_username)
    }
}
