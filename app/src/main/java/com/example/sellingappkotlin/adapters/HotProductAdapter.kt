package com.example.sellingappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.databinding.LayoutItemIntroHotProductBinding
import com.example.sellingappkotlin.models.HotProduct

class HotProductAdapter(var context: Context) :
    RecyclerView.Adapter<HotProductAdapter.HotProductViewHolder>() {

    private var list = mutableListOf<HotProduct>()

    fun addData(list: MutableList<HotProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    class HotProductViewHolder(val binding: LayoutItemIntroHotProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindingView(hotProduct: HotProduct) {
            Glide.with(binding.root).load(hotProduct.resourceId).into(binding.imgHotProduct)
            binding.titleHotProduct.text = hotProduct.titleHotProduct
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotProductViewHolder {
        val binding = LayoutItemIntroHotProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HotProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HotProductViewHolder, position: Int) {
        val obj: HotProduct = list[position]
        holder.bindingView(obj)
    }

}