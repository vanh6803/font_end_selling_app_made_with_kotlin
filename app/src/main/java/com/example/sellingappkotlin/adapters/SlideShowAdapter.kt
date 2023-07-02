package com.example.sellingappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.LayoutSlideShowProductBinding
import com.example.sellingappkotlin.models.ImageProduct
import com.example.sellingappkotlin.utils.Config

class SlideShowAdapter(val context: Context) :
    RecyclerView.Adapter<SlideShowAdapter.SlideViewHolder>() {
    class SlideViewHolder(val binding: LayoutSlideShowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindingView(image: ImageProduct) {
//                Log.d("url", url)
                Glide.with(binding.root.context).load(image.url.replace("localhost", Config.LOCALHOST)).error(R.drawable.baseline_image_24)
                    .into(binding.imgSlideShow)


        }
    }

    private var list = mutableListOf<ImageProduct>()

    fun setData(list: MutableList<ImageProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        return SlideViewHolder(
            LayoutSlideShowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val product = list[position]
        holder.bindingView(product)
    }
}