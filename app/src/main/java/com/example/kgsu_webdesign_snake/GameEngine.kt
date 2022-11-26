package com.example.kgsu_webdesign_snake

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class GameEngine(
    private val scope: CoroutineScope,
    private val difficulty: Int,
    private val widthCount: Int,
    private val heightCount: Int) {

    private val mutex = Mutex()

    fun doesCollide(move:Pair<Int, Int>): Boolean{
        val snake = mutableState.value.snake

        return if(snake.size > 1) {
            getNewPosition(snake[0], move) ==snake[1]
        } else {
            false
        }
    }

    private fun getNewPosition(
        snake: Pair<Int, Int>,
        move: Pair<Int, Int>
    ) = Pair(
        (snake.first + move.first + Board_Size.first) % Board_Size.first,
        (snake.second + move.second + Board_Size.second) % Board_Size.second
    )

    private val mutableState =
        MutableStateFlow(State(
            food = Pair(Random.nextInt(Board_Size.first), Random.nextInt(Board_Size.second)),
            snake = listOf(Pair(Random.nextInt(Board_Size.first), Random.nextInt(Board_Size.second))),
            snakeLength = 1
        )
        )
    val state: Flow<State> = mutableState

    var move = Pair(0, 0)

        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    init {
        scope.launch {
            Board_Size = Pair(widthCount, heightCount)
            var snakeLength = 1
            val boardSize = widthCount * heightCount

            while (true) {
                val defaultDelay = 250L + difficulty
                var currentDelay:Long = defaultDelay - snakeLength * difficulty
                val lowerBoundDelay:Long = 100L

                currentDelay = if(currentDelay<lowerBoundDelay) lowerBoundDelay else currentDelay

                delay(currentDelay)
                mutableState.update {
                    Log.d("snake.size", it.snake.size.toString()+ "" + boardSize )
                    val newPosition = it.snake.first().let { pos ->
                        mutex.withLock {
                            getNewPosition(pos, move)
                        }
                    }

                    snakeLength = it.snake.size

                    if(newPosition == it.food)
                    {
                        snakeLength++
                    }

                    var newFoodPos = Pair(
                        Random.nextInt(Board_Size.first),
                        Random.nextInt(Board_Size.second)
                    )

                    while(newFoodPos in (it.snake + listOf(getNewPosition(it.snake[0], move)))) newFoodPos = Pair(
                        Random.nextInt(Board_Size.first),
                        Random.nextInt(Board_Size.second)
                    )

                    if(it.snake.contains(newPosition) || it.snake.size >= boardSize-2) {

                        snakeLength = 1
                    }

                    it.copy(
                        food = if (newPosition == it.food) {
                            newFoodPos
                        }
                        else it.food,
                        snake = (listOf(newPosition) + it.snake.take(snakeLength - 1)),
                        snakeLength = snakeLength
                    )
                }
            }
        }
    }

    companion object {
        var Board_Size = Pair(1, 1)
    }
}