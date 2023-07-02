package com.example.sellingappkotlin.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.LayoutItemColorBinding
import com.example.sellingappkotlin.models.ColorProduct

class ColorSelectedAdapter(context: Context) :
    RecyclerView.Adapter<ColorSelectedAdapter.ColorViewHolder>() {

    private var list = mutableListOf<ColorProduct>()
    var itemSelected: String = ""
    var onClickColorListener: ((nameColor:String) -> Unit)? = null

    fun setData(list: MutableList<ColorProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ColorViewHolder(val binding: LayoutItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(color: ColorProduct) {
            binding.tvNameColor.text = color.name
//            Log.d("COLOR", color.name)

            if (itemSelected == color.name) {
                binding.selected.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            color.code
                        )
                    )
                )
                if (color.name.contains("Black")||color.name.contains("Purple")) {
                    binding.tvNameColor.setTextColor(Color.WHITE)
                } else {
                    binding.tvNameColor.setTextColor(Color.BLACK)
                }
                Log.d("SELECTED", color.name)

            } else {
                binding.selected.setCardBackgroundColor(binding.root.context.getColor(R.color.white))
                binding.tvNameColor.setTextColor(Color.BLACK)
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
            onClickColorListener?.invoke(list[position].name)
            itemSelected = list[position].name
            notifyDataSetChanged()
        }

    }

}