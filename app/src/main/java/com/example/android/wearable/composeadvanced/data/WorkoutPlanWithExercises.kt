package com.example.android.wearable.composeadvanced.data

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutPlanWithExercises(
    @Embedded val workoutPlan: WorkoutPlan,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutPlanId"
    )
    val exercises: List<Exercise>
)
