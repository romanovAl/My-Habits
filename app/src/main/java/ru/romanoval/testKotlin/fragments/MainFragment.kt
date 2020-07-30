package ru.romanoval.testKotlin.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_good.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.romanoval.testKotlin.R
import ru.romanoval.testKotlin.adapters.ViewPagerAdapter
import ru.romanoval.testKotlin.ui.ApiViewModel


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

//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner, object: OnBackPressedCallback(true){
//                override fun handleOnBackPressed() {
//
//
//
//                }
//            }
//        )

        super.onViewCreated(view, savedInstanceState)
    }



}