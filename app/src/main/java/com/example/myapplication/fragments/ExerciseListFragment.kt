package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.databinding.ExercisesListFragmentBinding
import com.example.myapplication.utils.FragmentManager
import com.example.myapplication.utils.MainViewModel


class ExerciseListFragment : Fragment() {

    private lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }


    private fun init() = with(binding){
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}