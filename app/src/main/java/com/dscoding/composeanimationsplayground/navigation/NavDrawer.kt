package com.dscoding.composeanimationsplayground.navigation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.math.roundToInt

enum class MenuState {
    EXPANDED, COLLAPSED
}

@Composable
fun NavDrawer(navController: NavHostController) {
    var screen by remember { mutableStateOf(NavigationItem.HOME) }
    var menuState by remember { mutableStateOf(MenuState.COLLAPSED) }
    val updateAnim = updateTransition(menuState, label = "MenuState")
    val scale = updateAnim.animateFloat(
        transitionSpec = {
            when {
                MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                    tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                }

                MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                    tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                }

                else -> {
                    snap()
                }
            }
        }, label = ""
    ) {
        when (it) {
            MenuState.EXPANDED -> 0.7f
            MenuState.COLLAPSED -> 1f
        }
    }
    val transitionOffset = updateAnim.animateOffset({
        when {
            MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            }

            MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            }

            else -> {
                snap()
            }
        }
    }, label = "") {
        when (it) {
            MenuState.EXPANDED -> Offset(750f, 60f)
            MenuState.COLLAPSED -> Offset(0f, 0f)
        }
    }

    val alphaMenu = updateAnim.animateFloat({
        when {
            MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                tween(durationMillis = 300)
            }

            MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                tween(durationMillis = 300)
            }

            else -> {
                snap()
            }
        }
    }, label = "") {
        when (it) {
            MenuState.EXPANDED -> 1f
            MenuState.COLLAPSED -> 0.5f
        }
    }

    val roundness = updateAnim.animateDp({
        when {
            MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                tween(durationMillis = 300)
            }

            MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                tween(durationMillis = 300)
            }

            else -> {
                snap()
            }
        }
    }, label = "") {
        when (it) {
            MenuState.EXPANDED -> 10.dp
            MenuState.COLLAPSED -> 0.dp
        }
    }

    val menuOffset = updateAnim.animateOffset({
        when {
            MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            }

            MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            }

            else -> {
                snap()
            }
        }
    }, label = "") {
        when (it) {
            MenuState.EXPANDED -> Offset(0f, 0f)
            MenuState.COLLAPSED -> Offset(-100f, 0f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        //side menu
        MenuComponent(
            Modifier
                .offset {
                    IntOffset(
                        menuOffset.value.x.roundToInt(),
                        menuOffset.value.y.roundToInt()
                    )
                }
                .alpha(alphaMenu.value),
        ) {
            screen = it
            navController.navigate(it.route)
            menuState = MenuState.COLLAPSED
        }
        StackLayersUI(scale, transitionOffset, roundness, alphaMenu)
        // dashboard content
        Column(modifier = Modifier
            .fillMaxSize()
            .scale(scale.value)
            .offset {
                IntOffset(
                    transitionOffset.value.x.toInt(),
                    transitionOffset.value.y.toInt()
                )
            }
            .clip(shape = RoundedCornerShape(roundness.value))
            .background(color = Color(0xFFebf2fa))) {
            TopAppBarUI(screen) {
                menuState = when (menuState) {
                    MenuState.EXPANDED -> MenuState.COLLAPSED
                    MenuState.COLLAPSED -> MenuState.EXPANDED
                }
            }
            Navigation(navController)
        }
    }
}

@Composable
fun TopAppBarUI(screen: NavigationItem, onMenuToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onMenuToggle()
                },
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = screen.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }

    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
    )
}

@Composable
fun StackLayersUI(
    scale: State<Float>,
    transitionOffset: State<Offset>,
    roundness: State<Dp>,
    alphaMenu: State<Float>
) {
    // stack layer 0
    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value - 0.05f)
            .offset {
                IntOffset(
                    transitionOffset.value.x.toInt() - 50,
                    transitionOffset.value.y.toInt()
                )
            }
            .background(
                Color(0xFFF3F6FA).copy(alpha = .90f),
                shape = RoundedCornerShape(roundness.value)
            )
            .padding(8.dp)
            .alpha(alphaMenu.value)
    )
    //stack layer 1
    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value - 0.08f)
            .offset {
                IntOffset(
                    transitionOffset.value.x.toInt() - 100,
                    transitionOffset.value.y.toInt()
                )
            }
            .background(
                Color(0xFFF3F6FA).copy(.5f),
                shape = RoundedCornerShape(roundness.value)
            )
            .padding(8.dp)
            .alpha(alphaMenu.value)
    )
}

@Composable
fun MenuComponent(modifier: Modifier, onMenuSelected: (NavigationItem) -> Unit) {

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.weight(1f))

        LazyColumn {

            items(NavigationItem.values()) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 0.dp, end = 16.dp, top = 20.dp, bottom = 10.dp)
                        .clickable {
                            onMenuSelected(it)
                        }
                ) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        //app version
        Text(
            text = "App version: 1.0",
            color = Color.White.copy(alpha = .4f),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp),
            fontWeight = FontWeight.Medium,
        )

    }

}