package com.wojciechbiskup.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val workoutPlanId: Int,
    var name: String?
)
