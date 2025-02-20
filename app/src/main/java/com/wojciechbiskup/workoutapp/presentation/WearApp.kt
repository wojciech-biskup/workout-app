package com.wojciechbiskup.workoutapp.presentation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.wojciechbiskup.workoutapp.data.WorkoutAppDatabase
import com.wojciechbiskup.workoutapp.data.WorkoutInProgressViewModel
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen
import com.wojciechbiskup.workoutapp.presentation.theme.WearAppTheme
import com.wojciechbiskup.workoutapp.presentation.theme.initialThemeValues
import com.wojciechbiskup.workoutapp.presentation.ui.*
import com.wojciechbiskup.workoutapp.presentation.ui.LandingScreen

@Composable
fun WearApp(
    modifier: Modifier = Modifier,
    swipeDismissableNavController: NavHostController = rememberSwipeDismissableNavController()
) {
    val workoutAppDb = Room.databaseBuilder(
        LocalContext.current,
        WorkoutAppDatabase::class.java, "workout-app-database"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    val workoutPlanDao = workoutAppDb.workoutPlanDao()
    val exerciseDao = workoutAppDb.exerciseDao()
    val exerciseSetDao = workoutAppDb.exerciseSetDao()
    val workoutInProgressViewModel: WorkoutInProgressViewModel = viewModel()
    val themeColors by remember { mutableStateOf(initialThemeValues.colors) }

    WearAppTheme(colors = themeColors) {
        Scaffold(
            modifier = modifier
        ) {
            SwipeDismissableNavHost(
                navController = swipeDismissableNavController,
                startDestination = Screen.Landing.route,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                composable(
                    route = Screen.Landing.route,
                ) {
                    LandingScreen(
                        workoutPlanDao = workoutPlanDao,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.WorkoutPlan.route + "/{workoutPlanId}",
                    arguments = listOf(
                        navArgument("workoutPlanId") { type = NavType.IntType }
                    )
                ) {
                    val workoutPlanId = it.arguments!!.getInt("workoutPlanId")

                    WorkoutPlanScreen(
                        workoutPlanId = workoutPlanId,
                        workoutPlanDao = workoutPlanDao,
                        exerciseDao = exerciseDao,
                        workoutInProgressViewModel = workoutInProgressViewModel,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.Exercise.route + "/{exerciseId}",
                    arguments = listOf(
                        navArgument("exerciseId") { type = NavType.IntType }
                    )
                ) {
                    val exerciseId = it.arguments!!.getInt("exerciseId")

                    ExerciseScreen(
                        exerciseId = exerciseId,
                        exerciseDao = exerciseDao,
                        exerciseSetDao = exerciseSetDao,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.ExerciseSet.route + "/{exerciseSetId}",
                    arguments = listOf(
                        navArgument("exerciseSetId") { type = NavType.IntType }
                    )
                ) {
                    val exerciseSetId = it.arguments!!.getInt("exerciseSetId")

                    ExerciseSetScreen(
                        exerciseSetId = exerciseSetId,
                        exerciseSetDao = exerciseSetDao,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.Repetition.route + "/{exerciseSetId}",
                    arguments = listOf(
                        navArgument("exerciseSetId") { type = NavType.IntType }
                    )
                ) {
                    val exerciseSetId = it.arguments!!.getInt("exerciseSetId")

                    RepetitionScreen(
                        exerciseSetId = exerciseSetId,
                        exerciseSetDao = exerciseSetDao
                    )
                }

                composable(
                    route = Screen.Weight.route + "/{exerciseSetId}",
                    arguments = listOf(
                        navArgument("exerciseSetId") { type = NavType.IntType }
                    )
                ) {
                    val exerciseSetId = it.arguments!!.getInt("exerciseSetId")

                    WeightScreen(
                        exerciseSetId = exerciseSetId,
                        exerciseSetDao = exerciseSetDao
                    )
                }

                composable(
                    route = Screen.WorkoutInProgress.route
                ) {
                    WorkoutInProgressScreen(
                        workoutInProgressViewModel = workoutInProgressViewModel,
                        exerciseDao = exerciseDao,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.ExerciseInProgress.route
                ) {
                    ExerciseInProgressScreen(
                        workoutInProgressViewModel = workoutInProgressViewModel,
                        navController = swipeDismissableNavController
                    )
                }

                composable(
                    route = Screen.ExerciseSetInProgress.route
                ) {
                    ExerciseSetInProgress(
                        workoutInProgressViewModel = workoutInProgressViewModel,
                        navController = swipeDismissableNavController)
                }
            }
        }
    }
}
