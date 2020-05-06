package com.example.todonotes.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.todonotes.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    lateinit var textViewNext: TextView
    lateinit var onNextClick: OnNextClick
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick=context as OnNextClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
    //bind the widgets in this fun ,as we did in adapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BindView(view)

    }
    private fun clickListeners() {
        textViewNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onNextClick.onClick()

            }

        })
    }

    private fun BindView(view: View) {
        textViewNext=view.findViewById(R.id.textViewNext)
        clickListeners()
    }
    interface OnNextClick{
        fun onClick()
    }
}
