package com.wojciechbiskup.workoutapp.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Scale
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.wojciechbiskup.workoutapp.data.ExerciseSetDao
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen

@Composable
fun ExerciseSetScreen(
    exerciseSetId: Int,
    exerciseSetDao: ExerciseSetDao,
    navController: NavHostController
) {
    val exerciseSet = exerciseSetDao.getById(exerciseSetId)
    val id = exerciseSet.id

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
                    navController.navigate(Screen.Repetition.route + "/" + id)
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
                    navController.navigate(Screen.Weight.route + "/" + id)
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
            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    exerciseSetDao.delete(exerciseSet)
                    navController.popBackStack()
                },
                label = {
                    Text(text = "Delete series")
                },
                icon = {
                    Icon(
                        Icons.Filled.Delete,
                        "",
                        Modifier.size(ChipDefaults.IconSize),
                        tint = Color.Red
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Color.Red
                    )
                )
            )
        }

        item { Spacer(modifier = Modifier.padding(24.dp)) }
    }
}
