package com.example.myapplication.training.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.DayModel
import com.example.myapplication.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val mainDb: MainDb
): ViewModel() {
    val daysList = MutableLiveData<List<DayModel>>()

    fun getExerciseDaysByDifficult(difficulty: String){
        viewModelScope.launch {
            mainDb.daysDao.getAllDayDifficulty(difficulty).collect{ list ->
                daysList.value = list
            }
        }
    }
}