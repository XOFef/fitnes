package com.example.myapplication.training.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTrainingBinding
import com.example.myapplication.training.ui.DaysViewModel
import com.example.myapplication.training.ui.adapters.VpAdapter
import com.example.myapplication.training.utils.TrainingUtils
import com.google.android.material.tabs.TabLayoutMediator


class TrainingFragment : Fragment() {
    private val diffList = listOf(
        "easy",
        "middle",
        "hard"
    )
    private lateinit var binding: FragmentTrainingBinding
    private val model: DaysViewModel by activityViewModels()


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
        topCardObserver()
        binding.vp.adapter = vpAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp){tab, pos ->
            tab.text = getString(TrainingUtils.tabTitle[pos])
        }.attach()
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                model.getExerciseDaysByDifficult(TrainingUtils.topCardList[position].copy(
                    difficultyTitle = getString(TrainingUtils.tabTitle[position])
                ))
            }
        })
    }

    private fun topCardObserver() = with(binding){
        model.topCardUpdata.observe(viewLifecycleOwner){ card ->
            val alphaAnimation = AlphaAnimation(0.2f, 1.0f)
            alphaAnimation.duration = 700
            im.setImageResource(card.imageId)
            im.startAnimation(alphaAnimation)
            difficultyTitle.text = card.difficultyTitle
            pB.max = card.maxProgress
            animProgressBar(card.maxProgress - card.progress)
            val daysRestText = getString(R.string.rest) + " " + card.progress
            tvRestDays.text = daysRestText
        }
    }

    private fun animProgressBar(progress: Int){
        val anim = ObjectAnimator.ofInt(
            binding.pB,
            "progress",
            binding.pB.progress,
            progress * 100
        )
        anim.duration = 700
        anim.start()

    }

}