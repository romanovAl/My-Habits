package ru.romanoval.testKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.Serializable

@Entity
class HabitRoom (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var name: String,
    var description: String,
    @TypeConverters(PriorityConverter::class)
    var priority: Priority,
    var type: Boolean,
    @TypeConverters(PeriodConverter::class)
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
            AMONTH("В месяц", 2),
            ANYEAR("В год", 1),
            NOPERIOD("Период не выбран", 0)
        }

    class PriorityConverter{
        @TypeConverter
        fun fromPriority(priority: Priority) : String{
            return priority.priority
        }
        @TypeConverter
        fun toPriority(data: String) : Priority{
            return when(data){
                Priority.HIGH.priority -> Priority.HIGH
                Priority.MEDIUM.priority -> Priority.MEDIUM
                Priority.LOW.priority -> Priority.LOW
                else -> Priority.NOPRIORITY
            }
        }
    }

    class PeriodConverter{
        @TypeConverter
        fun fromPeriod(period : Period) : String{
            return period.period
        }
        @TypeConverter
        fun toPeriod(data: String) : Period{
            return when(data){
                Period.ANHOUR.period -> Period.ANHOUR
                Period.ADAY.period -> Period.ADAY
                Period.AWEEK.period -> Period.AWEEK
                Period.AMONTH.period -> Period.AMONTH
                Period.ANYEAR.period -> Period.ANYEAR
                else -> Period.NOPERIOD
            }
        }
    }
    }