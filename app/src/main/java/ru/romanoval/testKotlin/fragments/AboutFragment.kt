package ru.romanoval.testKotlin.fragments

import androidx.fragment.app.Fragment
import ru.romanoval.testKotlin.R


class AboutFragment : Fragment(R.layout.fragment_about) {

    companion object {
        fun newInstance(param1: String, param2: String) =
            AboutFragment()
    }
}