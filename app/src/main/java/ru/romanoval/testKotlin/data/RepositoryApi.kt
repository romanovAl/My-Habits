package ru.romanoval.testKotlin.data

import androidx.lifecycle.LiveData
import retrofit2.Response
import ru.romanoval.testKotlin.data.model.HabitJson

class RepositoryApi(private val habitsDao: HabitsDao2, val dtApi: DoubletappApi) {

    val habits: LiveData<List<HabitJson>> = habitsDao.getHabits()

    suspend fun downloadHabitsFromApi(header1: String, header2: String)
            : Response<List<HabitJson>> = dtApi.getHabitsList(header1, header2)

    suspend fun insert(habit: HabitJson) = habitsDao.insertHabit(habit)

    suspend fun insertListOfHabits(habits: List<HabitJson>) = habitsDao.insertListOfHabits(habits)

    suspend fun update(habit: HabitJson) = habitsDao.updateHabit(habit)

    suspend fun delete(habit: HabitJson) = habitsDao.deleteHabit(habit)

    suspend fun deleteAllHabits() = habitsDao.deleteAllHabits()
}