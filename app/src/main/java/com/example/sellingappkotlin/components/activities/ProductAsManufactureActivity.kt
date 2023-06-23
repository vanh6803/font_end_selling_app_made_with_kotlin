package com.example.sellingappkotlin.components.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivityProductAsManufactureBinding
import com.example.sellingappkotlin.models.Manufacturer
import com.example.sellingappkotlin.utils.Config

class ProductAsManufactureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAsManufactureBinding
    private lateinit var manufacturer: Manufacturer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAsManufactureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.extras?.getString("name")
        var logo = intent.extras?.getString("logo")

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvTitle.text = name
        Glide.with(binding.root).load(logo?.replace("localhost", Config.LOCALHOST )).error(R.drawable.baseline_image_24)
            .into(binding.logo)

    }
}