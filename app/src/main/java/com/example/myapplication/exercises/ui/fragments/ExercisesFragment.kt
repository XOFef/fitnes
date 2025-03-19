package  com.example.myapplication.exercises.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.myapplication.db.ExerciseModel
import com.example.myapplication.databinding.ExerciseBinding
import com.example.myapplication.db.DayModel
import com.example.myapplication.exercises.ui.ExerciseViewModel
import com.example.myapplication.fragments.DayFinishFragment
import com.example.myapplication.utils.FragmentManager
import com.example.myapplication.utils.TimeUtils
import com.example.myapplication.utils.getDayFromArguments
import dagger.hilt.android.AndroidEntryPoint
import pl.droidsonroids.gif.GifDrawable

@AndroidEntryPoint
class ExercisesFragment : Fragment() {

    private lateinit var binding: ExerciseBinding
    private var ab: ActionBar? = null
    private val model: ExerciseViewModel by viewModels()

    private var currentDay: DayModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = getDayFromArguments()
        updateExercise()
        updateTime()
        currentDay?.let { model.getExercises(it) }
        ab = (activity as AppCompatActivity).supportActionBar
        binding.bNext.setOnClickListener{
            model.nextExercise()
        }
    }


    private fun updateExercise() = with(binding) {
        model.updateExercise.observe(viewLifecycleOwner) { exercise ->
            imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            tvName.text = exercise.name
            subTitle.text = exercise.subTitle
            showTime(exercise)
        }
    }

    private fun updateTime() = with(binding) {
        model.updateTime.observe(viewLifecycleOwner) { time ->
            tvTime.text = TimeUtils.getTime(time)
        }
    }

    private fun showTime(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            binding.tvTime.text = exercise.time
        } else {
            model.startTimer(exercise.time.toLong())
        }
    }

    override fun onDetach() {
        super.onDetach()
        model.onPause()
    }


}