package ru.romanoval.testKotlin.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recycler_element.view.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.R
import java.lang.RuntimeException


//, val adapterOnClickConstraint : (Habit, Int) -> Unit

class RecyclerAdapter (private var habits: ArrayList<Habit>, var context: Context)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder((inflater.inflate(R.layout.main_recycler_element, parent, false)))
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }

    fun addItem(hab : Habit){
        habits.add(hab)
    }
    fun changeItem(hab : Habit, pos: Int){
        habits[pos] = hab
        notifyItemChanged(pos)
    }

    var callback: RecyclerCallback? = null



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun bind(habit: Habit, position: Int){

            containerView.run {

                constraintMainRecyclerElement.setOnClickListener{
                    callback?.constraintOnClick(habit)
                }

                habitNameRecyclerElement.text = habit.name
                habitDescriptionRecyclerElement.text = habit.description
                habitPeriodRecyclerElement.text = habit.period
                habitPriorityRecyclerElement.text = "${habit.priority} приоритет"

                habitTypeRecyclerElement.text = if(habit.type){
                    "Хорошая"
                }else{
                    "Плохая"
                }
                habitNameRecyclerElement.setBackgroundColor(Color.parseColor(habit.color))
                habitTimesRecyclerElement.text = "${habit.times.toString()} раз"
            }

            
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if(context is RecyclerCallback){
            callback = context as RecyclerCallback
        }else{
            throw RuntimeException("${context.toString()} must implement RecyclerCallback")
        }
    }

    interface RecyclerCallback{
        fun constraintOnClick(hab: Habit)
    }

}