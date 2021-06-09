package com.galih.appfilm.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.galih.appfilm.movies.MoviesFragment
import com.galih.appfilm.search.SearchFragment
import com.galih.appfilm.core.utils.TAB_TITLES

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MoviesFragment()
            1 -> SearchFragment()
            else -> Fragment()
        }
}