package ru.romanoval.testKotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.romanoval.testKotlin.data.model.HabitRoom

class AddEditFragmentViewModel : ViewModel() {

    private var mutableSavedEdit: HabitRoom? = null
    private var savedEdit = MutableLiveData<HabitRoom?>()

    init {
        savedEdit.value = mutableSavedEdit
    }

    fun setSavedHabit(habit: HabitRoom){
        mutableSavedEdit = habit
        savedEdit.value = mutableSavedEdit
    }

    fun getSavedHabit() = savedEdit as LiveData<HabitRoom?>

}