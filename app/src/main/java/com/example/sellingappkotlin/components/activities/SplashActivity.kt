package com.example.sellingappkotlin.components.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.sellingappkotlin.components.activities.auth.LoginActivity
import com.example.sellingappkotlin.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var checkedClickImage: Boolean = false
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var delayRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        delayRunnable = Runnable {
            kotlin.run {
                if (!checkedClickImage){
                    nextActivity()
                }
            }
        }

        handler.postDelayed(delayRunnable, 2000)

        binding.logo.setOnClickListener {
            checkedClickImage = true
            handler.removeCallbacks(delayRunnable)
            nextActivity()
        }

    }

    private fun nextActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(delayRunnable)
    }
}