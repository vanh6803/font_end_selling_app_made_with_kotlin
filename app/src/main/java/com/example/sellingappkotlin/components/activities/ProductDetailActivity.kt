package com.example.sellingappkotlin.components.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}