package com.example.befit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.befit.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    lateinit var lineChart: LineChart
    lateinit var lineData: LineData
    lateinit var lineDataSet: LineDataSet
    private lateinit var lineList : ArrayList<Entry>
    private lateinit var lineList2 : ArrayList<Entry>
    private var sleeps = ArrayList<String>()
    private var feelings = ArrayList<String>()

    private lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        analysis()
        makeLineChart()
        return binding.root
    }

    private fun analysis(){
        //Get the averageSleep and averageFeeling update the view
        lifecycleScope.launch(Dispatchers.IO){
            val averageSleep = (activity?.application as SleepFeelingApplication).db.sleepFeelDao().averageSleep()
            val averageFeeling = (activity?.application as SleepFeelingApplication).db.sleepFeelDao().averageFeeling()

            val hos = binding.hoursOfSleepTextView
            val af = binding.averageFeelingTextView

            hos.text = averageSleep.toString()
            af.text = ("$averageFeeling/10")

        }
    }

    private fun makeLineChart(){

        lifecycleScope.launch(Dispatchers.IO){
            sleeps = (activity?.application as SleepFeelingApplication).db.sleepFeelDao().getAllSleep() as ArrayList<String>
            feelings = (activity?.application as SleepFeelingApplication).db.sleepFeelDao().getAllFeeling() as ArrayList<String>

            lineChart = binding.graphVisualization
            lineList = ArrayList()
            lineList2 = ArrayList()
            for (i in 0 until sleeps.size-1){
                lineList.add(Entry(sleeps[i].toFloat(), feelings[i].toFloat()))
                lineList2.add(Entry(feelings[i].toFloat(), i.toFloat()))
            }

            lineDataSet = LineDataSet(lineList, "Line Chart Data")
            lineData = LineData(lineDataSet)
            lineChart.data = lineData
            lineDataSet.color = R.color.kinda_orangish
            lineDataSet.valueTextColor = Color.BLACK
            lineDataSet.valueTextSize = 16f
            lineChart.description.isEnabled = false

        }

    }

}