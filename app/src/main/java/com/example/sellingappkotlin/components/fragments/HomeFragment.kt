package com.example.sellingappkotlin.components.fragments

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
import androidx.viewpager2.widget.ViewPager2
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.HotProductAdapter
import com.example.sellingappkotlin.adapters.ProductAdapter
import com.example.sellingappkotlin.databinding.FragmentHomeBinding
import com.example.sellingappkotlin.models.ApiResponseProduct
import com.example.sellingappkotlin.models.HotProduct
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //hot products
    private lateinit var hotProducts: MutableList<HotProduct>
    private lateinit var hotProductAdapter: HotProductAdapter

    //product
    private lateinit var listProduct: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter

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
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slideImageHotProduct()
        showProducts()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    private fun showListHotProducts(): MutableList<HotProduct> {
        val list = mutableListOf<HotProduct>()
        list.add(HotProduct(R.drawable.image_test, "siêu sell giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sell giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sell giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sell giảm \ngiá tận 20%"))
        list.add(HotProduct(R.drawable.image_test, "siêu sell giảm \ngiá tận 20%"))
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
        ApiService.create().getListProduct()
            .enqueue(object : Callback<ApiResponseProduct>{
                override fun onResponse(
                    call: Call<ApiResponseProduct>,
                    response: Response<ApiResponseProduct>
                ) {
                    val apiResponseProduct = response.body()
                    listProduct = apiResponseProduct!!.data
                    Log.d("data from api", "${listProduct.size}")
                    productAdapter = ProductAdapter(requireContext())
                    productAdapter.setData(listProduct)
                    binding.rcvProducts.adapter = productAdapter
                }

                override fun onFailure(call: Call<ApiResponseProduct>, t: Throwable) {
                    Toast.makeText(requireActivity(), "call api fail due to ${t.message} ", Toast.LENGTH_SHORT).show()
                    Log.e("call api product", "${t.message}")
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}