package ru.romanoval.testKotlin.model

import java.io.Serializable

data class Habit  (
    var name: String,
    var description: String,
    var priority: Priority,
    var type: Boolean,
    var period: Period,
    var color: String, var times: Int?
) : Serializable {

    enum class Priority(val priority: String, val intPriority: Int){
        HIGH("Высокий", 3),
        MEDIUM("Средний", 2),
        LOW("Низкий", 1),
        NOPRIORITY("Приоритет не выбран", 0)
    }
    enum class Period(val period: String, val intPeriod: Int){
        ANHOUR("В час", 5),
        ADAY("В день", 4),
        AWEEK("В неделю", 3),
        AMMONTH("В месяц", 2),
        ANYEAR("В год", 1),
        NOPERIOD("Период не выбран", 0)
    }
}