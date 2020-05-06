package com.example.todonotes.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.todonotes.R

class SecondFragment : Fragment() {
    lateinit var textViewDone: TextView
    lateinit var textViewBack: TextView
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick=context as OnOptionClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BindView(view)
    }

    private fun BindView(view: View) {
        textViewDone = view.findViewById(R.id.textViewDone)
        textViewBack = view.findViewById(R.id.textViewBack)
        clickListener()

    }

    private fun clickListener() {
        textViewBack.setOnClickListener { onOptionClick.onOptionBack() }
        textViewDone.setOnClickListener { onOptionClick.onOptionDone() }
    }

    interface OnOptionClick {
        fun onOptionDone()
        fun onOptionBack()
    }
}