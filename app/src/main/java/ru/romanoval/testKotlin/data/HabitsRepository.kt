package ru.romanoval.testKotlin.data

import ru.romanoval.testKotlin.model.Habit

class HabitsRepository private constructor(private val habitsDao: FakeDao){

    fun addHabit(habit: Habit ){
        habitsDao.addHabit(habit)
    }

    fun replaceHabit(habitOld: Habit, habitNew: Habit){
        habitsDao.replaceHabit(habitOld, habitNew)
    }

    fun getHabits() = habitsDao.getHabits()

    companion object{
        @Volatile private var instance: HabitsRepository? = null

        fun getInstance(habitsDao: FakeDao) =
            instance ?: synchronized(this){
                instance ?: HabitsRepository(habitsDao).also { instance = it }
            }
    }

}