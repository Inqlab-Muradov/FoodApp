package com.example.foodapp.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.R
import com.example.foodapp.presentation.login.CustomTextField
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = Color(0xFFF6F6F6),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .border(
                                color = Color(0xFFCBF482),
                                shape = CircleShape,
                                width = 1.dp
                            )
                            .padding(8.dp),
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        tint = Color(0xFFCBF482)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Today 2 November"
                        )
                        Text(
                            text = "Welcome Muradov Inqlab",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                CalorieBox()
                Spacer(modifier = Modifier.height(24.dp))
                FoodItem.entries.forEachIndexed { index, foodItem ->
                    FoodItem(foodItem = foodItem, index = index)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBF482))
                ) {
                    Text(
                        text = "Calculate",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CalorieBox(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .background(color = Color(0xFFCBF482), shape = RoundedCornerShape(12.dp))
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.light),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Daily Intake",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "62.5%",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Box() {
                    CircularSlider()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    CalorieItem()
                }
            }
        }
    }
}

@Composable
fun CalorieItem(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Carbs",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        SliderTest()
        Text(
            text = "250/256 g"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderTest(modifier: Modifier = Modifier) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isProgress by remember { mutableStateOf(false) }
    val thumbSize = animateDpAsState(
        targetValue = if (isProgress) 24.dp else 36.dp,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )
    Slider(
        modifier = Modifier
            .width(80.dp)
            .height(8.dp),
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            isProgress = true
        },
        colors = SliderDefaults.colors(
            activeTrackColor = Color(0xFF9ECF28),
            inactiveTrackColor = Color.White,
        ),
        onValueChangeFinished = {
            isProgress = false
        },
        thumb = {},
        track = { sliderState ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                // Inactive track
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                )

                // Active track
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(sliderState.value)
                        .background(Color(0xFF9ECF28))
                )
            }
        }
    )
}

@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 12.dp,
    innerPadding: Dp = 12.dp,
    initialValue: Float = 0.00f,
) {
    var angle by remember { mutableStateOf(270 * initialValue) }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawArc(
                color = Color.White,
                startAngle = 150f,
                sweepAngle = 240f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFF9ECF28),
                startAngle = 135f,
                sweepAngle = angle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    clip = false,
                    spotColor = Color.Cyan,
                )
                .background(
                    color = Color(0xFFF1FDDB),
                    shape = CircleShape
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "100 cal"
            )
        }
    }
}


@Composable
fun FoodItem(modifier: Modifier = Modifier, foodItem: FoodItem, index: Int) {
    var breakfastValue by remember { mutableStateOf("") }
    var lunchValue by remember { mutableStateOf("") }
    var saladValue by remember { mutableStateOf("") }
    var dinnerValue by remember { mutableStateOf("") }

    var enable by remember { mutableStateOf(true) }
    var isFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val text = when (index) {
        0 -> breakfastValue
        1 -> lunchValue
        2 -> saladValue
        3 -> dinnerValue
        else -> ""
    }

    fun onValueChanged(value: String) {
        when (index) {
            0 -> breakfastValue = value
            1 -> lunchValue = value
            2 -> saladValue = value
            3 -> dinnerValue = value
        }
    }

    fun onAddClick() {
        enable = !enable
        isFocus = enable
    }

    LaunchedEffect(isFocus) {
        if (isFocus) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(foodItem.icon),
            contentDescription = null,
            modifier = Modifier
                .offset(y = (-24).dp)
                .size(56.dp)
                .background(color = Color(0xFFF6F6F6), shape = CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = foodItem.tittle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextField(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .padding(vertical = 8.dp),
                    text = text,
                    onValueChanged = { newValue ->
                        onValueChanged(newValue)
                    },
                    placeHolder = "Add ${foodItem.tittle}",
                    fontSize = 12.sp,
                    enabled = enable,
                    focusRequester = focusRequester
                )
                Button(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBF482)),
                    onClick = {
                        onAddClick()
                    }
                ) {
                    AnimatedContent(
                        targetState = enable
                    ) { enable ->
                        Text(
                            text = if (enable) "+Add" else "Edit",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

enum class FoodItem(val icon: Int, val tittle: String, val description: String) {
    BREAKFAST(
        icon = R.drawable.breakfast,
        tittle = "Breakfast",
        description = "Recommended 830-1000 cal"
    ),
    LUNCH(icon = R.drawable.lunch, tittle = "Lunch", description = "Recommended 830-1000 cal"),
    SALAD(icon = R.drawable.salad, tittle = "Salad", description = "Recommended 830-1000 cal"),
    DINNER(icon = R.drawable.dinner, tittle = "Dinner", description = "Recommended 830-1000 cal");
}