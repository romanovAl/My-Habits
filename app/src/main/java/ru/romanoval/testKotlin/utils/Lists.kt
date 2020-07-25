package ru.romanoval.testKotlin.utils

import android.content.Context
import ru.romanoval.testKotlin.R


object Lists {

    fun getPriorities(context: Context): List<String> {
        return listOf(
            context.resources.getString(R.string.high_priorirty),
            context.resources.getString(R.string.medium_priority),
            context.resources.getString(R.string.high_priorirty)
        )
    }

    fun getPeriods(context: Context): List<String> {
        return listOf(
            context.resources.getString(R.string.period_anhour),
            context.resources.getString(R.string.period_aday),
            context.resources.getString(R.string.period_aweek),
            context.resources.getString(R.string.period_amonth),
            context.resources.getString(R.string.period_anyear)
        )
    }

    fun getFilterTypes(context: Context): List<String> {
        return listOf(
            context.resources.getString(R.string.by_priority),
            context.resources.getString(R.string.by_period),
            context.resources.getString(R.string.by_quantity),
            context.resources.getString(R.string.without_filter)
        )
    }

}