package com.example.android.wearable.composeadvanced.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.android.wearable.composeadvanced.data.WorkoutInProgressViewModel
import com.example.android.wearable.composeadvanced.presentation.navigation.Screen

@Composable
fun ExerciseInProgressScreen(
    workoutInProgressViewModel: WorkoutInProgressViewModel,
    navController: NavHostController
) {
    val exerciseWithSets = workoutInProgressViewModel.exerciseWithSets
    val exercise = exerciseWithSets.exercise
    val sets = exerciseWithSets.sets
    val finishedSets = workoutInProgressViewModel.finishedSets

    if (finishedSets.containsAll(sets)) {
        val finishedExercises = workoutInProgressViewModel.finishedExercises
        finishedExercises.add(exercise)
        workoutInProgressViewModel.showFinishedExerciseToast = true
        navController.popBackStack()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Spacer(modifier = Modifier.padding(24.dp)) }

        item {
            val exerciseName = exercise.name.toString()

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {

                },
                label = {
                    Text(text = exerciseName)
                },
                secondaryLabel = {
                    Text(text = "Nazwa ćwiczenia")
                },
                icon = {
                    Icon(
                        Icons.Filled.FitnessCenter,
                        "",
                        Modifier.size(ChipDefaults.IconSize),
                        tint = Color.Blue
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Color.Blue
                    )
                )
            )
        }

        item {
            Divider(
                modifier = Modifier.padding(8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
        }

        items(sets) { exerciseSet ->
            val repetitionsCount = exerciseSet.repetitionsCount
            val weight = exerciseSet.weight
            val secondaryLabelText = if (finishedSets.contains(exerciseSet)) {
                "Ukończono"
            } else {
                val setNumber = sets.indexOf(exerciseSet) + 1
                "Seria nr $setNumber"
            }
            val color = if (!finishedSets.contains(exerciseSet)) {
                Color.Blue
            } else {
                Color.Gray
            }

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!finishedSets.contains(exerciseSet)) {
                        workoutInProgressViewModel.exerciseSet = exerciseSet
                        navController.navigate(Screen.ExerciseSetInProgress.route)
                    }
                },
                label = {
                    Text(text = "$repetitionsCount x $weight kg")
                },
                secondaryLabel = {
                    Text(text = secondaryLabelText)
                },
                icon = {
                    Icon(
                        Icons.Filled.FitnessCenter,
                        "",
                        Modifier.size(ChipDefaults.IconSize),
                        tint = color
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = color
                    )
                )
            )
        }

        item { Spacer(modifier = Modifier.padding(24.dp)) }
    }
}
