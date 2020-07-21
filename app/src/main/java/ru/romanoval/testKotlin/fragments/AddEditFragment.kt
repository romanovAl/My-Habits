package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_add_edit.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.model.Habit


class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("On Attach Fragment")
        callback = context as AddEditFragmentCallback
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        println("OnCreate fragment ")
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        println("onCreateView frag")
//
//        return inflater.inflate(R.layout.fragment_add_edit,container, false)
//    }

//    override fun onStart() {
//        super.onStart()
//        println("onStart frag")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        println("onResume frag")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        println("onPause frag")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        println("onStop frag")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        println("onDestroyView frag")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        println("onDetach frag")
//    }
//
//    override fun onDestroy() {
//        println("onDestroy frag")
//        super.onDestroy()
//    }

    var callback: AddEditFragmentCallback? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        println("onViewCreated frag")
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
