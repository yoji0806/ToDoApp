package com.yoji0806.to_doapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        edit_taskTitle.visibility = View.INVISIBLE


        var lowPosition = true

        fab.setOnClickListener {

            if (lowPosition) {
                edit_taskTitle.visibility = View.VISIBLE
                showSoftKeyboard(edit_taskTitle)
            }else{
                edit_taskTitle.visibility = View.INVISIBLE
                hideSoftKeyboard(edit_taskTitle)
            }

            lowPosition = !lowPosition
        }
    }


    private fun hideSoftKeyboard(view:View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    private fun showSoftKeyboard(view:View) {
        if (view.requestFocus())
        {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }



}





class MainAdapter
