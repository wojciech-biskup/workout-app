package com.wojciechbiskup.workoutapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Landing : Screen("landing")
    object WorkoutPlan : Screen("workoutPlan")
    object Exercise : Screen("exercise")
    object ExerciseSet : Screen("exerciseSet")
    object Repetition : Screen("repetition")
    object Weight : Screen("weight")
    object WorkoutInProgress : Screen("workoutInProgress")
    object ExerciseInProgress : Screen("exerciseInProgress")
    object ExerciseSetInProgress : Screen("exerciseSetInProgress")
}
