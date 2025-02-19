package com.example.myapplication.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.databinding.ExercisesListFragmentBinding
import com.example.myapplication.databinding.WaitingFragmentBinding
import com.example.myapplication.utils.MainViewModel

const val COUNT_DOWN_TIME = 11000L

class WaitingFragment : Fragment() {

    private lateinit var binding: WaitingFragmentBinding
    private lateinit var adapter: ExerciseAdapter
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pBar.max = COUNT_DOWN_TIME.toInt()
    }


    private fun startTimer() = with(binding){
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 100){
            override fun onTick(restTime: Long) {

                pBar.progress = restTime.toInt()
            }

            override fun onFinish() {

            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}