package ru.romanoval.testKotlin.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.romanoval.testKotlin.data.HabitsJsonDatabase
import ru.romanoval.testKotlin.data.RepositoryApi
import ru.romanoval.testKotlin.data.DoubletappApi
import ru.romanoval.testKotlin.data.model.HabitJson
import kotlinx.android.synthetic.main.fragment_good_habits.*
import ru.romanoval.testKotlin.data.model.Delete

class ApiViewModel(application: Application) : AndroidViewModel(application) {


    private val mutableIsDataLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val isDataLoading : LiveData<Boolean?> = mutableIsDataLoading

    private val repository: RepositoryApi

    val habits: LiveData<List<HabitJson>>

    private val apiService: DoubletappApi =
        DoubletappApi.create()

    init {

        val habitsDao = HabitsJsonDatabase.getHabitsDatabase(application).habitsDao()
        repository = RepositoryApi(habitsDao, apiService)
        habits = repository.habits

    }


    fun downloadHabitsFromApi() {
        mutableIsDataLoading.value = true
        GlobalScope.launch(Dispatchers.IO){
            deleteAllHabits()
        }
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO){
                apiService.getHabitsList("application/json", "5cbb174f-cd85-4ad0-91d4-93be374c9883")

            }
            val res = result.body()
            println("res is - $res")
            if (res != null) {
                withContext(Dispatchers.IO){insertListOfHabits(res)}
            }
            mutableIsDataLoading.postValue(false)
            if(result.isSuccessful){
                Toast.makeText(getApplication(), "Данные загружены", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(getApplication(), "Что-то пошло не так, ошибка ${result.code()}", Toast.LENGTH_SHORT).show()
            }

        }

    }


    fun uploadHabitsToApi() {

        val habits = habits.value

        mutableIsDataLoading.value = true
        GlobalScope.launch(Dispatchers.Main){

            withContext(Dispatchers.IO){
                val result = withContext(Dispatchers.IO){
                    apiService.getHabitsList("application/json", "5cbb174f-cd85-4ad0-91d4-93be374c9883")
                }

                result.body()?.forEach {
                    val delete = Delete(it.uid!!)
                    apiService.deleteHabit(
                        "application/json",
                        "5cbb174f-cd85-4ad0-91d4-93be374c9883",
                        "application/json",
                        delete
                    )
                }
            }

            withContext(Dispatchers.IO){
                habits?.forEach {
                    it.uid = null
                    val result = apiService.putHabit(
                        "application/json",
                        "5cbb174f-cd85-4ad0-91d4-93be374c9883",
                        "application/json",
                        it
                    )
                    if(!result.isSuccessful){
                        Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            mutableIsDataLoading.postValue(false)
            Toast.makeText(getApplication(), "Данные выгружены", Toast.LENGTH_SHORT).show()
        }
    }

    fun insert(habit: HabitJson) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

    private fun insertListOfHabits(habits: List<HabitJson>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertListOfHabits(habits)
    }

    fun update(habit: HabitJson) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit)
    }

    fun delete(habit: HabitJson) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(habit)
    }

    private fun deleteAllHabits() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllHabits()
    }
}