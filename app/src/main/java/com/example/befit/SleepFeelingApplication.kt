package com.example.befit

import android.app.Application

class SleepFeelingApplication: Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}