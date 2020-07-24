package ru.romanoval.testKotlin.utils

import ru.romanoval.testKotlin.data.FakeDatabase
import ru.romanoval.testKotlin.data.HabitsRepository
import ru.romanoval.testKotlin.ui.HabitsViewModelFactory

object InjectorUtils {

    fun provideHabitsViewModelFactory(): HabitsViewModelFactory{
        val habitsRepository = HabitsRepository.getInstance(FakeDatabase.getInstance().habitsDao)
        return HabitsViewModelFactory(habitsRepository)
    }
}