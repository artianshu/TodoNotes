package com.example.todonotes.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.*

object StoreSession {
    private var sharedPreferences:SharedPreferences? = null

    fun init(context: Context){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        }
    }

    fun write(key:String, value:Boolean){
     val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun read(key: String) : Boolean? {
        return sharedPreferences?.getBoolean(key, false)
    }
}