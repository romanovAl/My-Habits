package ru.romanoval.testKotlin.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.romanoval.testKotlin.adapters.ViewPagerAdapter
import ru.romanoval.testKotlin.model.Habit

class MainFragmentViewModel : ViewModel() {

    private val mutableAdapter: MutableLiveData<ViewPagerAdapter?> = MutableLiveData()
    private val mutableAddedHabit: MutableLiveData<Habit?> = MutableLiveData()
    private val mutableEditedHabit: MutableLiveData<Habit?> = MutableLiveData()

    val adapter: LiveData<ViewPagerAdapter?> = mutableAdapter
    val addedHabit: LiveData<Habit?> = mutableAddedHabit
//    val editedHabit:

}