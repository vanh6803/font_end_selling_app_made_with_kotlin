package com.example.sellingappkotlin.components.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}