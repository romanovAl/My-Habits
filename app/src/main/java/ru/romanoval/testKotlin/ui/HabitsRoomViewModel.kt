package ru.romanoval.testKotlin.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.data.HabitsDatabase
import ru.romanoval.testKotlin.data.HabitsRoomRepository

class HabitsRoomViewModel(application: Application) : AndroidViewModel(application){

    private val repository: HabitsRoomRepository

    val habits : LiveData<List<HabitRoom>>

    init {
        val habitsDao = HabitsDatabase.getHabitsDatabase(application).habitsDao()
        repository = HabitsRoomRepository(habitsDao)
        habits = repository.habits

    }


    fun insert(habitRoom: HabitRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habitRoom)
    }

    fun update(habitRoom: HabitRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habitRoom)
    }

}