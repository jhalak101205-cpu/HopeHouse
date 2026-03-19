package com.example.hopehouse.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.Ngo

/**
 * RecyclerView adapter for the NGO listing screen.
 *
 * Uses dummy/static data from [com.example.hopehouse.data.HopeHouseRepository].
 */
class NgoAdapter(
    private val onNgoClick: (Ngo) -> Unit
) : RecyclerView.Adapter<NgoAdapter.NgoViewHolder>() {

    private var items: List<Ngo> = emptyList()

    fun submitList(newItems: List<Ngo>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NgoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ngo, parent, false)
        return NgoViewHolder(view, onNgoClick)
    }

    override fun onBindViewHolder(holder: NgoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class NgoViewHolder(
        itemView: View,
        private val onNgoClick: (Ngo) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val ivNgo: ImageView = itemView.findViewById(R.id.ivNgo)
        private val tvNgoName: TextView = itemView.findViewById(R.id.tvNgoName)
        private val tvNgoCategory: TextView = itemView.findViewById(R.id.tvNgoCategory)
        private val tvNgoLocation: TextView = itemView.findViewById(R.id.tvNgoLocation)

        private var currentNgo: Ngo? = null

        init {
            itemView.setOnClickListener {
                currentNgo?.let(onNgoClick)
            }
        }

        fun bind(ngo: Ngo) {
            currentNgo = ngo
            ivNgo.setImageResource(ngo.imageResId)
            tvNgoName.text = ngo.name
            tvNgoCategory.text = ngo.category
            tvNgoLocation.text = ngo.location
        }
    }
}

