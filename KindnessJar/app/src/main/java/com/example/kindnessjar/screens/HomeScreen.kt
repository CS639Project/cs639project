package com.example.kindnessjar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kindnessjar.R

@Composable
fun HomeScreen(onPickNoteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.peach_background)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Welcome text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.welcome_to),
                    fontSize = 28.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.kindness_jar),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Jar Image
            Image(
                painter = painterResource(id = R.drawable.jar),
                contentDescription = "Jar Image",
                modifier = Modifier.size(150.dp)
            )

            // Button
            Button(
                onClick = { onPickNoteClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_blue)
                ),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(260.dp)
                    .height(65.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.pick_today_note),
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

