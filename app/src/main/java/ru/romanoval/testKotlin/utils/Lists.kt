package ru.romanoval.testKotlin.utils

import ru.romanoval.testKotlin.model.Habit

object Lists {

    val priorities = listOf(
        Habit.Priority.HIGH.priority, Habit.Priority.MEDIUM.priority,
        Habit.Priority.LOW.priority)
    val periods = listOf(
        Habit.Period.ANHOUR.period, Habit.Period.ADAY.period
        , Habit.Period.AWEEK.period, Habit.Period.AMMONTH.period, Habit.Period.ANYEAR.period)

}