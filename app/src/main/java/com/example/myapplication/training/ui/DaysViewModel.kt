package com.example.myapplication.training.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.DayModel
import com.example.myapplication.db.MainDb
import com.example.myapplication.training.data.TrainingTopCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val mainDb: MainDb
): ViewModel() {
    val daysList = MutableLiveData<List<DayModel>>()
    val topCardUpdata = MutableLiveData<TrainingTopCardModel>()

    fun getExerciseDaysByDifficult(trainingTopCardModel: TrainingTopCardModel){
        viewModelScope.launch {
            mainDb.daysDao.getAllDayDifficulty(trainingTopCardModel.difficulty).collect{ list ->
                daysList.value = list
                topCardUpdata.value = trainingTopCardModel.copy(
                    maxProgress = list.size,
                    progress = getProgress(list)
                )
            }
        }
    }

    private fun getProgress(list: List<DayModel>): Int{
        var counter = 0
        list.forEach{ day ->
            if(day.isDone){
                counter++
            }
        }
        return counter
    }
}