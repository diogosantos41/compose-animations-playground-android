package com.dscoding.composeanimationsplayground.features

import android.graphics.BlurMaskFilter
import android.graphics.Rect
import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun LoadingAnimationsScreen() {
    Column(
        modifier  = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        TripleOrbitLoadingAnimation(modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(160.dp))
        PulseAnimation(modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(160.dp))
        BlurredAnimatedText(modifier = Modifier.fillMaxWidth())

    }
}



private const val PADDING_PERCENTAGE_OUTER_CIRCLE = 0.15f
private const val PADDING_PERCENTAGE_INNER_CIRCLE = 0.3f
private const val POSITION_START_OFFSET_OUTER_CIRCLE = 90f
private const val POSITION_START_OFFSET_INNER_CIRCLE = 135f

@Composable
fun TripleOrbitLoadingAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000),
        ),
        label = "rotation animation"
    )
    var width by remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = modifier
            .size(40.dp)
            .onSizeChanged {
                width = it.width
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 1.dp,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = rotation
                }
        )
        CircularProgressIndicator(
            strokeWidth = 1.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * PADDING_PERCENTAGE_INNER_CIRCLE).toDp()
                    }
                )
                .graphicsLayer {
                    rotationZ = rotation + POSITION_START_OFFSET_INNER_CIRCLE
                }
        )
        CircularProgressIndicator(
            strokeWidth = 1.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * PADDING_PERCENTAGE_OUTER_CIRCLE).toDp()
                    }
                )
                .graphicsLayer {
                    rotationZ = rotation + POSITION_START_OFFSET_OUTER_CIRCLE
                }
        )
    }
}

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
        ),
        label = "progress animation"
    )

    Box(
        modifier = modifier
            .size(60.dp)
            .graphicsLayer {
                scaleX = progress
                scaleY = progress
                alpha = 1f - progress
            }
            .border(
                width = 5.dp,
                color = Color.Red,
                shape = CircleShape
            )
    )
}

private const val ANIMATION_DURATION = 1000

@Composable
fun BlurredAnimatedText(
    modifier: Modifier = Modifier
) {
    val text = "Hello World is Loading"
    val blurList = text.mapIndexed { index, character ->
        if(character == ' ') {
            remember {
                mutableFloatStateOf(0f)
            }
        } else {
            val infiniteTransition = rememberInfiniteTransition(label = "infinite transition $index")
            infiniteTransition.animateFloat(
                initialValue = 10f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = (ANIMATION_DURATION / text.length) * index
                    )
                ),
                label = "blur animation"
            )
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        text.forEachIndexed { index, character ->
            Text(
                text = character.toString(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .graphicsLayer {
                        if(character != ' ') {
                            val blurAmount = blurList[index].value
                            renderEffect = BlurEffect(
                                radiusX = blurAmount,
                                radiusY = blurAmount
                            )
                        }
                    }
                    .then(
                        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                            Modifier.fullContentBlur(
                                blurRadius = { blurList[index].value.roundToInt() }
                            )
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}

private fun Modifier.fullContentBlur(
    blurRadius: () -> Int,
): Modifier {
    return drawWithCache {
        val radius = blurRadius()
        val nativePaint = Paint().apply {
            isAntiAlias = true

            if(radius > 0) {
                maskFilter = BlurMaskFilter(
                    radius.toFloat(),
                    BlurMaskFilter.Blur.NORMAL
                )
            }
        }

        onDrawWithContent {
            drawContent()

            drawIntoCanvas { canvas ->
                canvas.save()

                val rect = Rect(0, 0, size.width.toInt(), size.height.toInt())
                canvas.nativeCanvas.drawRect(rect, nativePaint)

                canvas.restore()
            }
        }
    }
}