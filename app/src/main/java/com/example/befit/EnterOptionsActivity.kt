package com.example.befit

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.befit.databinding.ActivityEnterOptionsBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

class EnterOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterOptionsBinding
    private lateinit var selectedDate: String
    private lateinit var monthInput: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveOptionsButton.setOnClickListener{
            val sleephours = binding.sleepHoursEditText.text.toString()
            val feeling = binding.feelingEditText.text.toString()
            val note = binding.noteFeeling.text.toString()
            selectedDate = binding.dateButton.text.toString()

            lifecycleScope.launch(IO){
                (application as SleepFeelingApplication).db.sleepFeelDao().insert(
                    SleepFeelingEntity(sleephours,feeling, note, selectedDate)
                )
            }


            finish()
        }

        binding.dateButton.setOnClickListener{
            clickDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(android.icu.util.Calendar.YEAR)
        val month = myCalendar.get(android.icu.util.Calendar.MONTH)
        val day = myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {view, year, month, dayOfMonth ->
                monthInput = (month + 1).toString()
                if (monthInput.toInt() == 1) {
                    monthInput = "January"
                } else if (monthInput.toInt() == 2) {
                    monthInput = "February"
                } else if (monthInput.toInt() == 3) {
                    monthInput = "March"
                } else if (monthInput.toInt() == 4) {
                    monthInput = "April"
                } else if (monthInput.toInt() == 5) {
                    monthInput = "May"
                } else if (monthInput.toInt() == 6) {
                    monthInput = "June"
                } else if (monthInput.toInt() == 7) {
                    monthInput = "July"
                } else if (monthInput.toInt() == 8) {
                    monthInput = "August"
                } else if (monthInput.toInt() == 9) {
                    monthInput = "September"
                } else if (monthInput.toInt() == 10) {
                    monthInput = "October"
                } else if (monthInput.toInt() == 11) {
                    monthInput = "November"
                } else if (monthInput.toInt() == 12) {
                    monthInput = "December"
                }

                val selectedDate = "${monthInput} $day, $year"
                binding.dateButton.text = selectedDate

            },
            year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
}