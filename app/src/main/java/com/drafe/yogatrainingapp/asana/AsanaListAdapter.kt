package com.drafe.yogatrainingapp.asana

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drafe.yogatrainingapp.databinding.AsanaListItemBinding
import java.io.IOException
import java.util.UUID

class AsanaListAdapter(
    private val asanaList: List<Asana>,
    private val onAsanaClicked: (asanaId: UUID) -> Unit
):    RecyclerView.Adapter<AsanaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsanaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AsanaListItemBinding.inflate(inflater, parent, false)
        return AsanaHolder(binding)
    }

    override fun onBindViewHolder(holder: AsanaHolder, position: Int) {
        val asana = asanaList[position]
        holder.bind(asana, onAsanaClicked)
    }
    override fun getItemCount(): Int = asanaList.size
}

class AsanaHolder(
    private val binding: AsanaListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(asana: Asana, onAsanaClicked: (asanaId: UUID) -> Unit) {
        binding.asanaNameEng.text = asana.nameEng
        binding.asanaNameHindi.text = asana.nameHin

        val context = itemView.context
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open("poses/${asana.imgName}")
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.asanaIcon.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
            // TODO: Обработайте ошибку (например, установите изображение по умолчанию)
        }
        binding.root.setOnClickListener{
            onAsanaClicked(asana.id)
        }
    }
}
