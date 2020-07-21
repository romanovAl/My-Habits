package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.fragment_good_habits.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.model.Data


class FragmentGoodHabits : Fragment(R.layout.fragment_good_habits) {

    companion object{
        private const val ARGS_NAME = "args_name"

        fun newInstance(name:String) : FragmentGoodHabits{
            val fragment = FragmentGoodHabits()
            val bundle = Bundle().run {
                putString(name, ARGS_NAME)
                fragment.arguments = this
            }
            return fragment
        }
    }

    var name: String = ""
    var callback : FragmentGoodHabitsCallback? = null
    var adapter : RecyclerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as FragmentGoodHabitsCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = context?.let { RecyclerAdapter(Data.getGoodHabits(), it) }
        recyclerGoodHabits.adapter = adapter

        fabGoodHabits.setColorFilter(Color.argb(255, 255, 255, 255))

        fabGoodHabits.setOnClickListener{
            println("Произошел онКлик в хорошем - ")
            callback?.fabSetOnClick()
        }

    }

    fun addHabit(habit: Habit){
        adapter?.addItem(habit)
    }

    interface FragmentGoodHabitsCallback{
        fun fabSetOnClick()
    }
}