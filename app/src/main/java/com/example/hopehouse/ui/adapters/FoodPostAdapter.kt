package com.example.hopehouse.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.example.hopehouse.R
import com.example.hopehouse.data.FoodPost
import com.google.android.material.button.MaterialButton

/**
 * RecyclerView adapter for the Food Rescue screen.
 *
 * Allows claiming food posts (dummy behavior updates in-memory data).
 */
class FoodPostAdapter(
    private val onClaimClick: (FoodPost) -> Unit
) : RecyclerView.Adapter<FoodPostAdapter.FoodPostViewHolder>() {

    private var items: List<FoodPost> = emptyList()

    fun submitList(newItems: List<FoodPost>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_post, parent, false)
        return FoodPostViewHolder(view, onClaimClick)
    }

    override fun onBindViewHolder(holder: FoodPostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class FoodPostViewHolder(
        itemView: View,
        private val onClaimClick: (FoodPost) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvLocation: TextView = itemView.findViewById(R.id.tvFoodLocation)
        private val tvMeta: TextView = itemView.findViewById(R.id.tvFoodMeta)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvFoodStatus)
        private val btnClaim: MaterialButton = itemView.findViewById(R.id.btnClaimFood)

        private var current: FoodPost? = null

        init {
            btnClaim.setOnClickListener {
                current?.let(onClaimClick)
            }
        }

        fun bind(post: FoodPost) {
            current = post
            tvLocation.text = post.location
            tvMeta.text = "${post.quantityKg} kg • ${post.timeLabel}"
            if (post.claimed) {
                tvStatus.text = "Status: Claimed"
                btnClaim.isEnabled = false
                btnClaim.text = "Claimed"
                btnClaim.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.hope_blue_500)
                )
            } else {
                tvStatus.text = "Status: Available"
                btnClaim.isEnabled = true
                btnClaim.text = "Claim Food"
                btnClaim.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.hope_green_500)
                )
            }
        }
    }
}

