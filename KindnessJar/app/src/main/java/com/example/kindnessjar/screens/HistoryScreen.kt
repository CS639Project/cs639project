package com.example.kindnessjar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kindnessjar.R

@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.history_background)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            // Title
            Text(
                text = stringResource(id = R.string.history_title),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Cards Section
            HistoryCard(
                title = stringResource(id = R.string.history_item_1_title),
                date = stringResource(id = R.string.history_item_1_date)
            )

            HistoryCard(
                title = stringResource(id = R.string.history_item_2_title),
                date = stringResource(id = R.string.history_item_2_date)
            )

            HistoryCard(
                title = stringResource(id = R.string.history_item_3_title),
                date = stringResource(id = R.string.history_item_3_date)
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun HistoryCard(title: String, date: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorResource(id = R.color.history_card_bg)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.history_card_text)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = date,
                fontSize = 14.sp,
                color = colorResource(id = R.color.history_card_text)
            )
        }
    }
}