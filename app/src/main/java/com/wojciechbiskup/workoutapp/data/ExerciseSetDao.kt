package com.wojciechbiskup.workoutapp.data

import androidx.room.*

@Dao
interface ExerciseSetDao {
    @Insert
    fun insert(exerciseSet: ExerciseSet) : Long

    @Query("SELECT * FROM exerciseSet")
    fun getAll(): List<ExerciseSet>

    @Query("SELECT * FROM exerciseSet WHERE id = :id")
    fun getById(id: Int): ExerciseSet

    @Update
    fun update(exerciseSet: ExerciseSet)

    @Delete
    fun delete(exerciseSet: ExerciseSet)
}
