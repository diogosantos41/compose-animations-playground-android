package com.dscoding.composeanimationsplayground.features

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dscoding.composeanimationsplayground.R
import kotlinx.coroutines.delay

data class DvdState(
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    var velocityX: Float = 4f,
    var velocityY: Float = 4f,
    val size: Int = 60
)

@Composable
fun DVDAnimationScreen() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val colors = listOf(
            Color.Red,
            Color.Green,
            Color.Cyan,
            Color.Yellow,
            Color.Blue
        )

        var currentColorIndex by remember { mutableIntStateOf(0) }
        var dvdState by remember { mutableStateOf(DvdState(size = 60)) }
        val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value
        val screenWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val screenHeight = with(LocalDensity.current) { maxHeight.toPx() }

        LaunchedEffect(key1 = dvdState) {
            while (true) {
                delay(8) // ~60 FPS
                dvdState = updateDVDPosition(
                    dvdState,
                    screenWidth,
                    screenHeight,
                    statusBarHeight,
                    onBounce = {
                        currentColorIndex = (currentColorIndex + 1) % colors.size
                    })
            }
        }
        Icon(
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer {
                    translationX = dvdState.offsetX
                    translationY = dvdState.offsetY
                },
            imageVector = ImageVector.vectorResource(R.drawable.dvd_logo),
            contentDescription = "DVD screen saver logo",
            tint = colors[currentColorIndex]
        )
    }

}

private fun updateDVDPosition(
    dvdState: DvdState,
    screenWidth: Float,
    screenHeight: Float,
    statusBarHeight: Float,
    onBounce: () -> Unit
): DvdState {
    var newOffsetX = dvdState.offsetX + dvdState.velocityX
    var newOffsetY = dvdState.offsetY + dvdState.velocityY
    // Bounce off edges
    if (newOffsetX + statusBarHeight + dvdState.size * 2 >= screenWidth || newOffsetX <= 0) {
        dvdState.velocityX *= -1
        newOffsetX = dvdState.offsetX + dvdState.velocityX
        onBounce()
    }
    if (newOffsetY + statusBarHeight + dvdState.size * 2 >= screenHeight || newOffsetY <= 0) {
        dvdState.velocityY *= -1
        newOffsetY = dvdState.offsetY + dvdState.velocityY
        onBounce()
    }

    return dvdState.copy(offsetX = newOffsetX, offsetY = newOffsetY)
}