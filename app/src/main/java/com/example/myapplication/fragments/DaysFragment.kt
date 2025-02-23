package com.example.myapplication.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.DayModel
import com.example.myapplication.adapters.DaysAdapter
import com.example.myapplication.adapters.ExerciseModel
import com.example.myapplication.databinding.FragmentDaysBinding
import com.example.myapplication.utils.FragmentManager
import com.example.myapplication.utils.MainViewModel
import com.example.myapplication.utils.dialogManager


class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentDaysBinding
    private var ab: ActionBar? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        initRcView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            dialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_days_message,
                object : dialogManager.Listener {
                    override fun onClick() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }
                }
            )

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRcView() = with(binding) {
        adapter = DaysAdapter(this@DaysFragment)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.days)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }


    private fun fillDaysArray(): ArrayList<DayModel> {
        val tArray = ArrayList<DayModel>()
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter))
        }
        binding.pB.max = tArray.size
        tArray.forEach {
            if (it.isDone) daysDoneCounter++
        }
        updateRestDaysUI(tArray.size - daysDoneCounter, tArray.size)
        return tArray
    }

    private fun updateRestDaysUI(restDays: Int, days: Int) = with(binding) {
        val rDays = getString(R.string.rest) + " $restDays " + getString(R.string.rest_days)
        tvRestDays.text = rDays
        pB.progress = days - restDays
    }


    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split(",")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], false, exerciseArray[2]))
        }
        model.mutableListExercise.value = tempList
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fillExerciseList(day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {
            dialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_message,
                object : dialogManager.Listener {
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }
                }
            )
        }
    }
}