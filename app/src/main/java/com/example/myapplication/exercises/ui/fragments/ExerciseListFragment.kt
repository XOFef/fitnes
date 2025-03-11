package com.example.myapplication.exercises.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.databinding.ExercisesListFragmentBinding
import com.example.myapplication.db.DayModel
import com.example.myapplication.exercises.ui.ExerciseListViewModel
import com.example.myapplication.fragments.WaitingFragment
import com.example.myapplication.utils.FragmentManager
import com.example.myapplication.utils.MainViewModel


class ExerciseListFragment : Fragment() {

    private var dayModel: DayModel? = null
    private lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExerciseAdapter
    private var ab: ActionBar? = null
    private val model: ExerciseListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayModel = getDayFromArguments()
        init()
        exerciseListObserver()
        dayModel =getDayFromArguments()
        model.getDayExerciseList(dayModel)
    }

    private fun getDayFromArguments(): DayModel?{
        return arguments.let{ bundle ->
            if(Build.VERSION.SDK_INT >= 33){
                bundle?.getSerializable("day", DayModel::class.java)
            } else{
                bundle?.getSerializable("day") as DayModel
            }
        }
    }

    private fun init() = with(binding) {
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.exercises)
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            FragmentManager.setFragment(
                WaitingFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }

    private fun exerciseListObserver(){
        model.exerciseList.observe(viewLifecycleOwner){ list ->
            adapter.submitList(list)
        }
    }

}