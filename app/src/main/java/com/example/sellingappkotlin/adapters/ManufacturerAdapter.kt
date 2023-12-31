package com.example.sellingappkotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sellingappkotlin.databinding.LayoutItemManufacturerBinding
import com.example.sellingappkotlin.models.Manufacturer


class ManufacturerAdapter(var context: Context) : Adapter<ManufacturerAdapter.ManufacturerViewHolder>() {

    private var list = mutableListOf<Manufacturer>()
    var onClickManufacturerListener: ((id:String) -> Unit)? = null

    fun setData(list: MutableList<Manufacturer>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val view =
            LayoutItemManufacturerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManufacturerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        val obj: Manufacturer = list[position]
        holder.bindView(obj)


        holder.binding.layoutItemManufacture.setOnClickListener {
            onClickManufacturerListener?.invoke(list[position]._id)
            Log.d("CCC", "${onClickManufacturerListener?.invoke(list[position]._id)}")
            notifyDataSetChanged()
        }
    }

    class ManufacturerViewHolder(val binding: LayoutItemManufacturerBinding) : ViewHolder(binding.root) {
        fun bindView(manufacturer: Manufacturer) {
            binding.tvNameManufacturer.text = manufacturer.name
        }
    }

}