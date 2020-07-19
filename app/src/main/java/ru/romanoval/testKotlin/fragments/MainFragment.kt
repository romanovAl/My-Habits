package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.adapters.MainActivityViewPager2Adapter
import ru.romanoval.testKotlin.model.Habit


class MainFragment : Fragment(R.layout.fragment_main) {


    companion object {

        fun newInstance() = MainFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager2.adapter = MainActivityViewPager2Adapter(this)

        val tabNames = listOf("Хорошие", "Плохие")

        TabLayoutMediator(tabLayout,viewPager2) {
            tab, position ->
            tab.text = tabNames[position]
        }.attach()

        val hab = MainFragmentArgs.fromBundle(requireArguments()).editOrAddedHabit

        if(hab != null){

            println(hab)

        }

        super.onViewCreated(view, savedInstanceState)
    }




}