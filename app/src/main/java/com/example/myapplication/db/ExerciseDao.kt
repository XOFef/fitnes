package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise_table")
    suspend fun getAllExercises(): List<ExerciseModel>
}