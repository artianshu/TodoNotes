package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.adapter.NotesAdapter
import com.example.todonotes.clickListeners.ItemClickListener
import com.example.todonotes.model.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var fullName : String
    lateinit var fabNotes : FloatingActionButton
    val TAG = "myNotesActivity"

    lateinit var recyclerView : RecyclerView
    var notesList = ArrayList<Notes>()

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindView()
        setupSharedPreferences()
     //   getIntentData()

        fabNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setupDialogBox()
            }

        })
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        val intent = intent
        //if(intent.hasExtra(AppConstants.Full_Name)) {
            fullName = intent.getStringExtra(AppConstants.Full_Name)

        if(fullName.isEmpty()){
            fullName = sharedPreferences.getString(PrefConstants.FULL_NAME, "Anshu").toString()
        }
        supportActionBar?.title = fullName

    }

    private fun BindView() {

        fabNotes = findViewById<FloatingActionButton>(R.id.fab_notes)
        recyclerView = findViewById<RecyclerView>(R.id.rvnotes)

    }

    private fun setupDialogBox() {

        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.add_notes, null)

        var txtTitle = view.findViewById<EditText>(R.id.et_title)
        var txtDesc = view.findViewById<EditText>(R.id.et_desc)
        var submit = view.findViewById<Button>(R.id.bt_submit)
        var dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        submit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title = txtTitle.text.toString()
                val desc = txtDesc.text.toString()
                if(title.isNotEmpty() && desc.isNotEmpty()){
                  val notes = Notes(title, desc)
                    notesList.add(notes)
                }
                else{
                    Toast.makeText(this@MainActivity, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
                }
                setuprecyclerView()
                dialog.hide()
            }

        })
        dialog.show()
    }

    private fun setuprecyclerView() {
        val itemClickListener = object : ItemClickListener{
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(AppConstants.Title, notes.title)
                intent.putExtra(AppConstants.Description, notes.description)
                startActivity(intent)
            }

        }

        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = notesAdapter
    }
}
