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
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.data.model.HabitRoom
import ru.romanoval.testKotlin.ui.HabitsRoomViewModel
import ru.romanoval.testKotlin.utils.Lists

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
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initUi() {

        val filterTypes = Lists.getFilterTypes(requireContext())

        val viewModelRoom = ViewModelProvider(this).get(HabitsRoomViewModel::class.java)

        viewModelRoom.habits.observe(viewLifecycleOwner, Observer { habits ->
            adapter = RecyclerAdapter(habits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
            recyclerGoodHabits.adapter = adapter

            filterFindGood.addTextChangedListener(object : TextWatcher { //Поиск по имени
                override fun afterTextChanged(p0: Editable?) {

                    if (p0 != null) {

                        val filteredHabits: ArrayList<HabitRoom> = habits.filter {
                            it.name.contains(p0.toString(), ignoreCase = true)
                        } as ArrayList<HabitRoom>

                        val adapter =
                            RecyclerAdapter(filteredHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

            filterSortUp.setOnClickListener { //Сортировка вверх
                val sortedHabits = habits as ArrayList<HabitRoom>
                when (filterTypeSpinnerGood.text.toString()) {

                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortByDescending { it.priority.intPriority }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortByDescending { it.period.intPeriod }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortByDescending { it.times }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                }
            }

            filterSortDown.setOnClickListener {//Сортировка вниз
                val sortedHabits = habits as ArrayList<HabitRoom>
                when (filterTypeSpinnerGood.text.toString()) {
                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortBy { it.priority.intPriority }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortBy { it.period.intPeriod }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortBy { it.times }
                        val adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits?.adapter = adapter
                    }
                }
            }

            filterTypeSpinnerGood.onItemClickListener =
                AdapterView.OnItemClickListener() { _, _, p, _ ->
                    if (p == 3) {
                        val sortedHabits = habits as ArrayList<HabitRoom>
                        sortedHabits.sortBy {
                            it.id
                        }
                        adapter =
                            RecyclerAdapter(sortedHabits.filter { it.type } as ArrayList<HabitRoom>, requireContext())
                        recyclerGoodHabits.adapter = adapter
                    }
                }

        })

        fabGoodHabits.setColorFilter(Color.argb(255, 255, 255, 255))

        val adapterFilterTypes = context?.let { ArrayAdapter(it, R.layout.list_item, filterTypes) }
        (filterInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterFilterTypes)

        findAndSortTextView.setOnClickListener {
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        fabGoodHabits.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragment2ToAddEditFragment(this.resources.getString(R.string.label_add))
            Navigation.findNavController(curView).navigate(action)
        }

        filterTypeSpinnerGood.keyListener = null

    }
}