package com.example.befit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.befit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding1: ActivityMainBinding
    private var sleepFeels: ArrayList<SleepFeeling> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        binding1.addFab.setOnClickListener{
            val intent = Intent(this, EnterOptionsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val sleepFeelAdapter = ItemsAdapter(sleepFeels)
        binding1.recyclerView.adapter = sleepFeelAdapter

        // Get all items from the database and populate the SleepFeeling class for the adapter
        lifecycleScope.launch{
            (application as SleepFeelingApplication).db.sleepFeelDao().getAll().collect { databaseList ->
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
                    sleepFeelAdapter.notifyDataSetChanged()
                }
            }
        }

        binding1.recyclerView.layoutManager = LinearLayoutManager(this)

        //Get the averageSleep and averageFeeling update the view
        lifecycleScope.launch(IO){
            val averageSleep = (application as SleepFeelingApplication).db.sleepFeelDao().averageSleep()
            val averageFeeling = (application as SleepFeelingApplication).db.sleepFeelDao().averageFeeling()

            binding1.hoursOfSleepTextView.text = averageSleep.toString()
            binding1.averageFeelingTextView.text = ("$averageFeeling/10")
        }


    }

}