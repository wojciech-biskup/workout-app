package com.example.android.wearable.composeadvanced.data

import androidx.room.*

@Dao
interface ExerciseDao {
    @Insert
    fun insert(exercise: Exercise) : Long

    @Transaction
    @Query("SELECT * FROM exercise")
    fun getAllWithSets(): List<ExerciseWithSets>

    @Query("SELECT * FROM exercise WHERE id = :id")
    fun getWithSetsById(id: Int): ExerciseWithSets

    @Update
    fun update(exercise: Exercise)

    @Delete
    fun delete(exercise: Exercise)
}
