package com.example.myapplication.exercises.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.DayModel
import com.example.myapplication.db.ExerciseModel
import com.example.myapplication.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val mainDb: MainDb
): ViewModel() {
    val exerciseList = MutableLiveData<List<ExerciseModel>>()

    fun getDayExerciseList(dayModel: DayModel?) = viewModelScope.launch {
        val day = dayModel?.id?.let { mainDb.daysDao.getDayById(it) }
        val allExerciseList = mainDb.ExerciseDao.getAllExercises()
        val tempExerciseList = ArrayList<ExerciseModel>()
        day?.let { dayModel ->
            dayModel.exercises.split(",").forEach{ index ->
                tempExerciseList.add(allExerciseList[index.toInt()])
            }
            exerciseList.value = tempExerciseList
        }
    }
}