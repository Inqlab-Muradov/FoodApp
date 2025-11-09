package com.example.foodapp.presentation.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navigateToHome: () -> Unit) {

    var screenState by remember { mutableStateOf(ScreenState.LOGIN) }

    val animatableLoginButtonContainerColor by animateColorAsState(
        targetValue = if (screenState == ScreenState.LOGIN) Color.White else Color(0xFFCBF482),
        animationSpec = tween(durationMillis = 600)
    )

    val animatableLoginButtonContentColor by animateColorAsState(
        targetValue = if (screenState == ScreenState.LOGIN) Color(0xFFCBF482) else Color.White,
        animationSpec = tween(durationMillis = 600)
    )
    val animatableRegisterButtonContainerColor by animateColorAsState(
        targetValue = if (screenState == ScreenState.REGISTER) Color.White else Color(0xFFCBF482),
        animationSpec = tween(durationMillis = 600)
    )
    val animatableRegisterButtonContentColor by animateColorAsState(
        targetValue = if (screenState == ScreenState.REGISTER) Color(0xFFCBF482) else Color.White,
        animationSpec = tween(durationMillis = 600)
    )


    Scaffold { innerPadding ->
        Column(
            modifier
                .imePadding()
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box(
                modifier
                    .background(
                        color = Color(0xFFCBF482),
                        shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp)
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(160.dp)
                            .padding(start = 48.dp),
                        painter = painterResource(R.drawable.pizza_small),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Text(
                        modifier = Modifier.padding(start = 24.dp),
                        text = "Calorie Tracker",
                        fontSize = 44.sp,
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        lineHeight = 52.sp

                    )
                }
                Row(
                    modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 16.dp)
                ) {
                    Card(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = null
                        ) {
                            screenState = ScreenState.LOGIN
                        },
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = animatableLoginButtonContainerColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                            text = "Login",
                            fontSize = 16.sp,
                            color = animatableLoginButtonContentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Card(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = null
                        ) {
                            screenState = ScreenState.REGISTER
                        },
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = animatableRegisterButtonContainerColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                            text = "Register",
                            fontSize = 16.sp,
                            color = animatableRegisterButtonContentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "or",
                    fontSize = 16.sp,
                    color = Color(0xFFCECECC)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(16.dp),
                            painter = painterResource(R.drawable.google_icon),
                            contentDescription = null,
                            tint = Color(0xFFCBF482)
                        )
                    }
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(16.dp),
                            painter = painterResource(R.drawable.twitter_icon),
                            contentDescription = null,
                            tint = Color(0xFFCBF482)
                        )
                    }
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(16.dp),
                            painter = painterResource(R.drawable.facebook_icon),
                            contentDescription = null,
                            tint = Color(0xFFCBF482)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Crossfade(
                    targetState = screenState
                ) { target ->
                    if (target == ScreenState.LOGIN) LoginContent() else RegisterContent()
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(vertical = 24.dp)
                        .fillMaxWidth(),
                    onClick = navigateToHome,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCBF482)
                    ),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "Continue",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun LoginContent(modifier: Modifier = Modifier) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = userName,
            onValueChanged = { newText ->
                userName = newText
            },
            placeHolder = "Username"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = password,
            onValueChanged = { newText ->
                password = newText
            },
            placeHolder = "Password",
            isPassportField = true
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                ) {
                    append("Don't have an account?")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCBF482)
                    )
                ) {
                    append(" Sign Up")
                }
            }
        )
    }
}

@Composable
fun RegisterContent(modifier: Modifier = Modifier) {
    var userNameRegister by remember { mutableStateOf("") }
    var passwordRegister by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = userNameRegister,
            onValueChanged = { newText ->
                userNameRegister = newText
            },
            placeHolder = "Enter username"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = passwordRegister,
            onValueChanged = { newText ->
                passwordRegister = newText
            },
            placeHolder = "Enter password",
            isPassportField = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            text = confirmPassword,
            onValueChanged = { newValue ->
                confirmPassword = newValue
            },
            placeHolder = "Confirm Password",
            isPassportField = true
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                ) {
                    append("Already have an account?")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCBF482)
                    )
                ) {
                    append(" Sign In")
                }
            }
        )
    }
}


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChanged: (String) -> Unit,
    placeHolder: String,
    isPassportField: Boolean = false,
    fontSize: TextUnit = 14.sp,
    enabled: Boolean = true,
    focusRequester: FocusRequester = FocusRequester()
) {
    var onFocus by remember { mutableStateOf(false) }
    val animatableBorderColor by animateColorAsState(
        targetValue = if (onFocus) Color(0xFFCBF482) else Color(0xFFBDBDBD),
        animationSpec = tween(durationMillis = 600)
    )
    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                onFocus = focusState.isFocused
            }
            .wrapContentHeight()
            .border(1.dp, color = animatableBorderColor, shape = RoundedCornerShape(12.dp)),
        value = text,
        onValueChange = onValueChanged,
        cursorBrush = SolidColor(Color(0xFFCBF482)),
        textStyle = TextStyle(color = Color.Black, fontSize = fontSize),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = placeHolder,
                        style = TextStyle(color = Color.Gray, fontSize = fontSize)
                    )
                }
                innerTextField()
            }
        },
        enabled = enabled,
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (isPassportField) PasswordVisualTransformation() else VisualTransformation.None
    )

}

enum class ScreenState {
    LOGIN, REGISTER
}