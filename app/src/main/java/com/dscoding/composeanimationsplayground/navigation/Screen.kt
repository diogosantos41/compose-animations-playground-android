package com.dscoding.composeanimationsplayground.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object RainbowBorderScreen: Screen("rainbow_border_screen")
    object LottieAnimationScreen: Screen ("lottie_animation_screen")
    object LiquidBottomBarScreen: Screen ("liquid_bottom_bar_screen")
    object AnimatedProgressIndicator: Screen("animated_progress_indicator")
    object LoadingAnimations : Screen("loading_animations")
}