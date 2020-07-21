package ru.romanoval.testKotlin.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment

class MainActivityViewModel : ViewModel() {

    private val mutableNavHostFragment: MutableLiveData<NavHostFragment> = MutableLiveData()

    val navHostFragment : LiveData<NavHostFragment> = mutableNavHostFragment

    fun postValueOfNavHostFragment(navHostFragment: NavHostFragment){
        mutableNavHostFragment.value = navHostFragment
    }


}