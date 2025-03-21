package com.example.myapplication.exercises.ui.fragments

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.databinding.ExercisesListFragmentBinding
import com.example.myapplication.db.DayModel
import com.example.myapplication.exercises.ui.ExerciseListViewModel
import com.example.myapplication.fragments.WaitingFragment
import com.example.myapplication.utils.FragmentManager
import com.example.myapplication.utils.MainViewModel
import com.example.myapplication.utils.getDayFromArguments


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
        topCardObserver()
        init()
        exerciseListObserver()
        dayModel =getDayFromArguments()
        model.getDayExerciseList(dayModel)
    }



    private fun init() = with(binding) {
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.exercises)
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("day", dayModel)
            }
            findNavController().navigate(
                R.id.action_exerciseListFragment_to_exercisesFragment,
                bundle
                )
        }
    }

    private fun exerciseListObserver(){
        model.exerciseList.observe(viewLifecycleOwner){ list ->
            adapter.submitList(list)
        }
    }

    private fun topCardObserver(){
        model.topCardUpdata.observe(viewLifecycleOwner){ card ->
            binding.apply {
                val alphaAnimation = AlphaAnimation(0.2f, 1.0f)
                alphaAnimation.duration = 700
                im.setImageResource(card.imageId)
                im.startAnimation(alphaAnimation)

                val alphaAnimationText = AlphaAnimation(0.0f, 1.0f)
                alphaAnimationText.startOffset = 300
                alphaAnimationText.duration = 800
                difficultyTitle.setText(card.difficultyTitle)
                difficultyTitle.visibility = View.VISIBLE
                difficultyTitle.startAnimation(alphaAnimationText)

                val alphaAnimationText2 = AlphaAnimation(0.0f, 1.0f)
                alphaAnimationText2.startOffset = 600
                alphaAnimationText2.duration = 800
                val daysRest = card.maxProgress - card.progress
                val tvRestText = getString(R.string.rest) + " " + daysRest
                tvRestDays.text = if(daysRest==0){
                    getText(R.string.done)
                } else{
                    tvRestText
                }
                pB.max = card.maxProgress
                tvRestDays.visibility = View.VISIBLE
                tvRestDays.startAnimation(alphaAnimationText2)
                animProgressBar(card.progress)
            }
        }
    }

    private fun animProgressBar(progress: Int){
        val anim = ObjectAnimator.ofInt(
            binding.pB,
            "progress",
            binding.pB.progress,
            progress * 100
        )
        anim.startDelay = 900
        anim.duration = 700
        anim.start()

    }
}