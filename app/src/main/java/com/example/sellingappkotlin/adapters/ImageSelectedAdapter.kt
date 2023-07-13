package com.example.sellingappkotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.LayoutItemImageSelectedBinding
import com.example.sellingappkotlin.models.ImageProduct

class ImageSelectedAdapter(var context: Context) :
    RecyclerView.Adapter<ImageSelectedAdapter.ImageSelectedViewHolder>() {

    private var list = mutableListOf<ImageProduct>()
    private lateinit var colorSelectedAdapter: ColorSelectedAdapter
    var colorSelected  = ""

    class ImageSelectedViewHolder(var binding: LayoutItemImageSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindingData(image: ImageProduct) {

        }
    }

    fun setData(list: MutableList<ImageProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun updateImages(images: String) {
        this.colorSelected = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectedViewHolder {
        return ImageSelectedViewHolder(
            LayoutItemImageSelectedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageSelectedViewHolder, position: Int) {
        val image = list[position]
        holder.bindingData(image)
        Glide.with(context).load(image.url).error(R.drawable.image_test)
            .into(holder.binding.img)
        if (colorSelected == image.nameColor) {
            Log.d(
                "ImageSelectedAdapter",
                "color selected: ${colorSelected}," +
                        " name: ${image.nameColor}," +
                        " url selected: ${image.url}"
            )
            Glide.with(context).load(image.url).error(R.drawable.image_test)
                .into(holder.binding.img)
        }

    }

}