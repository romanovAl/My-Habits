package ru.romanoval.testKotlin.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_filter_good.*
import kotlinx.android.synthetic.main.fragment_good_habits.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.ui.HabitsViewModel
import ru.romanoval.testKotlin.utils.InjectorUtils

class FragmentGoodHabits : Fragment(R.layout.fragment_good_habits) {

    companion object {
        fun newInstance(name: String): FragmentGoodHabits = FragmentGoodHabits()
    }

    private var adapter: RecyclerAdapter? = null
    private lateinit var curView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        curView = view

        initUi()
    }

    override fun onPause() {
        super.onPause()
        filterFindGood.text = null
        filterTypeSpinnerGood.text = null
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            println("From fragment - bottomSheet state isn't collapsed, collapsing")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initUi() {
        val factory = InjectorUtils.provideHabitsViewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(HabitsViewModel::class.java)

        fabGoodHabits.setColorFilter(Color.argb(255, 255, 255, 255))

        val filterTypes =
            listOf("По приоритету", "По периодичности", "По количеству", "Без фильтра")
        val adapterFilterTypes = context?.let { ArrayAdapter(it, R.layout.list_item, filterTypes) }
        (filterInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterFilterTypes)

        viewModel.getHabits().observe(viewLifecycleOwner, Observer { habits ->
            adapter = RecyclerAdapter(habits.filter { it.type } as ArrayList<Habit>)
            recyclerGoodHabits?.adapter = adapter
        })

        fabGoodHabits.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragment2ToAddEditFragment("Добавление привычки")
            Navigation.findNavController(curView).navigate(action)
        }

        filterFindGood.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null) {

                    var habits = viewModel.getHabits().value
                    habits = habits?.filter {
                        it.name.contains(p0.toString(), ignoreCase = true)
                    } as ArrayList<Habit>

                    val adapter = RecyclerAdapter(habits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        findAndSortTextView.setOnClickListener {
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        filterTypeSpinnerGood.keyListener = null

        filterSortUp.setOnClickListener {
            val habits = viewModel.getHabits().value
            val sortedHabits = habits as ArrayList<Habit>
            when (filterTypeSpinnerGood.text.toString()) {

                filterTypes[0] -> { //Сортировка по приоритету
                    sortedHabits.sortByDescending { it.priority.intPriority }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
                filterTypes[1] -> { //Сортировка по периодичности
                    sortedHabits.sortByDescending { it.period.intPeriod }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
                filterTypes[2] -> { //Сортировка по количеству раз
                    sortedHabits.sortByDescending { it.times }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
            }
        }

        filterSortDown.setOnClickListener {
            val habits = viewModel.getHabits().value
            val sortedHabits = habits as ArrayList<Habit>
            when (filterTypeSpinnerGood.text.toString()) {
                filterTypes[0] -> { //Сортировка по приоритету
                    sortedHabits.sortBy { it.priority.intPriority }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
                filterTypes[1] -> { //Сортировка по периодичности
                    sortedHabits.sortBy { it.period.intPeriod }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
                filterTypes[2] -> { //Сортировка по количеству раз
                    sortedHabits.sortBy { it.times }
                    val adapter =
                        RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<Habit>)
                    recyclerGoodHabits?.adapter = adapter
                }
            }
        }
    }
}