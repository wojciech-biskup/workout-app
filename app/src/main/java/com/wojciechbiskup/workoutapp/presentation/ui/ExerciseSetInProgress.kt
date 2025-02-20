package com.wojciechbiskup.workoutapp.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Scale
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.wojciechbiskup.workoutapp.data.WorkoutInProgressViewModel

@Composable
fun ExerciseSetInProgress(
    workoutInProgressViewModel: WorkoutInProgressViewModel,
    navController: NavHostController
) {
    val exerciseSet = workoutInProgressViewModel.exerciseSet

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Spacer(modifier = Modifier.padding(24.dp)) }

        item {
            val repetitionsCount = exerciseSet.repetitionsCount.toString()

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {

                },
                label = {
                    Text(text = repetitionsCount)
                },
                secondaryLabel = {
                    Text(text = "Repetitions")
                },
                icon = {
                    Icon(
                        Icons.Filled.Repeat,
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
            val weight = exerciseSet.weight

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {

                },
                label = {
                    Text(text = "$weight kg")
                },
                secondaryLabel = {
                    Text(text = "Weight")
                },
                icon = {
                    Icon(
                        Icons.Filled.Scale,
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

        item {
            val finishedSets = workoutInProgressViewModel.finishedSets

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    finishedSets.add(exerciseSet)
                    navController.popBackStack()
                },
                label = {
                    Text(text = "Finish series")
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
