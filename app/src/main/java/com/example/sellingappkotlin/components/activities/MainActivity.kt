package com.example.sellingappkotlin.components.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Adapter
import android.widget.Toast
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.BottomNavAdapter
import com.example.sellingappkotlin.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val backPressInterval  = 2000 //Time allowed between two back presses (2 seconds)
    private var doubleBackToExitPressedOnce: Boolean = false
    private var handle = Handler(Looper.getMainLooper())
    private lateinit var bottomNavAdapter: BottomNavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavAdapter = BottomNavAdapter(this)
        binding.viewpager2.adapter = bottomNavAdapter
        binding.viewpager2.isUserInputEnabled = false
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_home -> binding.viewpager2.setCurrentItem(0, false)
                R.id.item_cart -> binding.viewpager2.setCurrentItem(1, false)
                R.id.item_notification -> binding.viewpager2.setCurrentItem(2, false)
                R.id.item_person -> binding.viewpager2.setCurrentItem(3, false)
            }
            true
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