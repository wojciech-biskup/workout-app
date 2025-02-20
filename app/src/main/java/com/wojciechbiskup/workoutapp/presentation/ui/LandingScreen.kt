package com.wojciechbiskup.workoutapp.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.MoreHoriz
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
import com.wojciechbiskup.workoutapp.data.WorkoutPlan
import com.wojciechbiskup.workoutapp.data.WorkoutPlanDao
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen

@Composable
fun LandingScreen(
    workoutPlanDao: WorkoutPlanDao,
    navController: NavHostController
) {
    val workoutPlansWithExercises = workoutPlanDao.getAllWithExercises()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Spacer(modifier = Modifier.padding(24.dp)) }

        items(workoutPlansWithExercises) { workoutPlanWithExercises ->
            val workoutPlan = workoutPlanWithExercises.workoutPlan
            val workoutPlanId = workoutPlan.id
            val workoutPlanName = workoutPlan.name.toString()
            val exercises = workoutPlanWithExercises.exercises
            val exercisesCount = exercises.size

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    navController.navigate(Screen.WorkoutPlan.route + "/" + workoutPlanId)
                },
                label = {
                    Text(text = workoutPlanName)
                },
                secondaryLabel = {
                    Text(text = "Exercises: $exercisesCount")
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

        if (workoutPlansWithExercises.isNotEmpty()) {
            item {
                Icon(
                    Icons.Filled.MoreHoriz,
                    "",
                    Modifier.size(ChipDefaults.IconSize),
                    tint = Color.DarkGray
                )
            }
        }

        item {
            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val newWorkoutPlan = WorkoutPlan(0, "New plan")
                    val newWorkoutPlanId = workoutPlanDao.insert(newWorkoutPlan)
                    navController.navigate(Screen.WorkoutPlan.route + "/" + newWorkoutPlanId)
                },
                label = {
                    Text(text = "Add plan")
                },
                icon = {
                    Icon(
                        Icons.Filled.Add,
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
