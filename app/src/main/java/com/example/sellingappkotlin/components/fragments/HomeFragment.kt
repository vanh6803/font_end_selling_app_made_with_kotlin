package com.example.sellingappkotlin.components.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.HotProductAdapter
import com.example.sellingappkotlin.adapters.ManufacturerAdapter
import com.example.sellingappkotlin.adapters.ProductAdapter
import com.example.sellingappkotlin.databinding.FragmentHomeBinding
import com.example.sellingappkotlin.models.responseApi.ApiResponseManufacturer
import com.example.sellingappkotlin.models.responseApi.ApiResponseProduct
import com.example.sellingappkotlin.models.HotProduct
import com.example.sellingappkotlin.models.Manufacturer
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.models.responseApi.ApiResponseUser
import com.example.sellingappkotlin.utils.ApiServiceProduct
import com.example.sellingappkotlin.utils.ApiServiceUser
import com.example.sellingappkotlin.utils.Config
import com.example.sellingappkotlin.utils.Constant
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

    private lateinit var user: User

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
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    private fun initView() {
        getProfile(Constant.token)
        slideImageHotProduct()
        showManufactures()
        showProducts()
        initRefresh()

        productAdapter = ProductAdapter(requireContext())
        binding.rcvProducts.adapter = productAdapter

        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    productAdapter.filterList(newText)
                }
                return true
            }
        })



        binding.btnAll.setOnClickListener {
            showProducts()
        }

        binding.btnAll.setOnClickListener { showProducts() }



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
        ApiServiceProduct.apiServiceProduct.getListProduct()
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
        ApiServiceProduct.apiServiceProduct.getListManufacturers()
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
                    manufacturerAdapter.onClickManufacturerListener = { id ->
                        filterProduct(id)
                    }
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

    private fun getProfile(token: String) {
        ApiServiceUser.apiServiceUser.getProfile("Bearer $token")
            .enqueue(object : Callback<ApiResponseUser> {
                override fun onResponse(
                    call: Call<ApiResponseUser>,
                    response: Response<ApiResponseUser>
                ) {
                    val result = response.body()
                    user = result!!.data
                    if (user.username == null) {
                        binding.tvGreeting.text = "Welcome"
                    } else {
                        binding.tvGreeting.text = "Hello, ${user.username}"
                    }
                    Glide.with(requireContext()).load(user.avatar.replace("localhost", Config.LOCALHOST)).error(R.drawable.avatar_default)
                        .into(binding.imgProfile)
                }

                override fun onFailure(call: Call<ApiResponseUser>, t: Throwable) {
                    Log.d("PersonFragment-getProfile-onFailure", "${t.message}")
                }
            })
    }

    fun filterProduct(id: String){
        ApiServiceProduct.apiServiceProduct.getListProductFormManufacturers(id)
            .enqueue(object : Callback<ApiResponseProduct> {
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
                    Toast.makeText(
                        requireContext(),
                        "call api fail due to ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("call api product", "${t.message}")
                }

            })
    }

    private fun initRefresh() {
        binding.layoutRefresh.setOnRefreshListener {
            showProducts()
            showManufactures()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding == null
    }

    override fun onResume() {
        super.onResume()
        showProducts()
        showManufactures()
        getProfile(Constant.token)
    }
}