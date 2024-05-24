package com.example.android.wearable.composeadvanced.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.android.wearable.composeadvanced.data.ExerciseSetDao

@Composable
fun WeightScreen(
    exerciseSetId: Int,
    exerciseSetDao: ExerciseSetDao,
) {
    val exerciseSet = exerciseSetDao.getById(exerciseSetId)
    val weight = exerciseSet.weight.toString()
    val kilograms = weight.substringBefore(".").toInt()
    val grams = weight.substringAfter(".").toInt()
    var kilogramsPickerValue by remember { mutableStateOf(kilograms) }
    var gramsPickerValue by remember {
        mutableStateOf(when(grams == 5) {
            true ->  50
            false -> grams
        })
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row {
                NumberPicker(
                    value = kilogramsPickerValue,
                    range =  0 .. 200,
                    onValueChange = {
                        kilogramsPickerValue = it
                        val newWeight = "$kilogramsPickerValue.$gramsPickerValue".toDouble()
                        exerciseSet.weight = newWeight
                        exerciseSetDao.update(exerciseSet)
                    },
                    dividersColor = Color.Blue,
                    textStyle = TextStyle(Color.White)
                )

                NumberPicker(
                    value = gramsPickerValue,
                    range =  0 .. 75 step 25,
                    onValueChange = {
                        gramsPickerValue = it
                        val newWeight = "$kilogramsPickerValue.$gramsPickerValue".toDouble()
                        exerciseSet.weight = newWeight
                        exerciseSetDao.update(exerciseSet)
                    },
                    dividersColor = Color.Blue,
                    textStyle = TextStyle(Color.White)
                )
            }
        }
    }
}
