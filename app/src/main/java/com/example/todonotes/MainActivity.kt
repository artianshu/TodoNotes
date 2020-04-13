package com.example.todonotes

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.model.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

   // lateinit var txtTitle:TextView
   // lateinit var txtDesc:TextView
      lateinit var fabNotes : FloatingActionButton

     lateinit var recyclerView : RecyclerView
     var notesList : ArrayList<Notes> ?= null

    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSharedPreferences()
        BindView()
        getIntentData()

        fabNotes.setOnClickListener {
           setupDialogBox()
        }
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstants().SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        var fullname = intent.getStringExtra(AppConstants().Full_Name)
        if(TextUtils.isEmpty(fullname)){
            fullname = sharedPreferences.getString(PrefConstants().FULL_NAME, "Anshu")
            Log.d("fullName", sharedPreferences.getString(PrefConstants().FULL_NAME, "Anshu") )
        }
        supportActionBar!!.setTitle(fullname)

    }

    private fun BindView() {

        fabNotes = findViewById<FloatingActionButton>(R.id.fab_notes)
        recyclerView = findViewById<RecyclerView>(R.id.rvnotes)
     //  txtTitle = findViewById<TextView>(R.id.tv_title)
     //  txtDesc = findViewById<TextView>(R.id.tv_desc)
    }

    private fun setupDialogBox() {

        val view = LayoutInflater.from(this).inflate(R.layout.add_notes, null)

        var title = view.findViewById<EditText>(R.id.et_title)
        var desc = view.findViewById<EditText>(R.id.et_desc)
        var submit = view.findViewById<Button>(R.id.bt_submit)
        var dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        submit.setOnClickListener {
            var txtTitle = title.text.toString()
            var txtDesc : String = desc.text.toString()
            var notes : Notes ?= null
            notes?.title = txtTitle
            notes?.description = txtTitle
            notesList?.add(notes!!)
            println(notesList)

          // txtTitle?.setText(title.text.toString())
          // txtDesc?.setText(desc.text.toString())
            dialog.hide()
        }

        dialog.show()
    }
}
