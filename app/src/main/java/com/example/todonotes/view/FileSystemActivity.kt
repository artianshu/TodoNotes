package com.example.todonotes.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.todonotes.R
import java.io.File

class FileSystemActivity : AppCompatActivity() {
    lateinit var textFileInput : TextView
    lateinit var textFileRead : TextView
    lateinit var buttonSave : Button
    lateinit var buttonRead : Button

    val FILE_NAME = "file.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_system)
        BindView()
        SaveText()
        ReadText()

    }

    private fun ReadText() {
        buttonRead.setOnClickListener {
            val file = File(filesDir, FILE_NAME)
            val textInFile = file.readText()
            textFileRead.text = textInFile
        }
    }

    private fun SaveText() {
        buttonSave.setOnClickListener{
            val Text = textFileInput.text.toString()
            val fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fileOutputStream.write(Text.toByteArray())
        }
    }

    private fun BindView() {
       textFileInput = findViewById<TextView>(R.id.tv_text_input)
       textFileRead = findViewById(R.id.tv_file_read)
       buttonSave = findViewById(R.id.btn_save)
       buttonRead = findViewById(R.id.btn_read)
    }
}
