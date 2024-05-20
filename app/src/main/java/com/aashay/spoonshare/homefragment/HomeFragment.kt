package com.aashay.spoonshare.homefragment

import Event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aashay.spoonshare.R

class HomeFragment : Fragment() {

    private lateinit var events: List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            events = it.getParcelableArrayList(ARG_EVENTS) ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = EventAdapter(events)

        return view
    }

    companion object {
        private const val ARG_EVENTS = "events"

        @JvmStatic
        fun newInstance(events: List<Event>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_EVENTS, ArrayList(events))
                }
            }
    }
}
