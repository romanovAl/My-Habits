package ru.romanoval.testKotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.adapters.ViewPagerAdapter


class MainFragment : Fragment(R.layout.fragment_main) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager2.adapter = ViewPagerAdapter(this)
        val tabNames = listOf(
            this.resources.getString(R.string.view_pager_good),
            this.resources.getString(R.string.view_pager_bad)
        )

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabNames[position]
        }.attach()


        super.onViewCreated(view, savedInstanceState)
    }

}