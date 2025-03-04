package com.example.myapplication.training.ui.fragments

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
import com.example.myapplication.db.DayModel
import com.example.myapplication.adapters.DaysAdapter
import com.example.myapplication.databinding.FragmentDaysBinding
import com.example.myapplication.training.ui.DaysViewModel
import com.example.myapplication.utils.dialogManager


class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentDaysBinding
    private var ab: ActionBar? = null
    private val model: DaysViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }




    private fun initRcView() = with(binding) {
        adapter = DaysAdapter(this@DaysFragment)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
    }

    private fun updateAdapter(){
        model.daysList.observe(viewLifecycleOwner){ list ->
            adapter.submitList(list)
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    companion object {
        @JvmStatic
        fun newInstance(s: String) = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (day.isDone) {
            dialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_message,
                object : dialogManager.Listener {
                    override fun onClick() {


                    }
                }
            )
        } else{

        }
    }
}