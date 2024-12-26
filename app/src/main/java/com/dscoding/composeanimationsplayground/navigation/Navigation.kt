package com.dscoding.composeanimationsplayground.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dscoding.composeanimationsplayground.features.AnimatedProgressIndicatorScreen
import com.dscoding.composeanimationsplayground.features.DVDAnimationScreen
import com.dscoding.composeanimationsplayground.features.HomeScreen
import com.dscoding.composeanimationsplayground.features.LiquidBottomBarScreen
import com.dscoding.composeanimationsplayground.features.LoadingAnimationsScreen
import com.dscoding.composeanimationsplayground.features.LottieAnimationScreen
import com.dscoding.composeanimationsplayground.features.RainbowBorderScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        )
        {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(route = Screen.RainbowBorderScreen.route) {
                RainbowBorderScreen()
            }
            composable(route = Screen.LottieAnimationScreen.route) {
                LottieAnimationScreen()
            }
            composable(route = Screen.LiquidBottomBarScreen.route) {
                LiquidBottomBarScreen()
            }
            composable(route = Screen.AnimatedProgressIndicatorScreen.route) {
                AnimatedProgressIndicatorScreen()
            }
            composable(route = Screen.LoadingAnimationsScreen.route) {
                LoadingAnimationsScreen()
            }
            composable(route = Screen.DVDAnimationScreen.route) {
                DVDAnimationScreen()
            }
        }
    }
}