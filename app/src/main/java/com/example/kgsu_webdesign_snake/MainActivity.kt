package com.example.kgsu_webdesign_snake

import com.example.kgsu_webdesign_snake.Direction
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.kgsu_webdesign_snake.MainActivity.Companion.sharedPref
import com.example.kgsu_webdesign_snake.ui.theme.KGSU_WebDesign_SnakeTheme
import com.example.kgsu_webdesign_snake.ui.theme._buttonSize
import com.example.kgsu_webdesign_snake.ui.theme._difficulty
import com.example.kgsu_webdesign_snake.ui.theme._widthCount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    companion object {
        var sharedPref: SharedPreferences? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences("mypref", 0);

        setContent {
            KGSU_WebDesign_SnakeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //settings
                    val screenHeight = LocalConfiguration.current.screenHeightDp
                    val screenWidth = LocalConfiguration.current.screenWidthDp

                    val buttonSize = _buttonSize
                    val widthCount = _widthCount
                    val difficulty = _difficulty

                    val tileSize = screenWidth / widthCount
                    val maxBoardHeight = screenHeight - 50// - buttonSize * 3 - 80
                    val heightCount = maxBoardHeight / tileSize
                    val boardHeight = heightCount * tileSize
                    val boardWidth = widthCount * tileSize

                    val game = GameEngine(lifecycleScope, difficulty, widthCount, heightCount)

                    GameWindow(game, boardWidth, boardHeight, tileSize, buttonSize)
                }
            }
        }
    }
}

data class State(val food:Pair<Int, Int>, val snake:List<Pair<Int, Int>>, val snakeLength: Int)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KGSU_WebDesign_SnakeTheme {
    }
}