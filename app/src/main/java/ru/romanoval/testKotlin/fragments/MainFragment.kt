package ru.romanoval.testKotlin.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.adapters.ViewPagerAdapter


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("onAttach frag")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        println("OnCreate fragment ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView frag")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        println("onStart frag")
    }

    override fun onResume() {
        super.onResume()
        println("onResume frag")
    }

    override fun onPause() {
        super.onPause()
        println("onPause frag")
    }

    override fun onStop() {
        super.onStop()
        println("onStop frag")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("onDestroyView frag")
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach frag")
    }

    override fun onDestroy() {
        println("onDestroy frag")
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        println("OnViewCreated frag")

        if(savedInstanceState == null){
            println("savedInstanceState is null")
            viewPager2.adapter = ViewPagerAdapter(this)
            val tabNames = listOf("Хорошие", "Плохие")

            TabLayoutMediator(tabLayout,viewPager2) {
                    tab, position ->
                tab.text = tabNames[position]
            }.attach()

            val hab = MainFragmentArgs.fromBundle(requireArguments()).editOrAddedHabit

            if(hab != null){
                println(hab)
            }
        }else{
            println("savedInstanceState isn't null")
        }

        super.onViewCreated(view, savedInstanceState)
    }




}