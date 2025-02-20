package com.wojciechbiskup.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String?
)
