package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DayModel::class,
        ExerciseModel::class,
        WeightModel::class,
        StatisticModel::class
    ],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val daysDao: DaysDao
    abstract val ExerciseDao: ExerciseDao
    abstract val weightDao: WeightDao
    abstract val statisticDao: StatisticDao
}