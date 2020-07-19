package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_bad_habits.*
import kotlinx.android.synthetic.main.fragment_good_habits.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R


class FragmentBadHabits : Fragment(R.layout.fragment_bad_habits){

    companion object{
        private const val ARGS_NAME = "args_name"

        fun newInstance(name:String) : FragmentBadHabits{
            val fragment = FragmentBadHabits()
            val bundle = Bundle().run {
                putString(name, ARGS_NAME)
                fragment.arguments = this
            }
            return fragment
        }
    }

    var name: String = ""
    var callback: FragmentBadHabitsCallback? = null
    var adapter: RecyclerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as FragmentBadHabitsCallback
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
            Habit(
                "Привычка плохая 1",
                "Описание",
                "Высокий",
                false,
                "Каждый день",
                "#757de8",
                12
            )
            ,
            Habit(
                "Привычка плохая 2",
                "Описание2",
                "Высокий",
                false,
                "Каждый день",
                "#757de8",
                12
            ),
            Habit(
                "Привычка плохая 3",
                "Описание3",
                "Высокий",
                false,
                "Каждый день",
                "#757de8",
                12
            )
        ).toMutableList()

        val items2 = ArrayList(items)

        val adapter = context?.let { RecyclerAdapter(items2, it) }
        recyclerBadHabits.adapter = adapter

        fabBadHabits.setColorFilter(Color.argb(255, 255, 255, 255))

        fabBadHabits.setOnClickListener{
            println("Произошел онКлик в плохом - ")
            callback?.fabSetOnClick()
        }
    }

    fun addHabit(habit: Habit){
        adapter?.addItem(habit)
    }

    interface FragmentBadHabitsCallback{
        fun fabSetOnClick()
    }

}