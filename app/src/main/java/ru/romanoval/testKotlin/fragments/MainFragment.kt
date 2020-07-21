package ru.romanoval.testKotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.adapters.ViewPagerAdapter


class MainFragment : Fragment(R.layout.fragment_main) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager2.adapter = ViewPagerAdapter(this)
        val tabNames = listOf("Хорошие", "Плохие")

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        val hab = MainFragmentArgs.fromBundle(requireArguments()).editOrAddedHabit

        if (hab != null) {
            println(hab)
        }

        super.onViewCreated(view, savedInstanceState)
    }


}