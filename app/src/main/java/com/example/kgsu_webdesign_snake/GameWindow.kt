package com.example.kgsu_webdesign_snake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GameWindow(game: GameEngine, boardWidth: Int, boardHeight: Int, tileSize:Int, buttonSize: Int = 70) {

    val state = game.state.collectAsState(initial = null)

    val highScore = MainActivity.sharedPref!!.getInt("highscore", 0)

    val currentScore = state.value?.snakeLength?.minus(1)

    if (currentScore != null) {
        if(currentScore > highScore){
            val editor = MainActivity.sharedPref!!.edit()
            editor.putInt("highscore", currentScore);
            editor.apply()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Score(currentScore, highScore)
        state.value?.let { it ->
            //drawing board
            Board(it, game.move, boardWidth, boardHeight, tileSize) {if(!game.doesCollide(it)) game.move = it}
        }
    }
}

