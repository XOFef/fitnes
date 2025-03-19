package com.example.myapplication.exercises.ui

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.DayModel
import com.example.myapplication.db.ExerciseModel
import com.example.myapplication.db.MainDb
import com.example.myapplication.exercises.utils.ExerciseHelper
import com.example.myapplication.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val mainDb: MainDb,
    private val exerciseHelper: ExerciseHelper
) : ViewModel() {
    private var timer: CountDownTimer? = null
    var updateExercise = MutableLiveData<ExerciseModel>()
    var updateTime = MutableLiveData<Long>()
    var currentDay: DayModel? = null
    private var exercisesStack: List<ExerciseModel> = emptyList()
    private var doneExerciseCounter = 0

    private fun updateDay(dayModel: DayModel) = viewModelScope.launch {
        mainDb.daysDao.insertDay(dayModel)
    }

    private fun dayDone() {
        currentDay = currentDay?.copy(isDone = true)
        currentDay?.let { updateDay(it) }
    }

    fun getExercises(day: DayModel) {
        currentDay = day
        viewModelScope.launch {
            val exerciseList = mainDb.ExerciseDao.getAllExercises()
            val exercisesOfTheDay = exerciseHelper.getExercisesOfTheDay(
                day.exercises,
                exerciseList
            )
            exercisesStack = exerciseHelper.createExercisesStack(
                exercisesOfTheDay.subList(
                    day.doneExerciseCounter,
                    exercisesOfTheDay.size
                )
            )
            nextExercise()
        }
    }

    fun startTimer(time: Long)  {
        timer = object : CountDownTimer((time + 1) * 1000, 1) {
            override fun onTick(restTime: Long) {
                updateTime.value = restTime
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()
    }

    fun nextExercise(){
        timer?.cancel()
        val exercise = exercisesStack[doneExerciseCounter++]
        updateExercise.value = exercise
    }

    fun onPause(){
        timer?.cancel()
    }
}