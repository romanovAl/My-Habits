package ru.romanoval.testKotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_good.*



class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.mainFragment2)

        val appBarConfiguration = AppBarConfiguration(navController.graph, navigationDrawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)

        navigationView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.my_navigation_graph)
        }

    }

    override fun onBackPressed() {

        if (navigationDrawerLayout != null && bottomSheetFilterBad != null && bottomSheetFilterGood != null) {

            val bottomSheetGoodBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
            val bottomSheetBadBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)

            if (navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                if (bottomSheetGoodBehavior.state != BottomSheetBehavior.STATE_COLLAPSED ||
                    bottomSheetBadBehavior.state != BottomSheetBehavior.STATE_COLLAPSED
                ) {
                    filterFindGood.text = null
                    filterFindBad.text = null

                    filterTypeSpinnerGood.text = null
                    filterTypeSpinnerBad.text = null

                    bottomSheetGoodBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    bottomSheetBadBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    super.onBackPressed()
                }
            }
        } else if (navigationDrawerLayout != null && bottomSheetFilterGood != null) {


            if (navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFilterGood)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {

                    filterFindGood.text = null

                    filterTypeSpinnerGood.text = null

                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    super.onBackPressed()
                }
            }
        }else if(navigationDrawerLayout != null && bottomSheetFilterBad != null){
            val bottomSheetBadBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)
            if(bottomSheetBadBehavior.state != BottomSheetBehavior.STATE_COLLAPSED){
                filterFindBad.text = null
                filterTypeSpinnerBad.text = null

                bottomSheetBadBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                super.onBackPressed()
            }
        }
        else{
            super.onBackPressed()
        }

    }


}