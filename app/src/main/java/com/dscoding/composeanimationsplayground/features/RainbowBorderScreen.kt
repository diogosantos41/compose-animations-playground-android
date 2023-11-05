package com.dscoding.composeanimationsplayground.features

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dscoding.composeanimationsplayground.R

@Composable
fun RainbowBorderScreen() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing)), label = ""
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "",
            modifier = Modifier
                .drawBehind {
                    rotate(rotationAnimation.value) {
                        drawCircle(
                            rainbowColorsBrush,
                            style = Stroke(60f)
                        )
                    }
                }
                .clip(CircleShape)
        )
    }
}

val rainbowColorsBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFF0000),  // Red
        Color(0xFFFF7F00),  // Orange
        Color(0xFFFFFF00),  // Yellow
        Color(0xFF00FF00),  // Green
        Color(0xFF0000FF),  // Blue
        Color(0xFF4B0082),  // Indigo
        Color(0xFF9400D3)   // Violet
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)  // Adjust the end offset as needed
)