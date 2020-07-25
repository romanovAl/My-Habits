package ru.romanoval.testKotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.romanoval.testKotlin.data.model.HabitRoom

@Database(entities = [HabitRoom::class], version = 1)
@TypeConverters(HabitRoom.PriorityConverter::class, HabitRoom.PeriodConverter::class)
abstract  class HabitsDatabase : RoomDatabase(){
    abstract fun habitsDao(): HabitsDao

    companion object{
        private var INSTANCE : HabitsDatabase? = null

        fun getHabitsDatabase(context: Context) : HabitsDatabase {
            if (INSTANCE == null){
                synchronized(HabitsDatabase::class){
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            HabitsDatabase::class.java,
                            "habitsDatabase")
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}