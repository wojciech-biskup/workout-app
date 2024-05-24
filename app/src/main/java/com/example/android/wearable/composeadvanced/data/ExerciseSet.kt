package com.example.android.wearable.composeadvanced.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val exerciseId: Int,
    var repetitionsCount: Int?,
    var weight: Double?
)
