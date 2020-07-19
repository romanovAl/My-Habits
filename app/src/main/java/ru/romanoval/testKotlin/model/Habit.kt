package ru.romanoval.testKotlin.model

import java.io.Serializable

data class Habit  (
    var name: String, var description: String, var priority: String, var type:Boolean,
    var period:String, var color:String, var times: Int?
) : Serializable