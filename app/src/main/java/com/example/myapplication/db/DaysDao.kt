package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(dayModel: DayModel)

    @Query("SELECT * FROM day_model_table WHERE id =:dayId ")
    suspend fun getDayById(dayId: Int): DayModel

    @Query("SELECT * FROM day_model_table WHERE difficulty =:difficulty ")
    fun getAllDayDifficulty(difficulty: String): Flow<List<DayModel>>
}