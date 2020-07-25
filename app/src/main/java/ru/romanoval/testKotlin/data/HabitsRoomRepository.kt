package ru.romanoval.testKotlin.data

import androidx.lifecycle.LiveData
import ru.romanoval.testKotlin.data.model.HabitRoom

class HabitsRoomRepository(private val habitsDao: HabitsDao) {

    val habits: LiveData<List<HabitRoom>> = habitsDao.getHabits()

    suspend fun insert(habit : HabitRoom){
        habitsDao.insertHabit(habit)
    }

    suspend fun update(habit: HabitRoom){
        habitsDao.updateHabit(habit)
    }


}