package com.mobile.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.liderstoreagent.ui.screens.CheckoutFragment
import com.mobile.liderstoreagent.ui.screens.checkout.viewpager.OutlayFragment
import com.mobile.liderstoreagent.ui.screens.checkout.viewpager.TransferMoneyFragment

class CheckOutViewAdapter(fragmentActivity: FragmentActivity,val start:String,val end:String) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransferMoneyFragment.newInstance(start,end)
            1 -> OutlayFragment.newInstance(start,end)
            else -> CheckoutFragment()
        }
    }
}