package com.example.kgsu_webdesign_snake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Score(currentScore: Int?, highScore: Int) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            "Score is $currentScore",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .weight(1f)
                .padding(18.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            "Highscore is $highScore",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .weight(1f)
                .padding(18.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}