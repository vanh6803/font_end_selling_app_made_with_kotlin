package com.example.sellingappkotlin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sellingappkotlin.components.fragments.CartFragment
import com.example.sellingappkotlin.components.fragments.HomeFragment
import com.example.sellingappkotlin.components.fragments.NotificationFragment
import com.example.sellingappkotlin.components.fragments.PersonFragment

class BottomNavAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment.newInstance()
            1 -> fragment = CartFragment.newInstance()
            2 -> fragment = NotificationFragment.newInstance()
            3 -> fragment = PersonFragment.newInstance()
        }
        return fragment!!
    }

    override fun getItemCount(): Int {
        return 4
    }
}