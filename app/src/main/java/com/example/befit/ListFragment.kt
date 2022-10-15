package com.example.befit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var listRecyclerView: RecyclerView
    private lateinit var listAdapter: ItemsAdapter
    private var sleepFeels: ArrayList<SleepFeeling> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        listRecyclerView = view.findViewById(R.id.recyclerView)
        listRecyclerView.setHasFixedSize(true)

        //find the fab using find by id and set an onclick listener
        val addFab = view.findViewById<FloatingActionButton>(R.id.add_fab)
        addFab.setOnClickListener {
            val intent = Intent(context, EnterOptionsActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        listAdapter = ItemsAdapter(sleepFeels)
        listRecyclerView.adapter = listAdapter

        // Get all items from the database and populate the SleepFeeling class for the adapter
        lifecycleScope.launch{
            (activity?.application as SleepFeelingApplication).db.sleepFeelDao().getAll().collect { databaseList ->
                databaseList.map{entity->
                    SleepFeeling(
                        entity.sleepHours,
                        entity.feeling,
                        entity.note,
                        entity.date
                    )
                }.also{mappedList->
                    sleepFeels.clear()
                    sleepFeels.addAll(mappedList)
                    listAdapter.notifyDataSetChanged()
                }
            }
        }

        val layoutManager = LinearLayoutManager(context)
        listRecyclerView.layoutManager = layoutManager
    }
}