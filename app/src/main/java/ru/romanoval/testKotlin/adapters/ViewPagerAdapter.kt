package ru.romanoval.testKotlin.adapters


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.romanoval.testKotlin.fragments.FragmentBadHabits
import ru.romanoval.testKotlin.fragments.FragmentGoodHabits


class ViewPagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> FragmentGoodHabits.newInstance("Good habits")
        else -> FragmentBadHabits.newInstance("Bad Habits")
    }
}