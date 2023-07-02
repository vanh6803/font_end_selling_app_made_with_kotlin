package com.example.sellingappkotlin.components.activities.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.components.activities.MainActivity
import com.example.sellingappkotlin.databinding.ActivityLoginBinding
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val backPressInterval  = 2000 //Time allowed between two back presses (2 seconds)
    private var doubleBackToExitPressedOnce: Boolean = false
    private var handle = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitleSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            finishAffinity() // clear all activities in the stack
            exitProcess(0) //Quit the app
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.press_back_one_more_time_to_exit), Toast.LENGTH_SHORT).show()
        handle.postDelayed({
            kotlin.run {
                doubleBackToExitPressedOnce = false
            }
        }, backPressInterval.toLong())
    }
}