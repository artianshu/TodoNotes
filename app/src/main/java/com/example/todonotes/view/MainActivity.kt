package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotes.NotesApp
import com.example.todonotes.utils.AppConstants
import com.example.todonotes.R
import com.example.todonotes.adapter.NotesAdapter
import com.example.todonotes.clickListeners.ItemClickListener
import com.example.todonotes.db.Notes
import com.example.todonotes.utils.StoreSession
import com.example.todonotes.workmanager.Myworker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var fullName: String?=null
    lateinit var fabNotes : FloatingActionButton
    val TAG = "myNotesActivity"
    val ADD_NOTES_CODE = 100
    lateinit var recyclerView : RecyclerView
    var notesList = ArrayList<com.example.todonotes.db.Notes>()
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindView()
        setupSharedPreferences()
        getIntentData()
        getDataFromDB()
        setuprecyclerView()
        setupWorkManager()
        supportActionBar?.title = fullName
        fabNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               // setupDialogBox()
                val intent=Intent(this@MainActivity,AddNotesActivity::class.java)
                startActivityForResult(intent,ADD_NOTES_CODE)
            }

        })
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                                    .build()
        val workRequest = PeriodicWorkRequest
            .Builder(Myworker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
       // StoreSession.init(this)
    }

    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(AppConstants.Full_Name)) {
            fullName = intent.getStringExtra(AppConstants.Full_Name)
        }
        if(fullName == null){
            fullName = sharedPreferences.getString(AppConstants.Full_Name, "Anshu")?:""
        }
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
                  val notes = com.example.todonotes.db.Notes(title = title, description = desc)
                    notesList.add(notes)
                    addNotesToDB(notes)
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

    private fun addNotesToDB(notes: com.example.todonotes.db.Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun getDataFromDB() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        val noteList = notesDao.getAll()
        notesList.addAll(noteList)
    }

    private fun setuprecyclerView() {
        val itemClickListener = object : ItemClickListener{
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(AppConstants.Title, notes.title)
                intent.putExtra(AppConstants.Description, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

        }

        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = notesAdapter
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==ADD_NOTES_CODE){
            val title=data?.getStringExtra(AppConstants.Title)
            val description=data?.getStringExtra(AppConstants.Description)
            val imagePath=data?.getStringExtra(AppConstants.IMAGE_PATH)
            //Add into DB
            val notes=Notes(title=title!!,description = description!!,imagePath = imagePath!!,isTaskCompleted = false)
            addNotesToDB(notes)
            notesList.add(notes)
            recyclerView.adapter?.notifyItemChanged(notesList.size-1)
        }

    }

    //create menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.blog){
            val intent=Intent(this@MainActivity,BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
