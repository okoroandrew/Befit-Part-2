package com.example.befit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_feeling_table")
data class SleepFeelingEntity(
    @ColumnInfo(name = "sleepHours")
    val sleepHours: String?,
    @ColumnInfo(name = "feeling")
    val feeling: String?,
    @ColumnInfo(name = "note")
    val note: String?,
    @ColumnInfo(name = "date")
    val date: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
