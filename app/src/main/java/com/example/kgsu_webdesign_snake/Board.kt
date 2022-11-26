package com.example.kgsu_webdesign_snake

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierFactoryExtensionFunction")
fun detectDrag(onDirectionChange: (Pair<Int, Int>)->Unit): Modifier {

    var direction: Direction? = null

    return Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                change.consumeAllChanges()

                val (x, y) = dragAmount
                if(kotlin.math.abs(x) > kotlin.math.abs(y)){
                    when {
                        x > 0 -> {
                            direction = Direction.RIGHT
                        }
                        x < 0 -> {
                            direction = Direction.LEFT
                        }
                    }
                }else{
                    when {
                        y > 0 -> {
                            direction = Direction.DOWN
                        }
                        y < 0 -> {
                            direction = Direction.UP
                        }
                    }
                }

            },
            onDragEnd = {
                when (direction){
                    Direction.RIGHT -> {
                        onDirectionChange(Pair(1, 0))
                    }
                    Direction.LEFT -> {
                        onDirectionChange(Pair(-1, 0))
                    }
                    Direction.DOWN -> {
                        onDirectionChange(Pair(0, 1))
                    }
                    Direction.UP -> {
                        onDirectionChange(Pair(0, -1))
                    }
                    else -> {}
                }
            }
        )
    }
}

@Composable
fun Board(state: State, move:Pair<Int, Int>, boardWidth: Int, boardHeight: Int, tileSize: Int, onDirectionChange: (Pair<Int, Int>)->Unit) {
    BoxWithConstraints (modifier = detectDrag(onDirectionChange)) {

        //board border
        Box(
            modifier = Modifier
                .size(boardWidth.dp, boardHeight.dp)
                .border(2.dp, Color.Red)
                .background(colorResource(R.color.boardTheme))
        )

        //apple
        Box(
            modifier = Modifier
                .offset(
                    x = tileSize.dp * state.food.first,
                    y = tileSize.dp * state.food.second
                )
                .size(tileSize.dp)
        ) {
            Image(painter = painterResource(R.drawable.apple), contentDescription = null)
        }

        //current head rotate degree
        val degrees = when(move) {
            Pair(1,0) -> 90f
            Pair(0,-1) -> 0f
            Pair(-1, 0) -> 270f
            Pair(0, 1) -> 180f
            else -> 0f
        }

        //snake head
        Box(
            modifier = Modifier
                .offset(
                    x = tileSize.dp * state.snake[0].first,
                    y = tileSize.dp * state.snake[0].second
                )
                .size(tileSize.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.snakehead),
                contentDescription = null,
                modifier = Modifier.rotate(degrees)
            )
        }

        //snake body
        for (pos:Pair<Int, Int> in state.snake.subList(fromIndex = 1, toIndex = state.snake.size)) {
            Box(
                modifier = Modifier
                    .offset(
                        x = tileSize.dp * pos.first,
                        y = tileSize.dp * pos.second
                    )
                    .size(tileSize.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.snakebody),
                    contentDescription = null
                )
            }
        }
    }
}