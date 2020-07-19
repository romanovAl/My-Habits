package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_add_edit.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.model.Habit


class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    companion object {
        fun newInstance(param1: String, param2: String) =
            AddEditFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = context as AddEditFragmentCallback
    }

    var callback: AddEditFragmentCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val hab = AddEditFragmentArgs.fromBundle(requireArguments()).habitToEdit

        if(hab == null){ //Добавление
            callback?.setTitleToAdd()
            init()

        }else{//Редактирование

            habitPriorityAddAndEdit(hab.priority)
            habitPeriodAddAndEdit(hab.period)



            habitNameAddAndEdit.setText(hab.name)
            habitDescriptionAddAndEdit.setText(hab.description)


            if (hab.type) {
                radioButtonGood.isChecked = true
            }else{
                radioButtonBad.isChecked = true
            }


            habitDoneAddEdit.setText(hab.times.toString())

            addAndEditFab.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_24))

            init()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(){
        val priorities = listOf("Высокий", "Средний", "Низкий");
        val adapterPriority = context?.let { ArrayAdapter(it, R.layout.list_item, priorities) }
        (habitPriorityInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPriority)

        val periods = listOf("Каждый день", "Будние дни", "Выходные дни", "Раз в неделю", "Раз в месяц");
        val adapterPeriod = context?.let { ArrayAdapter(it, R.layout.list_item, periods) }
        (habitPeriodInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPeriod)

        addAndEditFab.setColorFilter(Color.argb(255, 255, 255, 255))

        addAndEditFab.setOnClickListener{
            val habit  = Habit(habitNameAddAndEdit.text.toString(), habitDescriptionAddAndEdit.text.toString(),
                habitPriorityAddAndEdit.text.toString(), radioButtonGood.isChecked, habitPeriodAddAndEdit.text.toString()
                ,"#757de8", habitDoneAddEdit.text?.toString()?.toInt())

            callback?.fabOnClickToMainFragment(habit)
        }
    }

    interface AddEditFragmentCallback{
        fun fabOnClickToMainFragment(habit: Habit)
        fun setTitleToAdd()
    }
}

private operator fun AutoCompleteTextView.invoke(priority: String) {
    setText(priority)
}
