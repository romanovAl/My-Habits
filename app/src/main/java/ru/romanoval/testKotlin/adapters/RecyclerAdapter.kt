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
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.fragments.MainFragmentDirections
import ru.romanoval.testKotlin.utils.Lists


class RecyclerAdapter(private var habits: ArrayList<HabitRoom>, context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    private val priorities = Lists.getPriorities(context)
    private val periods = Lists.getPeriods(context)

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
                        MainFragmentDirections.actionMainFragment2ToAddEditFragment(context.resources.getString(R.string.label_edit))

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
                    HabitRoom.Period.NOPERIOD -> R.string.period_is_not_chosen.toString()
                }
                habitPriorityRecyclerElement.text = when(habit.priority){
                    HabitRoom.Priority.HIGH -> "${priorities[0]} ${this.resources.getString(R.string.priority)}"
                    HabitRoom.Priority.MEDIUM -> "${priorities[1]} ${this.resources.getString(R.string.priority)}"
                    HabitRoom.Priority.LOW -> "${priorities[2]} ${this.resources.getString(R.string.priority)}"
                    HabitRoom.Priority.NOPRIORITY -> this.resources.getString(R.string.priority_is_not_chosen)
                }

                habitTypeRecyclerElement.text = if (habit.type) {
                    this.resources.getText(R.string.good_habit)
                } else {
                    this.resources.getText(R.string.bad_habit)
                }
                habitNameRecyclerElement.setBackgroundColor(Color.parseColor(habit.color))
                habitTimesRecyclerElement.text = "${habit.times?.toString() ?: ""} ${this.resources.getString(R.string.times)}"
            }


        }
    }

}