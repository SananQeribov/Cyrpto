package com.legalist.cytpto.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.legalist.cytpto.databinding.RecycleRowBinding
import com.legalist.cytpto.model.CyrptoModelItem

class CyrptoRecycleView (private val cryptoList : ArrayList<CyrptoModelItem>, private val listener : Listener) : RecyclerView.Adapter<CyrptoRecycleView.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CyrptoModelItem)
    }
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(val binding : RecycleRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = RecycleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.cryptoNameText.text = cryptoList[position].currency
        holder.binding.cryptoPriceText.text = cryptoList[position].price
    }


}