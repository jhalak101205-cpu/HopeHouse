package com.example.hopehouse.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.Event
import com.example.hopehouse.data.HopeHouseRepository
import com.google.android.material.button.MaterialButton

/**
 * RecyclerView adapter for events cards.
 */
class EventAdapter(
    private val onRegisterClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var items: List<Event> = emptyList()

    fun submitList(newItems: List<Event>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view, onRegisterClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class EventViewHolder(
        itemView: View,
        private val onRegisterClick: (Event) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvDate: TextView = itemView.findViewById(R.id.tvEventDate)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        private val tvNgo: TextView = itemView.findViewById(R.id.tvEventNgo)
        private val btnRegister: MaterialButton = itemView.findViewById(R.id.btnRegisterEvent)

        private var current: Event? = null

        init {
            btnRegister.setOnClickListener {
                current?.let(onRegisterClick)
            }
        }

        fun bind(event: Event) {
            current = event
            tvDate.text = event.dateLabel
            tvTitle.text = event.title
            tvNgo.text = event.ngoName

            val registered = HopeHouseRepository.isEventRegistered(event.id)
            btnRegister.isEnabled = !registered
            btnRegister.text = if (registered) "Registered" else "Register"
        }
    }
}

