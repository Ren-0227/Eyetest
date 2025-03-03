package com.example.draweletterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import kotlin.random.Random
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EChartScreen(navController: NavHostController) {
    var rotation by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(200.dp) }
    var distance by remember { mutableStateOf(3.0f) }
    var isLanguageEnabled by remember { mutableStateOf(false) }
    var isVisualEnabled by remember { mutableStateOf(false) }
    var showHelp by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "E字视力表", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .background(
                                Color.Transparent,
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回主页",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (textInfo, eLetter, sliderContainer, buttonContainer, helpButton) = createRefs()

                // 文字说明
                Column(
                    modifier = Modifier
                        .constrainAs(textInfo) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "用户距离：${"%.1f".format(distance)} 米",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = "E字大小：${size.value.toInt()} 像素",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // E 字展示
                Box(
                    modifier = Modifier
                        .constrainAs(eLetter) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .background(
                            Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .aspectRatio(1f)
                ) {
                    BasicText(
                        text = "E",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = (size.value / 2).sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .graphicsLayer(rotationZ = rotation)
                            .align(Alignment.Center)
                    )
                }

                // 滑动条与按钮容器
                Column(
                    modifier = Modifier
                        .constrainAs(sliderContainer) {
                            top.linkTo(eLetter.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            bottom.linkTo(buttonContainer.top, margin = 16.dp)
                        }
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "调整用户距离",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Slider(
                        value = distance,
                        onValueChange = {
                            distance = it
                            size = calculateSize(distance)
                        },
                        valueRange = 0.5f..5f,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                    )
                }

                // 按钮容器
                Column(
                    modifier = Modifier
                        .constrainAs(buttonContainer) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 切换方向按钮
                    Button(
                        onClick = {
                            val randomDirection = Random.nextInt(4)
                            rotation = when (randomDirection) {
                                0 -> 0f
                                1 -> 90f
                                2 -> 180f
                                3 -> 270f
                                else -> 0f
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "切换方向并调整大小",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }

                    // 语言功能开关
                    ToggleButton(
                        checked = isLanguageEnabled,
                        onCheckedChange = {
                            isLanguageEnabled = it
                            if (it) {
                                // LanguageModel().generateLanguagePrompt(0)
                            } else {
                                // 关闭功能
                            }
                        },
                        colors = ButtonDefaults.toggleButtonColors(
                            checkedContainerColor = MaterialTheme.colorScheme.primary,
                            checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                            uncheckedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            uncheckedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = if (isLanguageEnabled) "关闭语言识别" else "开启语言识别",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // 视觉功能开关
                    ToggleButton(
                        checked = isVisualEnabled,
                        onCheckedChange = {
                            isVisualEnabled = it
                            if (it) {
                                // VisualModel().analyzeSignalResult()
                            } else {
                                // 关闭功能
                            }
                        },
                        colors = ButtonDefaults.toggleButtonColors(
                            checkedContainerColor = MaterialTheme.colorScheme.primary,
                            checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                            uncheckedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            uncheckedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = if (isVisualEnabled) "关闭视觉模式" else "开启视觉模式",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // 帮助按钮
                Button(
                    onClick = { showHelp = !showHelp },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .constrainAs(helpButton) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = "帮助",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            // 帮助信息
            if (showHelp) {
                ModalBottomSheet(
                    onDismissRequest = { showHelp = false },
                    content = {
                        Text(
                            text = "使用指南：\n\n1. 调整用户距离：通过滑动条可以调整用户与设备的距离，距离变化会影响E字的大小。\n\n2. 切换方向：点击切换方向按钮，E字会随机变化方向。\n\n3. 语言功能：开启语言识别功能后，可以进行语音交互。\n\n4. 视觉功能：开启视觉模式后，可以体验更多的视觉效果。\n\n5. 帮助按钮：点击帮助按钮，可以查看使用指南。",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp
                            ),
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    },
                    sheetState = rememberModalBottomSheetState(),
                    containerColor = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

// 字体大小计算方法
fun calculateSize(distance: Float): androidx.compose.ui.unit.Dp {
    val baseSize = 300.dp
    val minSize = 50.dp
    val maxSize = 300.dp

    val calculatedSize = baseSize.value / distance
    return calculatedSize.dp.coerceIn(minSize, maxSize)
}

// ToggleButton自定义
@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = RoundedCornerShape(16.dp),
) {
    Surface(
        modifier = modifier,
        color = if (checked) colors.containerColor(true).value else colors.containerColor(false).value,
        shape = shape,
        onClick = { onCheckedChange(!checked) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            content()
        }
    }
}
