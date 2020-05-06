package com.example.todonotes.onBoarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.todonotes.R
import com.example.todonotes.utils.AppConstants
import com.example.todonotes.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), FirstFragment.OnNextClick, SecondFragment.OnOptionClick {
    lateinit var viewPager: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        BindView()
        SetupSharedPrefences()
    }

    private fun SetupSharedPrefences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun BindView() {
        viewPager = findViewById(R.id.ViewPager)
        val adapter=FragmentAdapter(supportFragmentManager)
        viewPager.adapter=adapter
    }
    override fun onClick() {
        viewPager.currentItem=1
    }

    override fun onOptionDone() {
        //2nd Fragment
        editor=sharedPreferences.edit()
        editor.putBoolean(AppConstants.ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()
        val intent= Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionBack() {
        viewPager.currentItem=0
    }
}
