package com.wojciechbiskup.workoutapp.presentation.ui

import android.os.Handler
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.wojciechbiskup.workoutapp.data.ExerciseDao
import com.wojciechbiskup.workoutapp.data.WorkoutInProgressViewModel
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen

@Composable
fun WorkoutInProgressScreen(
    workoutInProgressViewModel: WorkoutInProgressViewModel,
    exerciseDao: ExerciseDao,
    navController: NavHostController
) {
    val workoutPlanWithExercises = workoutInProgressViewModel.workoutPlanWithExercises
    val workoutPlan = workoutPlanWithExercises.workoutPlan
    val exercises = workoutPlanWithExercises.exercises

    if (workoutInProgressViewModel.finishedExercises.containsAll(exercises)) {
        workoutInProgressViewModel.finishedSets.clear()
        workoutInProgressViewModel.finishedExercises.clear()
        workoutInProgressViewModel.isWorkoutInProgress = false
        workoutInProgressViewModel.showFinishedExerciseToast = false
        workoutInProgressViewModel.showFinishedWorkoutToast = true
        navController.popBackStack()
    }

    if (workoutInProgressViewModel.showFinishedExerciseToast) {
        val toast = Toast.makeText(LocalContext.current, "Exercise finished", Toast.LENGTH_SHORT)
        toast.show()
        val handler = Handler()
        handler.postDelayed(Runnable { toast.cancel() }, 1000)
        workoutInProgressViewModel.showFinishedExerciseToast = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Spacer(modifier = Modifier.padding(24.dp)) }

        item {
            val workoutPlanName = workoutPlan.name.toString()

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {

                },
                label = {
                    Text(text = workoutPlanName)
                },
                secondaryLabel = {
                    Text(text = "Plan name")
                },
                icon = {
                    Icon(
                        Icons.Filled.ListAlt,
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

        items(exercises) { exercise ->
            val id = exercise.id
            val name = exercise.name.toString()
            val exerciseWithSets = exerciseDao.getWithSetsById(id)
            val finishedExercises = workoutInProgressViewModel.finishedExercises
            val secondaryLabelText = if (finishedExercises.contains(exercise)) {
                "Finished"
            } else {
                val finishedSets = workoutInProgressViewModel.finishedSets
                var setsCount = exerciseWithSets.sets.size
                for (exerciseSet in finishedSets) {
                    if (exerciseSet.exerciseId == id) {
                        setsCount--
                    }
                }
                "Series left: $setsCount"
            }
            val color = if (!finishedExercises.contains(exercise)) {
                Color.Blue
            } else {
                Color.Gray
            }

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!finishedExercises.contains(exercise)) {
                        workoutInProgressViewModel.exerciseWithSets = exerciseWithSets
                        navController.navigate(Screen.ExerciseInProgress.route)
                    }
                },
                label = {
                    Text(text = name)
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

        item {
            Divider(
                modifier = Modifier.padding(8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
        }

        item {
            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    workoutInProgressViewModel.finishedSets.clear()
                    workoutInProgressViewModel.finishedExercises.clear()
                    workoutInProgressViewModel.isWorkoutInProgress = false
                    workoutInProgressViewModel.showFinishedExerciseToast = false
                    workoutInProgressViewModel.showFinishedWorkoutToast = true
                    navController.popBackStack()
                },
                label = {
                    Text(text = "Finish workout")
                },
                icon = {
                    Icon(
                        Icons.Filled.Close,
                        "",
                        Modifier.size(ChipDefaults.IconSize),
                        tint = Color.Cyan
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Color.Cyan
                    )
                )
            )
        }

        item { Spacer(modifier = Modifier.padding(24.dp)) }
    }
}
