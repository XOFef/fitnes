package com.example.myapplication.exercises.utils

import com.example.myapplication.db.ExerciseModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class ExerciseHelper @Inject constructor() {

    fun createExercisesStack(list: List<ExerciseModel>): List<ExerciseModel> {
        val tempList = ArrayList<ExerciseModel>()
        list.forEach { exercise ->
            tempList.add(
                exercise.copy(
                    time = "10",
                    subTitle = "Relax"
                )
            )
            tempList.add(
                exercise.copy(
                    subTitle = "Start"
                )
            )
            tempList.add(
                ExerciseModel(
                    null,
                    "Nice training",
                    "Exercise day finish",
                    "",
                    false,
                    "congratulations-congrats.gif",
                    0
                )
            )
        }
        return tempList
    }

    fun getExercisesOfTheDay(
        exercisesIndexes: String, list: List<ExerciseModel>
    ): List<ExerciseModel> {
        val exercisesIndexesArray = exercisesIndexes.split(",")
        val tempList = ArrayList<ExerciseModel>()
        for (i in exercisesIndexesArray.indices) {
            tempList.add(
                list[exercisesIndexesArray[i].toInt()]
            )
        }
        return tempList
    }
}