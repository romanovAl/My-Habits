package ru.romanoval.testKotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.romanoval.testKotlin.data.model.HabitJson

@Dao
interface HabitsDao2 {

    @Query("SELECT * FROM HabitJson")
    fun getHabits(): LiveData<List<HabitJson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitJson: HabitJson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfHabits(habitsJson: List<HabitJson>)

    @Update
    suspend fun updateHabit(habitJson: HabitJson)

    @Delete
    suspend fun deleteHabit(habitJson: HabitJson)

    @Query("DELETE FROM HabitJson")
    suspend fun deleteAllHabits()

}