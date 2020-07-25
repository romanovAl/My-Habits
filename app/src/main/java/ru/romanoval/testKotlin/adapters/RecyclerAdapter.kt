package ru.romanoval.testKotlin.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recycler_element.view.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.fragments.MainFragmentDirections
import ru.romanoval.testKotlin.utils.Lists


class RecyclerAdapter(private var habits: ArrayList<HabitRoom>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    private val priorities = Lists.priorities
    private val periods = Lists.periods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder((inflater.inflate(R.layout.main_recycler_element, parent, false)))
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: HabitRoom, position: Int) {

            containerView.run {

                constraintMainRecyclerElement.setOnClickListener {

                    val action =
                        MainFragmentDirections.actionMainFragment2ToAddEditFragment("Редактирование привычки")

                    action.habitRoomToEdit = habit

                    Navigation.findNavController(it).navigate(action)
                }


                habitNameRecyclerElement.text = habit.name
                habitDescriptionRecyclerElement.text = habit.description
                habitPeriodRecyclerElement.text = when(habit.period){
                    HabitRoom.Period.ANHOUR -> periods[0]
                    HabitRoom.Period.ADAY -> periods[1]
                    HabitRoom.Period.AWEEK -> periods[2]
                    HabitRoom.Period.AMONTH -> periods[3]
                    HabitRoom.Period.ANYEAR -> periods[4]
                    HabitRoom.Period.NOPERIOD -> ""
                }
                habitPriorityRecyclerElement.text = when(habit.priority){
                    HabitRoom.Priority.HIGH -> priorities[0]
                    HabitRoom.Priority.MEDIUM -> priorities[1]
                    HabitRoom.Priority.LOW -> priorities[2]
                    HabitRoom.Priority.NOPRIORITY -> ""
                }

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