package ru.romanoval.testKotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.romanoval.testKotlin.model.Habit

class FakeDao {
    private val habitsList = ArrayList<Habit>()
    private val habits = MutableLiveData<ArrayList<Habit>>()

    init {
        habits.value = habitsList
    }

    fun addHabit(habit: Habit){
        habitsList.add(habit)
        habits.value = habitsList
    }

    fun replaceHabit(habitOld: Habit, habitNew: Habit){
        habitsList.remove(habitOld)
        habitsList.add(habitNew)
        habits.value = habitsList
    }

    fun getHabits() = habits as LiveData<ArrayList<Habit>>
}