package com.example.hopehouse.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.Cause
import com.google.android.material.button.MaterialButton

/**
 * RecyclerView adapter for Donation screen causes.
 */
class CauseAdapter(
    private val onDonateMoney: (Cause) -> Unit,
    private val onDonateItems: (Cause) -> Unit
) : RecyclerView.Adapter<CauseAdapter.CauseViewHolder>() {

    private var items: List<Cause> = emptyList()

    fun submitList(newItems: List<Cause>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CauseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cause, parent, false)
        return CauseViewHolder(view, onDonateMoney, onDonateItems)
    }

    override fun onBindViewHolder(holder: CauseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CauseViewHolder(
        itemView: View,
        private val onDonateMoney: (Cause) -> Unit,
        private val onDonateItems: (Cause) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tvCauseTitle)
        private val progress: ProgressBar = itemView.findViewById(R.id.progressCause)
        private val tvProgressText: TextView = itemView.findViewById(R.id.tvCauseProgressText)
        private val btnMoney: MaterialButton = itemView.findViewById(R.id.btnDonateMoney)
        private val btnItems: MaterialButton = itemView.findViewById(R.id.btnDonateItems)

        private var current: Cause? = null

        init {
            btnMoney.setOnClickListener { current?.let(onDonateMoney) }
            btnItems.setOnClickListener { current?.let(onDonateItems) }
        }

        fun bind(cause: Cause) {
            current = cause
            tvTitle.text = cause.title

            progress.max = cause.target
            progress.progress = cause.progress

            val percent = if (cause.target == 0) 0 else (cause.progress * 100 / cause.target)
            tvProgressText.text = "${cause.progress} / ${cause.target} ${cause.unit} (${percent}%)"
        }
    }
}

