package ru.romanoval.testKotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.romanoval.testKotlin.data.HabitsRepository

class HabitsViewModelFactory(private val habitsRepository: HabitsRepository)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HabitsViewModel(habitsRepository) as T
    }

}