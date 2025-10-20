package com.example.foodapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.R

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box(
                modifier
                    .background(
                        color = Color(0xFFFE912D),
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
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                            text = "Login",
                            fontSize = 16.sp,
                            color = Color(0xFFFE912D),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFE912D) ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                            text = "Register",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "or",
                    fontSize = 16.sp,
                    color = Color(0xFFCECECC)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ){
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ){
                        Icon(
                            modifier = Modifier.padding(8.dp).size(16.dp),
                            painter = painterResource(R.drawable.google_icon),
                            contentDescription = null,
                            tint = Color(0xFF1AA1F0)
                        )
                    }
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ){
                        Icon(
                            modifier = Modifier.padding(8.dp).size(16.dp),
                            painter = painterResource(R.drawable.twitter_icon),
                            contentDescription = null,
                            tint = Color(0xFF1AA1F0)
                        )
                    }
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0F1)),
                        shape = CircleShape
                    ){
                        Icon(
                            modifier = Modifier.padding(8.dp).size(16.dp),
                            painter = painterResource(R.drawable.facebook_icon),
                            contentDescription = null,
                            tint = Color(0xFF1AA1F0)
                        )
                    }
                }
            }
        }
    }
}