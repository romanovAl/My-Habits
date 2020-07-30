package ru.romanoval.testKotlin.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.filterSortDown
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.filterSortUp
import kotlinx.android.synthetic.main.fragment_bad_habits.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.ui.ApiViewModel
import ru.romanoval.testKotlin.data.model.HabitJson
import ru.romanoval.testKotlin.utils.Lists

class FragmentBadHabits : Fragment(R.layout.fragment_bad_habits){

    companion object {
        fun newInstance(name: String): FragmentBadHabits = FragmentBadHabits()
    }

    private lateinit var adapter: RecyclerAdapter
    private lateinit var curView: View
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.uploadToServer) {
            apiViewModel.uploadHabitsToApi()
            return true
        } else if (item.itemId == R.id.downloadFromServer) {
            apiViewModel.downloadHabitsFromApi()
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        curView = view

        initUi()
    }

    override fun onPause() {
        super.onPause()

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            filterFindBad.text = null
            filterTypeSpinnerBad.text = null
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    private fun initUi() {


        val filterTypes = Lists.getFilterTypes(requireContext())

        apiViewModel = ViewModelProvider(this).get(ApiViewModel::class.java)

        apiViewModel.isDataLoading.observe(viewLifecycleOwner, Observer { isLoading ->

            if (isLoading!!) {
                progressBarBad.visibility = View.VISIBLE
                recyclerBadHabits.visibility = View.INVISIBLE
                bottomSheetFilterBad.visibility = View.INVISIBLE
            } else {
                progressBarBad.visibility = View.INVISIBLE
                recyclerBadHabits.visibility = View.VISIBLE
                bottomSheetFilterBad.visibility = View.VISIBLE
            }

        })

        apiViewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            adapter =
                RecyclerAdapter(habits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>,
                    requireContext())
            recyclerBadHabits.adapter = adapter

            filterFindBad.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                    if (p0 != null) {

                        val filteredHabits: ArrayList<HabitJson> = habits.filter {
                            it.title.contains(p0.toString(), ignoreCase = true)
                        } as ArrayList<HabitJson>

                        adapter.updateHabits(filteredHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

            filterSortUp.setOnClickListener {
                val sortedHabits = habits as ArrayList<HabitJson>
                when (filterTypeSpinnerBad.text.toString()) {

                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortByDescending { it.priority }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortByDescending { it.frequency }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortByDescending { it.count }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                }
            }

            filterSortDown.setOnClickListener {
                val sortedHabits = habits as ArrayList<HabitJson>
                when (filterTypeSpinnerBad.text.toString()) {
                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortBy { it.priority }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortBy { it.frequency }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortBy { it.count }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
                    }
                }
            }

            filterTypeSpinnerBad.onItemClickListener =
                AdapterView.OnItemClickListener() { _, _, p, _ ->
                    if (p == 3) {
                        val sortedHabits = habits as ArrayList<HabitJson>
                        sortedHabits.sortBy { it.bdId }
                        adapter.updateHabits(sortedHabits.filter { !(it.type.toBoolean()) } as ArrayList<HabitJson>)
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

        val behavior = BottomSheetBehavior.from(bottomSheetFilterBad)

        bottomSheetFilterBad.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                bottomSheetFilterBad.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val hidden = bottomSheetFilterBad.getChildAt(1)
                behavior.peekHeight = hidden.top
            }
        })

        fabBadHabits.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragment2ToAddEditFragment(
                    this.resources.getString(
                        R.string.label_add
                    )
                )
            Navigation.findNavController(curView).navigate(action)
        }

        filterTypeSpinnerBad.keyListener = null

    }

    fun Int.toBoolean(): Boolean {
        return this == 1
    }
}