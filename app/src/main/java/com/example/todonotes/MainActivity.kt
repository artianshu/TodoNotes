package com.example.todonotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fabNotes = findViewById<FloatingActionButton>(R.id.fab_notes)

        fabNotes.setOnClickListener {
            Log.d("Fab", "Fab clicked")
        }
    }
}
