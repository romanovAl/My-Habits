package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_filter_good.*
import kotlinx.android.synthetic.main.fragment_good_habits.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.ui.ApiViewModel
import ru.romanoval.testKotlin.data.model.HabitJson
import ru.romanoval.testKotlin.utils.Lists

class FragmentGoodHabits : Fragment(R.layout.fragment_good_habits) {

    companion object {
        fun newInstance(name: String): FragmentGoodHabits = FragmentGoodHabits()
    }

    private lateinit var adapter: RecyclerAdapter
    private lateinit var curView: View
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        val netCap = cm.getNetworkCapabilities(activeNetwork)

        val isConnected = (netCap != null && netCap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))

        if (item.itemId == R.id.uploadToServer) {
            return if(isConnected){
                apiViewModel.uploadHabitsToApi()
                true
            }else{
                Toast.makeText(requireContext(),
                    this.resources.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
                true
            }

        } else if (item.itemId == R.id.downloadFromServer) {
            return if(isConnected){
                apiViewModel.downloadHabitsFromApi()
                true
            }else{
                Toast.makeText(requireContext(),
                    this.resources.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
                true
            }
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
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initUi() {


        val filterTypes = Lists.getFilterTypes(requireContext())

        apiViewModel = ViewModelProvider(this).get(ApiViewModel::class.java)

        apiViewModel.isDataLoading.observe(viewLifecycleOwner, Observer { isLoading ->

            if(isLoading!!){
                progressBarGood.visibility = View.VISIBLE
                recyclerGoodHabits.visibility = View.INVISIBLE
                bottomSheetFilterGood.visibility = View.INVISIBLE
            }else{
                progressBarGood.visibility = View.INVISIBLE
                recyclerGoodHabits.visibility = View.VISIBLE
                bottomSheetFilterGood.visibility = View.VISIBLE
            }
        })


        apiViewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            adapter = RecyclerAdapter(habits.filter { it.type.toBoolean() } as ArrayList<HabitJson>,
                requireContext())
            recyclerGoodHabits.adapter = adapter

            filterFindGood.addTextChangedListener(object : TextWatcher { //Поиск по имени
                override fun afterTextChanged(p0: Editable?) {

                    if (p0 != null) {

                        val filteredHabits: ArrayList<HabitJson> = habits.filter {
                            it.title.contains(p0.toString(), ignoreCase = true)
                        } as ArrayList<HabitJson>

                        adapter.updateHabits(filteredHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

            filterSortUp.setOnClickListener { //Сортировка вверх
                val sortedHabits = habits as ArrayList<HabitJson>
                when (filterTypeSpinnerGood.text.toString()) {

                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortByDescending { it.priority }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortByDescending { it.frequency }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortByDescending { it.count }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                }
            }

            filterSortDown.setOnClickListener {//Сортировка вниз
                val sortedHabits = habits as ArrayList<HabitJson>
                when (filterTypeSpinnerGood.text.toString()) {
                    filterTypes[0] -> { //Сортировка по приоритету
                        sortedHabits.sortBy { it.priority }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                    filterTypes[1] -> { //Сортировка по периодичности
                        sortedHabits.sortBy { it.frequency }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                    filterTypes[2] -> { //Сортировка по количеству раз
                        sortedHabits.sortBy { it.count }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
                    }
                }
            }

            filterTypeSpinnerGood.onItemClickListener =
                AdapterView.OnItemClickListener() { _, _, p, _ ->
                    if (p == 3) {
                        val sortedHabits = habits as ArrayList<HabitJson>
                        sortedHabits.sortBy { it.bdId }
                        adapter.updateHabits(sortedHabits.filter { it.type.toBoolean() } as ArrayList<HabitJson>)
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

        val behavior = BottomSheetBehavior.from(bottomSheetFilterGood)

        bottomSheetFilterGood.viewTreeObserver.addOnGlobalLayoutListener (object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                bottomSheetFilterGood.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val hidden = bottomSheetFilterGood.getChildAt(1)
                behavior.peekHeight = hidden.top
            }
        })

        fabGoodHabits.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragment2ToAddEditFragment(
                    this.resources.getString(
                        R.string.label_add
                    )
                )
            Navigation.findNavController(curView).navigate(action)
        }
        filterTypeSpinnerGood.keyListener = null

    }

    fun Int.toBoolean(): Boolean {
        return this == 1
    }

}