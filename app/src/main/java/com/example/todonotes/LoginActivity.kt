package com.example.todonotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var login = findViewById<Button>(R.id.bt_login)
        var fullName = findViewById<EditText>(R.id.et_fullname)
        var username = findViewById<EditText>(R.id.et_username)

        var FN = fullName.text
        var UN = username.text

        login.setOnClickListener {
            if (!FN.isEmpty() && !UN.isEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            else {
                Log.d("Nothing", "Not logged in")
            }
        }
    }
}
