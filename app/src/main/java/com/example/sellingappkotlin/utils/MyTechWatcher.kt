package com.example.sellingappkotlin.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.sellingappkotlin.R

class MyTechWatcher(val context: Context, var editText: EditText, var imageView: ImageView, var textView: TextView ) : TextWatcher {

    private val baseEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p0?.length!! > 0){
            imageView.visibility = View.VISIBLE
        }else{
            imageView.visibility = View.GONE
        }
        val email = p0.toString()
        if (email.matches(Regex(baseEmail))) {
            // Email is valid, you can update UI accordingly
            textView.visibility = View.GONE
        } else {
            // Email is invalid, show an error message
            textView.visibility = View.VISIBLE
            textView.text = context.getString(R.string.invalid_email_format)
        }

    }

    override fun afterTextChanged(p0: Editable?) {

    }
}