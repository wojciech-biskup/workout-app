package com.wojciechbiskup.workoutapp.presentation.ui

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.wojciechbiskup.workoutapp.data.Exercise
import com.wojciechbiskup.workoutapp.data.ExerciseDao
import com.wojciechbiskup.workoutapp.data.WorkoutInProgressViewModel
import com.wojciechbiskup.workoutapp.data.WorkoutPlanDao
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen

@Composable
fun WorkoutPlanScreen(
    workoutPlanId: Int,
    workoutPlanDao: WorkoutPlanDao,
    exerciseDao: ExerciseDao,
    workoutInProgressViewModel: WorkoutInProgressViewModel,
    navController: NavHostController
) {
    val workoutPlanWithExercises = workoutPlanDao.getWithExercisesById(workoutPlanId)
    val workoutPlan = workoutPlanWithExercises.workoutPlan
    val exercises = workoutPlanWithExercises.exercises
    val isThisWorkoutInProgress = workoutInProgressViewModel.isWorkoutInProgress && workoutPlanWithExercises == workoutInProgressViewModel.workoutPlanWithExercises
    val context = LocalContext.current

    val inputTextKey = "input_text"

    var workoutPlanName by remember { mutableStateOf(workoutPlan.name.toString()) }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newInputText: CharSequence? = results.getCharSequence(inputTextKey)
                workoutPlanName = newInputText as String
                workoutPlan.name = workoutPlanName
                workoutPlanDao.update(workoutPlan)
            }
        }

    if (workoutInProgressViewModel.showFinishedWorkoutToast) {
        val toast = Toast.makeText(context, "Workout finished", Toast.LENGTH_SHORT)
        toast.show()
        val handler = Handler()
        handler.postDelayed(Runnable { toast.cancel() }, 1000)
        workoutInProgressViewModel.showFinishedWorkoutToast = false
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
            val color = if (isThisWorkoutInProgress) {
                Color.Gray
            } else {
                Color.Blue
            }

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!isThisWorkoutInProgress) {
                        launcher.launch(intent)
                    } else {
                        val toast = Toast.makeText(context, "Workout in progress", Toast.LENGTH_SHORT)
                        toast.show()
                    }
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

        if (exercises.isNotEmpty()) {
            item {
                val text = if (isThisWorkoutInProgress) {
                    "Back to workout"
                } else {
                    "Start workout"
                }
                val color = if (workoutInProgressViewModel.isWorkoutInProgress && !isThisWorkoutInProgress) {
                    Color.Gray
                } else {
                    Color.Cyan
                }

                Chip(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        if (!workoutInProgressViewModel.isWorkoutInProgress || isThisWorkoutInProgress) {
                            workoutInProgressViewModel.workoutPlanWithExercises = workoutPlanWithExercises
                            workoutInProgressViewModel.isWorkoutInProgress = true
                            navController.navigate(Screen.WorkoutInProgress.route)
                        } else {
                            val toast = Toast.makeText(context, "Workout in progress", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    },
                    label = {
                        Text(text = text)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.PlayArrow,
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
            val setsCount = exerciseWithSets.sets.size
            val color = if (isThisWorkoutInProgress) {
                Color.Gray
            } else {
                Color.Blue
            }

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!isThisWorkoutInProgress) {
                        navController.navigate(Screen.Exercise.route + "/" + id)
                    } else {
                        val toast = Toast.makeText(context, "Workout in progress", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                },
                label = {
                    Text(text = name)
                },
                secondaryLabel = {
                    Text(text = "Series: $setsCount")
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

        if (exercises.isNotEmpty()) {
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
            val color = if (isThisWorkoutInProgress) {
                Color.Gray
            } else {
                Color.Cyan
            }

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!isThisWorkoutInProgress) {
                        val newExercise = Exercise(0, workoutPlanId, "New exercise")
                        val newExerciseId = exerciseDao.insert(newExercise)
                        navController.navigate(Screen.Exercise.route + "/" + newExerciseId)
                    } else {
                        val toast = Toast.makeText(context, "Workout in progress", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                },
                label = {
                    Text(text = "Add exercise")
                },
                icon = {
                    Icon(
                        Icons.Filled.Add,
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
            val color = if (isThisWorkoutInProgress) {
                Color.Gray
            } else {
                Color.Red
            }

            Chip(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!isThisWorkoutInProgress) {
                        workoutPlanDao.delete(workoutPlan)
                        navController.popBackStack()
                    } else {
                        val toast = Toast.makeText(context, "Workout in progress", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                },
                label = {
                    Text(text = "Delete plan")
                },
                icon = {
                    Icon(
                        Icons.Filled.Delete,
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
