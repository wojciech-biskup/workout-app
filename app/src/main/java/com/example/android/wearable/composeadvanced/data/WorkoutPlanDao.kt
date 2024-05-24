package com.example.android.wearable.composeadvanced.data

import androidx.room.*

@Dao
interface WorkoutPlanDao {
    @Insert
    fun insert(workoutPlan: WorkoutPlan) : Long

    @Transaction
    @Query("SELECT * FROM workoutPlan")
    fun getAllWithExercises(): List<WorkoutPlanWithExercises>

    @Query("SELECT * FROM workoutPlan WHERE id = :id")
    fun getWithExercisesById(id: Int): WorkoutPlanWithExercises

    @Update
    fun update(workoutPlan: WorkoutPlan)

    @Delete
    fun delete(workoutPlan: WorkoutPlan)
}
