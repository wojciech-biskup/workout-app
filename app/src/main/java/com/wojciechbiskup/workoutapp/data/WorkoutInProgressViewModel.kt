package com.wojciechbiskup.workoutapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WorkoutInProgressViewModel : ViewModel() {
    lateinit var workoutPlanWithExercises: WorkoutPlanWithExercises
    lateinit var exerciseWithSets: ExerciseWithSets
    lateinit var exerciseSet: ExerciseSet
    var finishedExercises = HashSet<Exercise>()
    var finishedSets = HashSet<ExerciseSet>()
    var isWorkoutInProgress by mutableStateOf(false)
    var showFinishedWorkoutToast by mutableStateOf(false)
    var showFinishedExerciseToast by mutableStateOf(false)
}
