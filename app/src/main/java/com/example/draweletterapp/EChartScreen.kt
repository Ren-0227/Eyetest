package com.example.draweletterapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.random.Random
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.draweletterapp.models.LanguageModel
import com.example.draweletterapp.models.VisualModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EChartScreen(navController: NavHostController) {
    var rotation by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(200.dp) }
    var distance by remember { mutableStateOf(3.0f) }
    var isLanguageEnabled by remember { mutableStateOf(false) } // 语言功能开关
    var isVisualEnabled by remember { mutableStateOf(false) } // 视觉功能开关

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "E字视力表") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回主页"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 显示文字说明
                Text(text = "用户距离：${"%.1f".format(distance)} 米", style = MaterialTheme.typography.bodyLarge)
                Text(text = "E字大小：${size.value.toInt()} 像素", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                // E 字展示
                BasicText(
                    text = "E",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = (size.value / 2).sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer(rotationZ = rotation)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // 滑动条控制用户距离
                Text(text = "调整用户距离", style = MaterialTheme.typography.bodyMedium)
                Slider(
                    value = distance,
                    onValueChange = {
                        distance = it
                        size = calculateSize(distance)
                    },
                    valueRange = 0.5f..5f,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // 按钮：切换方向
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
                    Text(text = "切换方向并调整大小")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 添加语言开关按钮
                Button(onClick = {
                    isLanguageEnabled = !isLanguageEnabled
                    if (isLanguageEnabled) {
                        // 初始化并调用语言功能模型
                        val languageModel = LanguageModel()
                        println(languageModel.generateLanguagePrompt(0)) // 示例调用
                    }
                }) {
                    Text(
                        text = if (isLanguageEnabled) "关闭语言识别" else "开启语言识别"
                    )
                }

                // 添加视觉开关按钮
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    isVisualEnabled = !isVisualEnabled
                    if (isVisualEnabled) {
                        // 初始化并调用视觉功能模型
                        val visualModel = VisualModel()
                        println(visualModel.analyzeSignalResult()) // 示例调用
                    }
                }) {
                    Text(
                        text = if (isVisualEnabled) "关闭视觉模式" else "开启视觉模式"
                    )
                }
            }
        }
    )
}

// 字体大小计算方法
fun calculateSize(distance: Float): androidx.compose.ui.unit.Dp {
    val baseSize = 300.dp
    val minSize = 50.dp
    val maxSize = 300.dp

    val calculatedSize = baseSize.value / distance
    return calculatedSize.dp.coerceIn(minSize, maxSize)
}
