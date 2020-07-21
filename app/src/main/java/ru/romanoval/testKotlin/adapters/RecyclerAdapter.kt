package ru.romanoval.testKotlin.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recycler_element.view.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.fragments.MainFragmentDirections


class RecyclerAdapter(private var habits: ArrayList<Habit>, var context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder((inflater.inflate(R.layout.main_recycler_element, parent, false)))
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }

    fun addItem(hab: Habit) {
        habits.add(hab)
    }

    fun changeItem(hab: Habit, pos: Int) {
        habits[pos] = hab
        notifyItemChanged(pos)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit, position: Int) {

            containerView.run {

                constraintMainRecyclerElement.setOnClickListener {

                    var action =
                        MainFragmentDirections.actionMainFragment2ToAddEditFragment("Редактирование привычки")
                    action.habitToEdit = habit
                    Navigation.findNavController(it).navigate(action)
                }


                habitNameRecyclerElement.text = habit.name
                habitDescriptionRecyclerElement.text = habit.description
                habitPeriodRecyclerElement.text = habit.period
                habitPriorityRecyclerElement.text = "${habit.priority} приоритет"

                habitTypeRecyclerElement.text = if (habit.type) {
                    "Хорошая"
                } else {
                    "Плохая"
                }
                habitNameRecyclerElement.setBackgroundColor(Color.parseColor(habit.color))
                habitTimesRecyclerElement.text = "${habit.times.toString()} раз"
            }


        }
    }

}