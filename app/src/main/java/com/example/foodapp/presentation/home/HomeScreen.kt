package com.example.foodapp.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.packInts
import com.example.foodapp.R
import com.example.foodapp.presentation.auth.CustomTextField
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onTextChanged: (String, Int) -> Unit,
    onCalculate: () -> Unit,
    closeSnackBar:()->Unit,
    userEmail:String,
    navigateToLogin:()->Unit
) {
    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isEmptyMessage) {
        if(state.isEmptyMessage){
            snackHostState.showSnackbar("Food not found. Check the spelling and try again.")
        }
    }

    LaunchedEffect(snackHostState.currentSnackbarData) {
        if (snackHostState.currentSnackbarData != null) {
            delay(2000)
            closeSnackBar()
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = Color(0xFFF6F6F6),
        snackbarHost = {
            SnackbarHost(hostState = snackHostState)
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp),
            ) {
                Column(
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
                            val today = LocalDate.now()
                            val formattedDate = today.format(DateTimeFormatter.ofPattern("d MMMM"))

                            Text(text = "Today $formattedDate")

                            Text(
                                text = "Welcome $userEmail",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).clickable(
                                indication = null,
                                interactionSource = null
                            ){
                                navigateToLogin()
                            },
                            tint = Color(0xFFCBF482)
                        )
                    }
                    CalorieBox(state = state)
                    Spacer(modifier = Modifier.height(24.dp))
                    FoodItem.entries.forEachIndexed { index, foodItem ->
                        FoodItem(
                            foodItem = foodItem,
                            index = index,
                            state = state,
                            onTextChanged = onTextChanged
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onCalculate,
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
                if (state.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        trackColor = Color(0xFFCBF482),
                        color = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun CalorieBox(modifier: Modifier = Modifier, state: HomeState) {
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
                    val text = AnimatedPercentage(
                        state.initialCaloriePercentage ?: 0.0f,
                        isPercentage = true
                    )
                    Text(
                        text = "$text%",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Box() {
                    CircularSlider(state = state)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                state.calorieItemList.forEach {
                    CalorieItem(calorieItemData = it)
                }
            }
        }
    }
}

@Composable
fun CalorieItem(modifier: Modifier = Modifier, calorieItemData: CalorieItemData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = calorieItemData.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        SliderTest(calorieItemData = calorieItemData)
        val text = AnimatedPercentage(targetPercentage = calorieItemData.amount.toFloat())
        Text(
            text = "$text/256 g",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderTest(modifier: Modifier = Modifier, calorieItemData: CalorieItemData) {
    var isProgress by remember { mutableStateOf(false) }
    val thumbSize = animateDpAsState(
        targetValue = if (isProgress) 24.dp else 36.dp,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )

    val percentage = calorieItemData.percentage
    val value by animateFloatAsState(
        targetValue = percentage ?: 0.0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    Slider(
        modifier = Modifier
            .width(80.dp)
            .height(8.dp),
        value = value,
        onValueChange = {
            isProgress = true
        },
        colors = SliderDefaults.colors(
            activeTrackColor = calorieItemData.progressColor,
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
                        .background(calorieItemData.progressColor)
                )
            }
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 12.dp,
    innerPadding: Dp = 12.dp,
    state: HomeState
) {
    val targetAngle = 270f * (state.initialCaloriePercentage ?: 0f)

    val animatedAngle by animateFloatAsState(
        targetValue = targetAngle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

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
                sweepAngle = animatedAngle,
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
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val text = AnimatedPercentage(targetPercentage = state.calorie?.toFloat() ?: 0.0f)
                Text(
                    text = text,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "cal",
                )
            }
        }
    }
}


@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    foodItem: FoodItem,
    index: Int,
    state: HomeState,
    onTextChanged: (String, Int) -> Unit
) {

    fun getFoodText(index: Int): String {
        state.apply {
            return when (index) {
                0 -> breakfastValue
                1 -> lunchValue
                2 -> saladValue
                3 -> dinnerValue
                else -> ""
            }
        }
    }

    var enable by remember { mutableStateOf(true) }
    var isFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

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
                    text = getFoodText(index),
                    onValueChanged = { newValue ->
                        onTextChanged(newValue, index)
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

enum class FoodItem(val icon: Int, val tittle: String) {
    BREAKFAST(
        icon = R.drawable.breakfast,
        tittle = "Breakfast",
    ),
    LUNCH(icon = R.drawable.lunch, tittle = "Lunch"),
    SALAD(icon = R.drawable.salad, tittle = "Salad"),
    DINNER(icon = R.drawable.dinner, tittle = "Dinner");
}

@Composable
fun AnimatedPercentage(
    targetPercentage: Float,
    isPercentage: Boolean = false
): String {

    val animatedValue by animateFloatAsState(
        targetValue = if (isPercentage) targetPercentage * 100 else targetPercentage,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    return String.format("%.1f", animatedValue)

}