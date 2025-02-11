package com.example.draweletterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SightTestScreen()
        }
    }
}

@Composable
fun SightChartApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SightTestScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SightTestScreen() {
    // 状态声明
    var rotation by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(200.dp) }
    var distance by remember { mutableStateOf(3.0f) } // 距离模拟数据

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "E字视力表") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 文本信息
                Text(text = "用户距离：${distance}米", style = MaterialTheme.typography.bodyLarge)
                Text(text = "E字大小：${size.value.toInt()} 像素", style = MaterialTheme.typography.bodySmall)

                // 动态方向和大小的“E字”
                BasicText(
                    text = "E",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = (size.value / 2).sp),
                    modifier = Modifier.graphicsLayer(rotationZ = rotation)
                )

                // 更改方向
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    val randomDirection = Random.nextInt(4)
                    rotation = when (randomDirection) {
                        0 -> 0f
                        1 -> 90f
                        2 -> 180f
                        3 -> 270f
                        else -> 0f
                    }
                }) {
                    Text(text = "随机改变方向")
                }

                // 动态调整大小
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // 调整字体大小模拟
                    val minDistance = 2.0f
                    val maxSize = 300.dp
                    val minSize = 50.dp
                    size = ((maxSize.value - (distance - minDistance) * 50).toFloat().coerceIn(minSize.value, maxSize.value)).dp
                }) {
                    Text(text = "调整大小")
                }
            }
        }
    )
}