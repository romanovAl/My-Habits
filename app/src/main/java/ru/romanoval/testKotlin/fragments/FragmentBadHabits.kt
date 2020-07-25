package ru.romanoval.testKotlin.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.*
import kotlinx.android.synthetic.main.fragment_bad_habits.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.ui.HabitsRoomViewModel
import ru.romanoval.testKotlin.utils.Lists

class FragmentBadHabits : Fragment(R.layout.fragment_bad_habits) {

    companion object {
        fun newInstance(name: String): FragmentBadHabits = FragmentBadHabits()
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
        filterFindBad.text = null
        filterTypeSpinnerBad.text = null
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initUi() {

        val filterTypes = Lists.getFilterTypes(requireContext())

        val viewModelRoom = ViewModelProvider(this).get(HabitsRoomViewModel::class.java)

        viewModelRoom.habits.observe(viewLifecycleOwner, Observer { habits ->
            adapter = RecyclerAdapter(habits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
            recyclerBadHabits.adapter = adapter

            filterFindBad.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                    if (p0 != null) {

                        val filteredHabits: ArrayList<HabitRoom> = habits.filter {
                            it.name.contains(p0.toString(), ignoreCase = true)
                        } as ArrayList<HabitRoom>

                        val adapter =
                            RecyclerAdapter(filteredHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

            filterSortUp.setOnClickListener {
                val sortedHabits = habits as ArrayList<HabitRoom>
                when (filterTypeSpinnerBad.text.toString()) {

                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortByDescending { it.priority.intPriority }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortByDescending { it.period.intPeriod }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortByDescending { it.times }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                }
            }

            filterSortDown.setOnClickListener {
                val sortedHabits = habits as ArrayList<HabitRoom>
                when (filterTypeSpinnerBad.text.toString()) {
                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortBy { it.priority.intPriority }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortBy { it.period.intPeriod }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortBy { it.times }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { !it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerBadHabits?.adapter = adapter
                    }
                }
            }

        })

        fabBadHabits.setColorFilter(Color.argb(255, 255, 255, 255))

        val adapterFilterTypes = context?.let { ArrayAdapter(it, R.layout.list_item, filterTypes) }
        (filterInputLayoutBad.editText as? AutoCompleteTextView)?.setAdapter(adapterFilterTypes)

        findAndSortTextViewBad.setOnClickListener {
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        fabBadHabits.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragment2ToAddEditFragment(this.resources.getString(R.string.label_add))
            Navigation.findNavController(curView).navigate(action)
        }

        filterTypeSpinnerBad.keyListener = null

    }
}