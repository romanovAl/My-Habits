package ru.romanoval.testKotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.romanoval.testKotlin.data.model.HabitRoom

@Dao
interface HabitsDao {

    @Query("SELECT * FROM HabitRoom")
    fun getHabits() : LiveData<List<HabitRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitRoom: HabitRoom)

    @Update
    suspend fun updateHabit(habitRoom: HabitRoom)
}

