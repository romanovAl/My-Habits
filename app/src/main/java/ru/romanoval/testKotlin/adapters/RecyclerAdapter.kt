package ru.romanoval.testKotlin.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recycler_element.view.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitJson
import ru.romanoval.testKotlin.fragments.MainFragmentDirections
import ru.romanoval.testKotlin.ui.ApiViewModel
import ru.romanoval.testKotlin.utils.Lists


class RecyclerAdapter(private var habits: ArrayList<HabitJson>, private var context: Context):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    private val priorities = Lists.getPriorities(context)
    private val periods = Lists.getPeriods(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder((inflater.inflate(R.layout.main_recycler_element, parent, false)))
    }

    fun updateHabits(newList: ArrayList<HabitJson>){
        habits = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: HabitJson) {

            containerView.run {

                constraintMainRecyclerElement.setOnClickListener {

                    val action =
                        MainFragmentDirections.actionMainFragment2ToAddEditFragment(context.resources.getString(R.string.label_edit))

                    action.habitJsonToEdit = habit

                    Navigation.findNavController(it).navigate(action)
                }


                habitNameRecyclerElement.text = habit.title
                habitDescriptionRecyclerElement.text = habit.description
                habitPeriodRecyclerElement.text = when(habit.frequency){
                    0 -> periods[0]
                    1 -> periods[1]
                    2 -> periods[2]
                    3 -> periods[3]
                    4 -> periods[4]
                    else -> this.resources.getText(R.string.period_is_not_chosen)
                }
                habitPriorityRecyclerElement.text = when(habit.priority){
                    1 -> "${priorities[1]} ${this.resources.getString(R.string.priority)}"
                    2 -> "${priorities[2]} ${this.resources.getString(R.string.priority)}"
                    else -> priorities[0]
                }

                habitTypeRecyclerElement.text = if (habit.type == 1) {
                    this.resources.getText(R.string.good_habit)
                } else {
                    this.resources.getText(R.string.bad_habit)
                }

                habitNameRecyclerElement.setBackgroundColor(resources.getColor(R.color.colorPrimaryCustom))
                habitTimesRecyclerElement.text = "${habit.count?.toString() ?: ""} ${this.resources.getString(R.string.times)}"
            }

        }
    }

}