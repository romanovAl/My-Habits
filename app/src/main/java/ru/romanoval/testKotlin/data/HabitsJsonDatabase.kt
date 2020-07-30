package ru.romanoval.testKotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.romanoval.testKotlin.data.model.HabitJson

@Database(entities = [HabitJson::class], version = 1)
@TypeConverters(HabitJson.ListConverter::class)
abstract class HabitsJsonDatabase : RoomDatabase() {
    abstract fun habitsDao(): HabitsDao2

    companion object {
        private var INSTANCE: HabitsJsonDatabase? = null

        fun getHabitsDatabase(context: Context): HabitsJsonDatabase {
            if (INSTANCE == null) {
                synchronized(HabitsJsonDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            HabitsJsonDatabase::class.java,
                            "habitsJsonDatabase"
                        )
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }


}