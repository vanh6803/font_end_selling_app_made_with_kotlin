package com.example.sellingappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.LayoutItemColorBinding
import com.example.sellingappkotlin.models.ColorProduct

class ColorSelectedAdapter(context: Context) :
    RecyclerView.Adapter<ColorSelectedAdapter.ColorViewHolder>() {

    var list = mutableListOf<ColorProduct>()
    var selected = ""

    fun setData(list: MutableList<ColorProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ColorViewHolder(val binding: LayoutItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(color: ColorProduct) {
            color.code.forEach {
                binding.viewColor.setBackgroundColor(color.code.toColorInt())
                binding.selected.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.transparent
                    )
                )
                binding.view.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = list[position]
        holder.bindView(color)

        holder.binding.selected.setOnClickListener {
            if (selected == color.name) {
                holder.binding.selected.setBackgroundResource(R.drawable.border_item_selected)
                holder.binding.view.visibility = View.INVISIBLE
            }
        }

    }

}