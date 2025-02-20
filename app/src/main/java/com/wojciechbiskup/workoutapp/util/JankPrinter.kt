package com.wojciechbiskup.workoutapp.util

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.wojciechbiskup.workoutapp.BuildConfig
import com.wojciechbiskup.workoutapp.presentation.navigation.Screen
import java.util.concurrent.TimeUnit

class JankPrinter {
    private var stateHolder: PerformanceMetricsState.Holder? = null
    private lateinit var jankStats: JankStats
    private var nonJank = 0

    private fun Long.nanosToMillis() = "${TimeUnit.NANOSECONDS.toMillis(this)}ms"

    fun installJankStats(activity: Activity) {
        val contentView = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as ComposeView

        if (!BuildConfig.DEBUG) {
            stateHolder = PerformanceMetricsState.getHolderForHierarchy(contentView).apply {
                state?.putState("Activity", activity.javaClass.simpleName)
                state?.putState("route", Screen.Landing.route)
            }

            jankStats = JankStats.createAndTrack(
                activity.window
            ) {
                if (it.isJank) {
                    val route =
                        it.states.find { state -> state.key == "route" }?.value.orEmpty()
                    val duration = it.frameDurationUiNanos.nanosToMillis()
                    Log.w("Jank", "Jank $duration route:$route non:$nonJank")
                    nonJank = 0
                } else {
                    nonJank++
                }
            }.apply {
                jankHeuristicMultiplier = 3f
            }
        }
    }

    fun setRouteState(route: String?) {
        stateHolder?.state?.let {
            if (route != null) {
                it.putState("route", route)
            } else {
                it.removeState("route")
            }
        }
    }
}
