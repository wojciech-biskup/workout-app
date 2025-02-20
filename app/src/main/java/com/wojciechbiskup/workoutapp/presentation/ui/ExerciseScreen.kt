package com.wojciechbiskup.workoutapp.presentation.ui

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.wojciechbiskup.workoutapp.data.ExerciseDao
import com.wojciechbiskup.workoutapp.data.ExerciseSet
import com.wojciechbiskup.workoutapp.data.ExerciseSetDao
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen

@Composable
fun ExerciseScreen(
    exerciseId: Int,
    exerciseDao: ExerciseDao,
    exerciseSetDao: ExerciseSetDao,
    navController: NavHostController
) {
    val exerciseWithSets = exerciseDao.getWithSetsById(exerciseId)
    val exercise = exerciseWithSets.exercise
    val sets = exerciseWithSets.sets
    val inputTextKey = "input_text"

    var exerciseName by remember { mutableStateOf(exercise.name.toString()) }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newInputText: CharSequence? = results.getCharSequence(inputTextKey)
                exerciseName = newInputText as String
                exercise.name = exerciseName
                exerciseDao.update(exercise)
            }
        }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Spacer(modifier = Modifier.padding(24.dp)) }

        item {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(inputTextKey)
                    .setLabel("")
                    .wearableExtender {
                        setEmojisAllowed(true)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    launcher.launch(intent)
                },
                label = {
                    Text(text = exerciseName)
                },
                secondaryLabel = {
                    Text(text = "Exercise name")
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
            val id = exerciseSet.id
            val repetitionsCount = exerciseSet.repetitionsCount
            val weight = exerciseSet.weight
            val setNumber = sets.indexOf(exerciseSet) + 1

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    navController.navigate(Screen.ExerciseSet.route + "/" + id)
                },
                label = {
                    Text(text = "$repetitionsCount x $weight kg")
                },
                secondaryLabel = {
                    Text(text = "Series nr $setNumber")
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

        if (sets.isNotEmpty()) {
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
                    var repetitionsCount = 6
                    var weight = 25.0
                    if (sets.isNotEmpty()) {
                        val lastSetIndex = sets.lastIndex
                        repetitionsCount = sets[lastSetIndex].repetitionsCount!!
                        weight = sets[lastSetIndex].weight!!
                    }
                    val newExerciseSet = ExerciseSet(0, exerciseId, repetitionsCount, weight)
                    val newExerciseSetId = exerciseSetDao.insert(newExerciseSet)
                    navController.navigate(Screen.ExerciseSet.route + "/" + newExerciseSetId)
                },
                label = {
                    Text(text = "Add series")
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
                    exerciseDao.delete(exercise)
                    navController.popBackStack()
                },
                label = {
                    Text(text = "Delete exercise")
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
