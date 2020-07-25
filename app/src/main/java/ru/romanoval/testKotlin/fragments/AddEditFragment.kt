package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_edit.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.utils.Lists
import ru.romanoval.testKotlin.ui.AddEditFragmentViewModel
import ru.romanoval.testKotlin.ui.HabitsRoomViewModel


class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    private lateinit var viewModelAddEdit: AddEditFragmentViewModel

    private lateinit var viewModelRoom: HabitsRoomViewModel

    private val priorities = Lists.priorities
    private val periods = Lists.periods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelAddEdit = ViewModelProvider(this).get(AddEditFragmentViewModel::class.java)

        viewModelRoom = ViewModelProvider(this).get(HabitsRoomViewModel::class.java)

    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val habitToEdit = AddEditFragmentArgs.fromBundle(requireArguments()).habitRoomToEdit


        //Если произойдет смена конфигурации, сохранившаяся информация запишется в VM и здесь мы её заберем
        viewModelAddEdit.getSavedHabit().observe(viewLifecycleOwner, Observer { savedHabit ->

            if (savedHabit == null) //Если смены конфига не было
            {
                if (habitToEdit != null) //Редактирование
                {
                    addAndEditFab.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_24))

                    habitPriorityAddAndEdit(
                        when (habitToEdit.priority) {
                            HabitRoom.Priority.HIGH -> priorities[0]
                            HabitRoom.Priority.MEDIUM -> priorities[1]
                            HabitRoom.Priority.LOW -> priorities[2]
                            HabitRoom.Priority.NOPRIORITY -> ""
                        }
                    )

                    habitPeriodAddAndEdit(
                        when (habitToEdit.period) {
                            HabitRoom.Period.ANHOUR -> periods[0]
                            HabitRoom.Period.ADAY -> periods[1]
                            HabitRoom.Period.AWEEK -> periods[2]
                            HabitRoom.Period.AMONTH -> periods[3]
                            HabitRoom.Period.ANYEAR -> periods[4]
                            HabitRoom.Period.NOPERIOD -> ""
                        }
                    )

                    habitNameAddAndEdit.setText(habitToEdit.name)
                    habitDescriptionAddAndEdit.setText(habitToEdit.description)

                    if (habitToEdit.type) {
                        radioButtonGood.isChecked = true
                    } else {
                        radioButtonBad.isChecked = true
                    }

                    habitDoneAddEdit.setText(habitToEdit.times.toString())

                    init()

                    addAndEditFab.setOnClickListener {
                        val newHabit = HabitRoom(
                            habitToEdit.id,
                            habitNameAddAndEdit.text.toString(),
                            habitDescriptionAddAndEdit.text.toString(),
                            when (habitPriorityAddAndEdit.text.toString()) {
                                priorities[0] -> HabitRoom.Priority.HIGH
                                priorities[1] -> HabitRoom.Priority.MEDIUM
                                priorities[2] -> HabitRoom.Priority.LOW
                                else -> HabitRoom.Priority.NOPRIORITY
                            },
                            radioButtonGood.isChecked,
                            when (habitPeriodAddAndEdit.text.toString()) {
                                periods[0] -> HabitRoom.Period.ANHOUR
                                periods[1] -> HabitRoom.Period.ADAY
                                periods[2] -> HabitRoom.Period.AWEEK
                                periods[3] -> HabitRoom.Period.AMONTH
                                periods[4] -> HabitRoom.Period.ANYEAR
                                else -> HabitRoom.Period.NOPERIOD
                            },
                            "#757de8", //TODO color picker
                            if (habitDoneAddEdit.text.toString() == "") {
                                0
                            } else {
                                habitDoneAddEdit.text?.toString()?.toInt()
                            }
                        )

                        viewModelRoom.update(newHabit)

                        it.hideKeyboard()
                        Navigation.findNavController(requireView()).popBackStack()

                    }

                } else //Добавление
                {
                    init()

                    addAndEditFab.setOnClickListener {
                        val newHabit = HabitRoom(
                            null,
                            habitNameAddAndEdit.text.toString(),
                            habitDescriptionAddAndEdit.text.toString(),
                            when (habitPriorityAddAndEdit.text.toString()) {
                                priorities[0] -> HabitRoom.Priority.HIGH
                                priorities[1] -> HabitRoom.Priority.MEDIUM
                                priorities[2] -> HabitRoom.Priority.LOW
                                else -> HabitRoom.Priority.NOPRIORITY
                            },
                            radioButtonGood.isChecked,
                            when (habitPeriodAddAndEdit.text.toString()) {
                                periods[0] -> HabitRoom.Period.ANHOUR
                                periods[1] -> HabitRoom.Period.ADAY
                                periods[2] -> HabitRoom.Period.AWEEK
                                periods[3] -> HabitRoom.Period.AMONTH
                                periods[4] -> HabitRoom.Period.ANYEAR
                                else -> HabitRoom.Period.NOPERIOD
                            },
                            "#757de8", //TODO color picker
                            if (habitDoneAddEdit.text.toString() == "") {
                                0
                            } else {
                                habitDoneAddEdit.text?.toString()?.toInt()
                            }
                        )

                        viewModelRoom.insert(newHabit)

                        it.hideKeyboard()
                        Navigation.findNavController(requireView()).popBackStack()

                    }
                }
            } else //Если произошла смена конфига
            {
                val savedHabit = viewModelAddEdit.getSavedHabit().value

                if (savedHabit != null) {
                    habitPriorityAddAndEdit(
                        when (savedHabit.priority) {
                            HabitRoom.Priority.HIGH -> priorities[0]
                            HabitRoom.Priority.MEDIUM -> priorities[1]
                            HabitRoom.Priority.LOW -> priorities[2]
                            HabitRoom.Priority.NOPRIORITY -> ""
                        }
                    )

                    habitPeriodAddAndEdit(
                        when (savedHabit.period) {
                            HabitRoom.Period.ANHOUR -> periods[0]
                            HabitRoom.Period.ADAY -> periods[1]
                            HabitRoom.Period.AWEEK -> periods[2]
                            HabitRoom.Period.AMONTH -> periods[3]
                            HabitRoom.Period.ANYEAR -> periods[4]
                            HabitRoom.Period.NOPERIOD -> ""
                        }
                    )

                    habitNameAddAndEdit.setText(savedHabit.name)
                    habitDescriptionAddAndEdit.setText(savedHabit.description)

                    if (savedHabit.type) {
                        radioButtonGood.isChecked = true
                    } else {
                        radioButtonBad.isChecked = true
                    }

                    habitDoneAddEdit.setText(savedHabit.times.toString())

                    init()

                    if (habitToEdit != null)
                        addAndEditFab.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_24)) //Если редактирование

                    addAndEditFab.setOnClickListener {
                        val newHabit = HabitRoom(
                            null,
                            habitNameAddAndEdit.text.toString(),
                            habitDescriptionAddAndEdit.text.toString(),
                            when (habitPriorityAddAndEdit.text.toString()) {
                                priorities[0] -> HabitRoom.Priority.HIGH
                                priorities[1] -> HabitRoom.Priority.MEDIUM
                                priorities[2] -> HabitRoom.Priority.LOW
                                else -> HabitRoom.Priority.NOPRIORITY
                            },
                            radioButtonGood.isChecked,
                            when (habitPeriodAddAndEdit.text.toString()) {
                                periods[0] -> HabitRoom.Period.ANHOUR
                                periods[1] -> HabitRoom.Period.ADAY
                                periods[2] -> HabitRoom.Period.AWEEK
                                periods[3] -> HabitRoom.Period.AMONTH
                                periods[4] -> HabitRoom.Period.ANYEAR
                                else -> HabitRoom.Period.NOPERIOD
                            },
                            "#757de8", //TODO color picker
                            if (habitDoneAddEdit.text.toString() == "") {
                                0
                            } else {
                                habitDoneAddEdit.text?.toString()?.toInt()
                            }
                        )


                        if (habitToEdit != null) { //Редактирование
                            newHabit.id = habitToEdit.id
                            viewModelRoom.update(newHabit)
                        } else { //Добавление
                            viewModelRoom.insert(newHabit)
                        }

                        it.hideKeyboard()
                        Navigation.findNavController(requireView()).popBackStack()
                    }
                }


            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        //При смене конфига записываем в VM уже введеную на экране инфу
        val habit = HabitRoom(
            AddEditFragmentArgs.fromBundle(requireArguments()).habitRoomToEdit?.id,
            habitNameAddAndEdit.text.toString(),
            habitDescriptionAddAndEdit.text.toString(),
            when (habitPriorityAddAndEdit.text.toString()) {
                priorities[0] -> HabitRoom.Priority.HIGH
                priorities[1] -> HabitRoom.Priority.MEDIUM
                priorities[2] -> HabitRoom.Priority.LOW
                else -> HabitRoom.Priority.NOPRIORITY
            },
            radioButtonGood.isChecked,
            when (habitPeriodAddAndEdit.text.toString()) {
                periods[0] -> HabitRoom.Period.ANHOUR
                periods[1] -> HabitRoom.Period.ADAY
                periods[2] -> HabitRoom.Period.AWEEK
                periods[3] -> HabitRoom.Period.AMONTH
                periods[4] -> HabitRoom.Period.ANYEAR
                else -> HabitRoom.Period.NOPERIOD
            },
            "#757de8", //TODO color picker
            if (habitDoneAddEdit.text.toString() == "") {
                0
            } else {
                habitDoneAddEdit.text?.toString()?.toInt()
            }

        )
        viewModelAddEdit.setSavedHabit(habit)
    }

    private fun init() {
        val adapterPriority = context?.let { ArrayAdapter(it, R.layout.list_item, priorities) }
        (habitPriorityInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPriority)
        habitPriorityAddAndEdit.keyListener = null

        val adapterPeriod = context?.let { ArrayAdapter(it, R.layout.list_item, periods) }
        (habitPeriodInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPeriod)
        habitPeriodAddAndEdit.keyListener = null

        addAndEditFab.setColorFilter(Color.argb(255, 255, 255, 255))
    }
}

private operator fun AutoCompleteTextView.invoke(priority: String) {
    setText(priority)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
