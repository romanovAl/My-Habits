package ru.romanoval.testKotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.romanoval.testKotlin.adapters.RecyclerAdapter
import ru.romanoval.testKotlin.fragments.*
import ru.romanoval.testKotlin.model.Habit
import ru.romanoval.testKotlin.viewModels.MainActivityViewModel


class MainActivity : AppCompatActivity(),
    FragmentGoodHabits.FragmentGoodHabitsCallback,
    FragmentBadHabits.FragmentBadHabitsCallback,
    RecyclerAdapter.RecyclerCallback,
    AddEditFragment.AddEditFragmentCallback{


    private lateinit var curNavHostFragment: NavHostFragment

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)   {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        curNavHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragment2) as NavHostFragment

        navController = curNavHostFragment.navController

        navController.setGraph(R.navigation.my_navigation_graph)

        val appBarCompat = AppBarConfiguration(navController.graph, navigationDrawerLayout)

        navigationView.setupWithNavController(navController)

        toolbar.setupWithNavController(navController, appBarCompat)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("onSaveInstanceState")
        outState.putBundle("nav", navController.saveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("onRestoreInstanceState")
        navController.restoreState(savedInstanceState.getBundle("nav"))
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

        println("От така хуйня малята ${supportFragmentManager.findFragmentById(R.id.addEditFragment).toString()}")

        action.editOrAddedHabit = habit
        navController.navigate(action)
    }

    override fun setTitleToAdd() {
        toolbar.title = "Добавление привычки"
    }



}