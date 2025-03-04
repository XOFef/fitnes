package com.example.myapplication.training.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragments.WaitingFragment
import com.example.myapplication.training.ui.fragments.DaysFragment
import com.example.myapplication.training.utils.TrainingUtils

class VpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TrainingUtils.difListType.size
    }

    override fun createFragment(position: Int): Fragment {
        return DaysFragment.newInstance(TrainingUtils.difListType[position])
    }
}