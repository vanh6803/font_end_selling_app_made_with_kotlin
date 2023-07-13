package com.example.sellingappkotlin.components.activities.product

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.ProductAdapter
import com.example.sellingappkotlin.databinding.ActivityProductAsManufactureBinding
import com.example.sellingappkotlin.models.responseApi.ApiResponseProduct
import com.example.sellingappkotlin.models.Manufacturer
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.utils.ApiServiceSellingApp
import com.example.sellingappkotlin.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductAsManufactureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAsManufactureBinding
    private lateinit var manufacturer: Manufacturer
    private lateinit var listProduct: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAsManufactureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.extras?.getString("name")
        val logo = intent.extras?.getString("logo")
        val id = intent.extras?.getString("id")
        Log.d("AAA", id!!)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvTitle.text = name
        Glide.with(binding.root).load(logo?.replace("localhost", Config.LOCALHOST )).error(R.drawable.baseline_image_24)
            .into(binding.logo)

        callApi(id)
        initRefresh(id)
    }

    fun callApi(id: String){
        ApiServiceSellingApp.apiServiceSellingApp.getListProductFormManufacturers(id)
            .enqueue(object : Callback<ApiResponseProduct> {
                override fun onResponse(
                    call: Call<ApiResponseProduct>,
                    response: Response<ApiResponseProduct>
                ) {
                    val apiResponseProduct = response.body()
                    listProduct = apiResponseProduct!!.data
                    Log.d("data from api", "${listProduct.size}")
                    productAdapter = ProductAdapter(this@ProductAsManufactureActivity)
                    productAdapter.setData(listProduct)
                    binding.rcv.adapter = productAdapter
                }

                override fun onFailure(call: Call<ApiResponseProduct>, t: Throwable) {
                    Toast.makeText(
                        this@ProductAsManufactureActivity,
                        "call api fail due to ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("call api product", "${t.message}")
                }

            })
    }

    private fun initRefresh(id: String){
        binding.swipeRefresh.setOnRefreshListener {
            callApi(id)
            binding.swipeRefresh.isRefreshing = false
        }
    }

}