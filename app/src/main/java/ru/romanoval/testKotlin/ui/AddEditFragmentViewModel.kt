package ru.romanoval.testKotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.romanoval.testKotlin.data.model.HabitJson

class AddEditFragmentViewModel : ViewModel() {

    private var mutableSavedEdit: HabitJson? = null
    private var savedEdit = MutableLiveData<HabitJson?>()

    init {
        savedEdit.value = mutableSavedEdit
    }

    fun setSavedHabit(habit: HabitJson){
        mutableSavedEdit = habit
        savedEdit.value = mutableSavedEdit
    }

    fun getSavedHabit() = savedEdit as LiveData<HabitJson?>

}