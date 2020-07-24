package ru.romanoval.testKotlin.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.romanoval.testKotlin.model.Habit

class AddEditFragmentViewModel : ViewModel() {

    private var mutableSavedEdit: Habit? = null
    private var savedEdit = MutableLiveData<Habit?>()

    init {
        savedEdit.value = mutableSavedEdit
    }

    fun setSavedHabit(habit: Habit){
        mutableSavedEdit = habit
        savedEdit.value = mutableSavedEdit
    }

    fun getSavedHabit() = savedEdit as LiveData<Habit?>

}