package com.dscoding.composeanimationsplayground.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItem(val title: String, val icon: ImageVector, val route: String) {
    HOME("Home", Icons.Default.Home, Screen.HomeScreen.route),
    RAINBOW_BORDER("Rainbow Border", Icons.Default.Favorite, Screen.RainbowBorderScreen.route),
    LOTTIE_ANIMATION("Lottie Animation", Icons.Default.Favorite, Screen.LottieAnimationScreen.route),
    LIQUID_BOTTOM_BAR("Liquid Floating Button", Icons.Default.Favorite, Screen.LiquidBottomBarScreen.route),
    ANIMATED_PROGRESS_INDICATOR("Progress Indicator", Icons.Default.Favorite, Screen.AnimatedProgressIndicator.route),
    LOADING_ANIMATIONS("Loading Animations", Icons.Default.Favorite, Screen.LoadingAnimations.route)
}


