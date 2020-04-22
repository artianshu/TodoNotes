package com.example.todonotes

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    val tag = "Detail Activity"
    lateinit var textViewTitle : TextView
    lateinit var textViewDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        bindViews()
        setIntentData()
    }

    private fun setIntentData() {
        val intent = intent
        val title = intent.getStringExtra(AppConstants.Title)
        val description = intent.getStringExtra(AppConstants.Description)

        textViewTitle.text = title
        textViewDescription.text = description
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.tv_detail_title)
        textViewDescription = findViewById(R.id.tv_detail_desc)
    }
}