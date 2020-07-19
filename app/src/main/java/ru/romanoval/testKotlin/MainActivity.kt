package ru.romanoval.testKotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.fragments.*
import ru.romanoval.testKotlin.model.Habit


class MainActivity : AppCompatActivity(),
    FragmentGoodHabits.FragmentGoodHabitsCallback,
    FragmentBadHabits.FragmentBadHabitsCallback,
    RecyclerAdapter.RecyclerCallback,
    AddEditFragment.AddEditFragmentCallback{


    private lateinit var navHostFragment: NavHostFragment

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?)   {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragment2) as NavHostFragment
        navController = navHostFragment.navController

        val appBarCompat = AppBarConfiguration(navController.graph, navigationDrawerLayout)

        navigationView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(navController.graph)

        toolbar.setupWithNavController(navController, appBarCompat)

//        NavigationUI.setupActionBarWithNavController(this,navController,navigationDrawerLayout)


        if (savedInstanceState != null){

            val host = NavHostFragment.create(R.navigation.my_navigation_graph)
            val fragment = MainFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFragment2, host)
                .setPrimaryNavigationFragment(host)
                .commit()

        }


    }

    override fun onBackPressed() {
        if(navigationDrawerLayout.isDrawerOpen(GravityCompat.START)){
            navigationDrawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }

    }

    override fun fabSetOnClick() {
        navController.navigate(R.id.action_mainFragment2_to_addEditFragment)
    }

    override fun constraintOnClick(hab: Habit) {
        val action = MainFragmentDirections.actionMainFragment2ToAddEditFragment()
        action.habitToEdit = hab

        navController.navigate(action)
    }

    override fun fabOnClickToMainFragment(habit: Habit) {
        val action = AddEditFragmentDirections.actionAddEditFragmentToMainFragment2()

        action.editOrAddedHabit = habit
        navController.navigate(action)
    }

    override fun setTitleToAdd() {
        toolbar.title = "Добавление привычки"
    }


}