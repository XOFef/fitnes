package com.example.myapplication.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.adapters.ExerciseModel

class MainViewModel : ViewModel() {
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
}