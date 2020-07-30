package ru.romanoval.testKotlin

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_bad.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_good.*
import kotlinx.android.synthetic.main.header_layout.view.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.data.model.HabitJson
import ru.romanoval.testKotlin.ui.ApiViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.mainFragment2)

        val appBarConfiguration = AppBarConfiguration(navController.graph, navigationDrawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)

        navigationView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.my_navigation_graph)
        }

        var navDrawer = navigationView.getHeaderView(0)
        var imgView: ImageView = navDrawer.fragmentAboutImageView

        Glide.with(this)
            .load("https://doubletapp.ru/wp-content/uploads/2018/12/logo_for_vk.png")
            .override(150, 150)
            .placeholder(ColorDrawable(Color.BLACK))
            .error(R.drawable.ic_launcher_background)
            .transform(CircleCrop())
            .into(imgView)

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
        } else if (navigationDrawerLayout != null && bottomSheetFilterBad != null) {
            val bottomSheetBadBehavior = BottomSheetBehavior.from(bottomSheetFilterBad)
            if (bottomSheetBadBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
                filterFindBad.text = null
                filterTypeSpinnerBad.text = null

                bottomSheetBadBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }

    }
}