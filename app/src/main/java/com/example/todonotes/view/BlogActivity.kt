package com.example.todonotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotes.R
import com.example.todonotes.adapter.BlogsAdapter
import com.example.todonotes.model.JSONResponse

class BlogActivity : AppCompatActivity() {

    lateinit var recyclerViewBlogs: RecyclerView
    val TAG="BlogActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        BindView()
        GetBlogs()
    }

    private fun GetBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(JSONResponse::class.java, object :
                ParsedRequestListener<JSONResponse> {
                override fun onResponse(response: JSONResponse?) {
                    setUpBlogRecyclerView(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d(TAG, anError?.localizedMessage)
                }

            })
    }

    private fun setUpBlogRecyclerView(response: JSONResponse?) {
        val blogAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(recyclerViewBlogs.context,linearLayoutManager.orientation)
        recyclerViewBlogs.addItemDecoration(dividerItemDecoration)
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }

    private fun BindView() {
        recyclerViewBlogs = findViewById(R.id.rc_blog)
    }
}
