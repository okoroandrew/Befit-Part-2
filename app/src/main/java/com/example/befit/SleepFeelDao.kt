package com.example.befit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepFeelDao {
    @Query("SELECT * FROM sleep_feeling_table")
    fun getAll(): Flow<List<SleepFeelingEntity>>

    @Query("SELECT sleepHours FROM sleep_feeling_table")
    fun getAllSleep() : List<String>

    @Query("SELECT feeling FROM sleep_feeling_table")
    fun getAllFeeling(): List<String>

    @Query("SELECT date FROM sleep_feeling_table")
    fun getAllDate(): List<String>

    @Insert
    fun insertAll(sleepFeelings: List<SleepFeelingEntity>)

    @Insert
    fun insert(sleepFeeling: SleepFeelingEntity)

    @Query("DELETE FROM sleep_feeling_table")
    fun deleteAll()

    @Query("SELECT AVG(sleepHours) as averageSleep FROM sleep_feeling_table")
    fun averageSleep(): Int

    @Query("SELECT AVG(feeling) as averageFeeling FROM sleep_feeling_table")
    fun averageFeeling() : Int
}