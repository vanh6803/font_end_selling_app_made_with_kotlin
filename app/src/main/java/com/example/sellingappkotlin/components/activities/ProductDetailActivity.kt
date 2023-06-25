package com.example.sellingappkotlin.components.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.adapters.ColorSelectedAdapter
import com.example.sellingappkotlin.adapters.SlideShowAdapter
import com.example.sellingappkotlin.databinding.ActivityProductDetailBinding
import com.example.sellingappkotlin.databinding.BottomSheetDialogBuyNowBinding
import com.example.sellingappkotlin.databinding.DialogShowMoreProductBinding
import com.example.sellingappkotlin.models.ApiResponseProductDetail
import com.example.sellingappkotlin.models.Product
import com.example.sellingappkotlin.utils.ApiService
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

    }

    private fun setEventToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun callApi() {
        val id = intent.extras!!.getString("id")
        ApiService.create().getProductFromId(id!!)
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
        val numberFormat = NumberFormat.getNumberInstance(Locale("vi", "VN"))
        val formattedPrice = numberFormat.format(product?.price)
        binding.tvPriceProduct.text = "$formattedPrice đ"

        var index = 1
        slideShowAdapter = SlideShowAdapter(this)
        product?.image?.let { slideShowAdapter.setData(it) }
        binding.viewpager2.adapter = slideShowAdapter
        binding.circleIndicator.setViewPager(binding.viewpager2)
        "${index} \\ ${product?.image?.size} ".also { binding.tvCounter.text = it }

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
            showBottomDialog()
        }

        binding.btnAddToCart.setOnClickListener {}
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


    private fun showBottomDialog() {
        val binding: BottomSheetDialogBuyNowBinding =
            BottomSheetDialogBuyNowBinding.inflate(LayoutInflater.from(this))
        var bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(binding.root)
        bottomDialog.window?.setBackgroundDrawableResource(R.color.transparent)

        binding.tvName.text = product?.name
        Glide.with(this)
            .load(product!!.image[0].replace("localhost", Config.LOCALHOST))
            .error(R.drawable.baseline_image_24)
            .into(binding.img)

//        colorSelectedAdapter = ColorSelectedAdapter(this)
//        product?.color?.let { colorSelectedAdapter.setData(it) }
//        binding.rcvColor.adapter = colorSelectedAdapter

        bottomDialog.show()
    }

}