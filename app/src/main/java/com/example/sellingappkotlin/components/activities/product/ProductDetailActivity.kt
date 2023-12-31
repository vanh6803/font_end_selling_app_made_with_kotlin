package com.example.sellingappkotlin.components.activities.product

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.ColorSelectedAdapter
import com.example.sellingappkotlin.adapters.SlideShowAdapter
import com.example.sellingappkotlin.databinding.ActivityProductDetailBinding
import com.example.sellingappkotlin.databinding.BottomSheetDialogBuyNowBinding
import com.example.sellingappkotlin.databinding.DialogShowMoreProductBinding
import com.example.sellingappkotlin.models.responseApi.ApiResponseProductDetail
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.utils.ApiServiceProduct
import com.example.sellingappkotlin.utils.Config
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private var product: Product? = null
    private lateinit var slideShowAdapter: SlideShowAdapter
    private lateinit var colorSelectedAdapter: ColorSelectedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEventToolbar()
        callApi()
        initRefresh()

    }

    private fun setEventToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun callApi() {
        val id = intent.extras!!.getString("id")
        ApiServiceProduct.apiServiceProduct.getProductFromId(id!!)
            .enqueue(object : Callback<ApiResponseProductDetail> {
                override fun onResponse(
                    call: Call<ApiResponseProductDetail>,
                    response: Response<ApiResponseProductDetail>
                ) {
                    val data = response.body()!!
                    product = data.data
                    initView()
                }

                override fun onFailure(call: Call<ApiResponseProductDetail>, t: Throwable) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "call api fail due to ${t.message} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("call api product", "${t.message}")
                }
            })
    }

    private fun initView() {
        binding.tvNameProduct.text = product?.name
        binding.tvPriceProduct.text = product?.price?.let { formatPrice(it) }

        var index: Int
        slideShowAdapter = SlideShowAdapter(this)
        slideShowAdapter.setData(product?.image!!)
        binding.viewpager2.adapter = slideShowAdapter
        binding.circleIndicator.setViewPager(binding.viewpager2)
        binding.viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                index = position + 1
                binding.tvCounter.text = "$index / ${product?.image?.size}"
            }
        })

        binding.tvManufacturerProduct.text = ": ${product?.manufacturer?.name}"
        binding.tvQuantityProduct.text = "${product?.quantity}"
        binding.tableName.text = product?.name
        binding.tableChipset.text = product?.detail?.chipSet
        binding.tableCpu.text = product?.detail?.cpu
        binding.tableGpu.text = product?.detail?.gpu
        binding.tvRam.text = "${product?.detail?.ram} GB"
        binding.tvRom.text = "${product?.detail?.rom} GB"
        binding.tvDescription.text = product?.description

        binding.btnShowMore.setOnClickListener {
            showDialog()
        }

        binding.btnBuyNow.setOnClickListener {
            showBottomDialog("Buy now"){
                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnAddToCart.setOnClickListener {
            showBottomDialog("Add to cart"){
                Toast.makeText(this,"Add to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showDialog() {
        val binding: DialogShowMoreProductBinding = DialogShowMoreProductBinding.inflate(
            LayoutInflater.from(this)
        )
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        binding.tvRam.text = "${product?.detail?.ram} GB"
        binding.tvRom.text = "${product?.detail?.rom} Gb"

        binding.tvCpu.text = product?.detail?.cpu
        binding.tvGpu.text = product?.detail?.gpu
        binding.tvChipset.text = product?.detail?.chipSet

        binding.tvScreenSize.text = "${product?.detail?.screenSize} inch"
        binding.tvScreenTechnology.text = product?.detail?.screenTechnology
        binding.tvScreenFeatures.text = product?.detail?.screenFeatures
        binding.tvScreenType.text = product?.detail?.screenType

        binding.tvFontCamera.text = product?.detail?.frontCamera
        binding.tvRearCamera.text = product?.detail?.rearCamera

        binding.tvStatus.text = product?.status
        binding.tvBattery.text = "${product?.detail?.battery} mAh"
        binding.tvOperatingSystem.text = product?.detail?.operatingSystem
        binding.tvInternet.text = product?.detail?.internet
        binding.tvWeight.text = "${product?.detail?.weight}"
        binding.tvAudioTechnology.text = product?.detail?.audioTechnology
        binding.tvSpecialFeatures.text = product?.detail?.specialFeatures
        binding.tvManufacturer.text = product?.manufacturer?.name

        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        binding.icClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showBottomDialog(value:String, OnclickListener: ()->Unit) {
        val binding: BottomSheetDialogBuyNowBinding =
            BottomSheetDialogBuyNowBinding.inflate(LayoutInflater.from(this))
        val bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(binding.root)
        bottomDialog.window?.setBackgroundDrawableResource(R.color.transparent)

        colorSelectedAdapter = ColorSelectedAdapter(this)

        binding.tvName.text = product?.name
        binding.tvQuantity.text = "Total: ${product?.quantity}"

        binding.tvPrice.text = product?.price?.let { formatPrice(it) }

        /*----------------------------------------------------------------*/
        //todo: Select a color, and then select the image according to the selected color

        product?.color?.let { colorSelectedAdapter.setData(it) }
        binding.rcvColor.adapter = colorSelectedAdapter
        var imageSelected = ""
        Glide.with(binding.root).load(product!!.image[0].url.replace("localhost",Config.LOCALHOST )).error(R.drawable.baseline_image_24)
            .into(binding.imgProduct)

        colorSelectedAdapter.onClickColorListener = {
            Log.d("COLOR-SELECTED-PRODUCT-DETAIL",it )
            for (image in product!!.image){
                if ( image.nameColor== it){
                    Glide.with(this).load(image.url.replace("localhost", Config.LOCALHOST)).error(R.drawable.baseline_image_24).into(binding.imgProduct)
                    imageSelected = image.url
                }
            }
        }

        /*----------------------------------------------------------------*/
        var index = 1
        var finalPrice: Int = 0

        binding.btnPlus.setOnClickListener {
            index = binding.edQuantity.text.toString().trim().toInt()
            if (index < product?.quantity!!) {
                index++
                finalPrice = product?.price!! * index
                binding.tvPrice.setText(formatPrice(finalPrice))
            } else {
                Toast.makeText(this, "Number of existing products: ${product?.quantity}", Toast.LENGTH_SHORT).show()
            }
            binding.edQuantity.setText(index.toString())
        }

        binding.btnMinus.setOnClickListener {
            index = binding.edQuantity.text.toString().trim().toInt()
            if (index > 1) {
                index--
                finalPrice = product?.price!! * index
                Log.d("finalPrice",  finalPrice.toString())
                binding.tvPrice.setText(formatPrice(finalPrice))
            } else {
                Toast.makeText(this, "Can't select numbers less than 1", Toast.LENGTH_SHORT).show()
            }
            binding.edQuantity.setText(index.toString())
        }




        binding.btnBuyNow.setText(value)
        binding.btnBuyNow.setOnClickListener {


            OnclickListener.invoke()
        }

        bottomDialog.show()
    }

    private fun initRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            callApi()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun formatPrice(price: Int): String{
        val numberFormat = NumberFormat.getNumberInstance(Locale("vi", "VN"))
        var formattedPrice = numberFormat.format(price)
        return formattedPrice
    }


}