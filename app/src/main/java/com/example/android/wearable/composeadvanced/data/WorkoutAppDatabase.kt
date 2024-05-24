package com.example.android.wearable.composeadvanced.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutPlan::class, Exercise::class, ExerciseSet::class],
    version = 1
)
abstract class WorkoutAppDatabase : RoomDatabase() {
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseSetDao(): ExerciseSetDao
}
