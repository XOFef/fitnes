package com.example.myapplication.training.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentTrainingBinding
import com.example.myapplication.training.ui.adapters.VpAdapter
import com.example.myapplication.training.utils.TrainingUtils
import com.google.android.material.tabs.TabLayoutMediator


class TrainingFragment : Fragment() {
    private lateinit var binding: FragmentTrainingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vpAdapter = VpAdapter(this)
        binding.vp.adapter = vpAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp){tab, pos ->
            tab.text = getString(TrainingUtils.tabTitle[pos])
        }.attach()
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

}