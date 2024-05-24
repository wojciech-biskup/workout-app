package com.example.android.wearable.composeadvanced.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.android.wearable.composeadvanced.data.ExerciseSetDao

@Composable
fun RepetitionScreen(
    exerciseSetId: Int,
    exerciseSetDao: ExerciseSetDao,
) {
    val exerciseSet = exerciseSetDao.getById(exerciseSetId)
    val repetitionsCount = exerciseSet.repetitionsCount
    var repetitionsCountPickerValue by remember { mutableStateOf(repetitionsCount) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            NumberPicker(
                value = repetitionsCountPickerValue!!,
                range =  1 .. 50,
                onValueChange = {
                    repetitionsCountPickerValue = it

                    exerciseSet.repetitionsCount = it
                    exerciseSetDao.update(exerciseSet)
                },
                dividersColor = Color.Blue,
                textStyle = TextStyle(Color.White),
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
