package com.example.kgsu_webdesign_snake

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Buttons(buttonSize: Int, modifier: Modifier, onDirectionChange: (Pair<Int, Int>)->Unit) {

    val buttonSizeDp = Modifier.size(buttonSize.dp)

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {  }, modifier =
        buttonSizeDp
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        }

        Row {
            Button(onClick = {  }, modifier = buttonSizeDp) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
            Spacer(modifier = buttonSizeDp)
            Button(onClick = {  }, modifier = buttonSizeDp) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
        Button(onClick = {  }, modifier = buttonSizeDp) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    }
}