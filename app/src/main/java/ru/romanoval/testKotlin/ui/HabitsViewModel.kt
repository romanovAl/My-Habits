package ru.romanoval.testKotlin.ui

import androidx.lifecycle.ViewModel
import ru.romanoval.testKotlin.data.HabitsRepository
import ru.romanoval.testKotlin.model.Habit

class HabitsViewModel(private val habitsRepository: HabitsRepository) : ViewModel() {

    fun getHabits() = habitsRepository.getHabits()

    fun addHabit(habit: Habit) = habitsRepository.addHabit(habit)

    fun replaceHabit(oldHabit: Habit, newHabit: Habit) = habitsRepository.replaceHabit(oldHabit, newHabit)

}