package com.mansao.moneymanagercapstone.helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mansao.moneymanagercapstone.ui.addtask.money.fragments.*

class SectionPagerAdapter(activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AllTransactionFragment()
            1 -> fragment = DayTransactionFragment()
            2 -> fragment = WeekTransactionFragment()
            3 -> fragment = MonthTransactionFragment()
            4 -> fragment = YearTransactionFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 5
    }
}