package com.pack.shayariapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.pack.shayariapp.databinding.RcItemBinding

class RvAdapter(var context: Context,
                var shayariList: MutableList<NewDataEntity>,
                private val itemClickListener: OnItemClickListener,
                private val deleteItemListener:OnDeleteItemClickListener):RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    class ViewHolder (val binding: RcItemBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RcItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shayariList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentShayari = shayariList.get(position)
        holder.binding.tvShayariTitile.text= currentShayari.title
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(currentShayari)
        }
        holder.binding.imageDelete.setOnClickListener{
            val deleteConfirmationDialog = DeleteConfirmationDialog(context)
            deleteConfirmationDialog.showDeleteDialog(currentShayari, position, deleteItemListener)
        }
    }





    interface OnItemClickListener {
        fun onItemClick(dataEntity: NewDataEntity)
    }

    interface OnDeleteItemClickListener{
        fun deleteItem(dataEntity: NewDataEntity, position: Int)
    }
}