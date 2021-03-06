package com.example.gallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(private val context: Context, val dataset: List<Uri>, val listener: ImageItemClicked):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageURI(item)
        holder.imageView.setOnClickListener{
            listener.onItemClicked(dataset[position])
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

 }
interface ImageItemClicked{
    fun onItemClicked(item: Uri)
}