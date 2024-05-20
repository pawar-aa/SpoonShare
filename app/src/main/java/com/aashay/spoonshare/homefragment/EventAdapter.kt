package com.aashay.spoonshare.homefragment

import Event
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aashay.spoonshare.R
import com.bumptech.glide.Glide

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventName: TextView = itemView.findViewById(R.id.eventName)
        private val eventDescription: TextView = itemView.findViewById(R.id.eventDescription)
        private val eventHost: TextView = itemView.findViewById(R.id.eventHost)
        private val eventImage: ImageView = itemView.findViewById(R.id.eventImage)

        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            eventName.text = event.eventName
            eventDescription.text = event.eventGeopoint.latitude.toString() + " " + event.eventGeopoint.longitude.toString()
            eventHost.text = event.eventHost
            Glide.with(itemView.context).load(event.eventImage).into(eventImage)
        }
    }
}
