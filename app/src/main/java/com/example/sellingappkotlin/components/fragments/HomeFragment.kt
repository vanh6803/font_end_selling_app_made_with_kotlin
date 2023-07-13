package com.example.sellingappkotlin.components.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.HotProductAdapter
import com.example.sellingappkotlin.adapters.ManufacturerAdapter
import com.example.sellingappkotlin.adapters.ProductAdapter
import com.example.sellingappkotlin.components.activities.product.SearchActivity
import com.example.sellingappkotlin.components.activities.user.ProfileActivity
import com.example.sellingappkotlin.databinding.FragmentHomeBinding
import com.example.sellingappkotlin.models.responseApi.ApiResponseManufacturer
import com.example.sellingappkotlin.models.responseApi.ApiResponseProduct
import com.example.sellingappkotlin.models.HotProduct
import com.example.sellingappkotlin.models.Manufacturer
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.utils.ApiServiceSellingApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    //hot products
    private lateinit var hotProducts: MutableList<HotProduct>
    private lateinit var hotProductAdapter: HotProductAdapter

    //product
    private lateinit var listProduct: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter

    //manufacture
    private lateinit var listManufacture: MutableList<Manufacturer>
    private lateinit var manufacturerAdapter: ManufacturerAdapter


    private var handle = Handler(Looper.getMainLooper())
    private var runnable = Runnable {
        kotlin.run {
            if (binding.viewpager2IntroHotProduct.currentItem == hotProducts.size - 1) {
                binding.viewpager2IntroHotProduct.currentItem = 0
            } else {
                binding.viewpager2IntroHotProduct.currentItem =
                    binding.viewpager2IntroHotProduct.currentItem + 1
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        slideImageHotProduct()
        showProducts()
        showManufactures()
        showActivitySearch()
        initRefresh()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    private fun initView() {
        binding.imgProfile.setOnClickListener{
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
    }

    private fun showListHotProducts(): MutableList<HotProduct> {
        val list = mutableListOf<HotProduct>()
        list.add(HotProduct(R.drawable.image_test, "siêu sale giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sale giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sale giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sale giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sale giảm \ngiá tận 20%"))
        return list
    }

    private fun slideImageHotProduct() {
        hotProducts = showListHotProducts()
        hotProductAdapter = HotProductAdapter(requireContext())
        hotProductAdapter.addData(hotProducts)
        binding.viewpager2IntroHotProduct.adapter = hotProductAdapter
        binding.circleIndicator.setViewPager(binding.viewpager2IntroHotProduct)
        binding.viewpager2IntroHotProduct.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handle.removeCallbacks(runnable)
                handle.postDelayed(runnable, 3000)
            }
        })
    }

    private fun showProducts() {
        listProduct = mutableListOf()
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.rcvProducts.layoutManager = layoutManager
        calApiRequestProducts()
    }

    private fun calApiRequestProducts() {
        binding.layoutLoading.visibility = View.VISIBLE
        ApiServiceSellingApp.apiServiceSellingApp.getListProduct()
            .enqueue(object : Callback<ApiResponseProduct> {
                override fun onResponse(
                    call: Call<ApiResponseProduct>,
                    response: Response<ApiResponseProduct>
                ) {
                    binding.layoutLoading.visibility = View.GONE
                    val apiResponseProduct = response.body()
                    listProduct = apiResponseProduct!!.data
                    Log.d("data from api", "${listProduct.size}")
                    productAdapter = ProductAdapter(requireContext())
                    productAdapter.setData(listProduct)
                    binding.rcvProducts.adapter = productAdapter
                }

                override fun onFailure(call: Call<ApiResponseProduct>, t: Throwable) {
                    Toast.makeText(
                        requireActivity(),
                        "call api fail due to ${t.message} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("call api product", "${t.message}")
                }

            })
    }

    private fun showManufactures() {
        listManufacture = mutableListOf()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvManufacturer.layoutManager = layoutManager
        callApiRequestManufactures()
    }

    private fun callApiRequestManufactures() {
        ApiServiceSellingApp.apiServiceSellingApp.getListManufacturers()
            .enqueue(object : Callback<ApiResponseManufacturer> {
                override fun onResponse(
                    call: Call<ApiResponseManufacturer>,
                    response: Response<ApiResponseManufacturer>
                ) {
                    val apiResponseManufacturer = response.body()
                    listManufacture = apiResponseManufacturer!!.data
                    Log.d("data from api", "${listProduct.size}")
                    manufacturerAdapter = ManufacturerAdapter(requireContext())
                    manufacturerAdapter.setData(listManufacture)
                    binding.rcvManufacturer.adapter = manufacturerAdapter
                }

                override fun onFailure(call: Call<ApiResponseManufacturer>, t: Throwable) {
                    Toast.makeText(
                        requireActivity(),
                        "call api fail due to ${t.message} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("call api product", "${t.message}")
                }

            })
    }

    private fun showActivitySearch() {
        binding.edtSearch.setOnClickListener {
            nextActivitySearch()
        }
    }

    private fun nextActivitySearch() {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        startActivity(intent)
    }

    private fun initRefresh(){
        binding.layoutRefresh.setOnRefreshListener {
            slideImageHotProduct()
            showProducts()
            showManufactures()
            showActivitySearch()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding == null
    }
}