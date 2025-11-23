package com.example.foodapp.presentation.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapp.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit
) {

    var logoVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val screenHeightPx = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() } / 2

    val offsetY by animateDpAsState(
        targetValue = if (logoVisible) 0.dp else (screenHeightPx / density.density).dp,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        delay(100)
        logoVisible = true
    }

    LaunchedEffect(Unit) {
        delay(3000)
        navigateToLogin()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.pizza),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = Color(0xFFCBF482)),
            modifier = Modifier
                .size(112.dp)
                .offset(y = offsetY)
        )
    }
}



