package com.example.kindnessjar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kindnessjar.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChallengeScreen(
    todayChallenge: StateFlow<String>,
    onMarkCompleted: () -> Unit
) {
    val challengeText = todayChallenge.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.challenge_background)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.padding(top = 40.dp)
        ) {

            // Screen Title
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.challenge_title_welcome),
                    fontSize = 26.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.challenge_title_appname),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Challenge Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = colorResource(id = R.color.challenge_card_bg),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(280.dp)
                    .height(180.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = challengeText,
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                }
            }

            // Mark as Completed Button
            Button(
                onClick = { onMarkCompleted() },
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.challenge_button_bg)
                ),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(280.dp)
                    .height(70.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.mark_as_completed),
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}