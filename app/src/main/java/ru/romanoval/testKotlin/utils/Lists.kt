package ru.romanoval.testKotlin.utils

import ru.romanoval.testKotlin.data.model.HabitRoom


object Lists {

    val priorities = listOf(
        HabitRoom.Priority.HIGH.priority, HabitRoom.Priority.MEDIUM.priority,
        HabitRoom.Priority.LOW.priority)
    val periods = listOf(
        HabitRoom.Period.ANHOUR.period, HabitRoom.Period.ADAY.period
        , HabitRoom.Period.AWEEK.period, HabitRoom.Period.AMONTH.period, HabitRoom.Period.ANYEAR.period)

}