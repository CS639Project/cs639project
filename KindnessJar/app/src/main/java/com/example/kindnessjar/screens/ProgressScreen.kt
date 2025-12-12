package com.example.kindnessjar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kindnessjar.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProgressScreen(
    streak: StateFlow<Int>,
    weeklyCompleted: StateFlow<Int>
) {
    val streakValue = streak.collectAsState().value
    val weekly = weeklyCompleted.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.mint_background)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.padding(top = 40.dp)
        ) {

            // Title
            Text(
                text = stringResource(id = R.string.progress_title),
                fontSize = 40.sp,
                color = Color.Black
            )

            // Daily Streak Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = colorResource(id = R.color.streak_card_bg),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(250.dp)
                    .height(120.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.daily_streak_label),
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = streakValue.toString(),   // <-- dynamic
                        fontSize = 40.sp,
                        color = Color.Black
                    )
                }
            }

            // Weekly Progress Boxes (dynamic)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                repeat(7) { index ->
                    val isFilled = index < weekly

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (isFilled)
                                    colorResource(id = R.color.box_filled)
                                else
                                    colorResource(id = R.color.box_empty),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.box_border),
                                shape = RoundedCornerShape(6.dp)
                            )
                    )
                }
            }

            // Weekly completed label (dynamic)
            Text(
                text = "$weekly task completed this week",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

