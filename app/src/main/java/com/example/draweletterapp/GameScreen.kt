package com.example.draweletterapp

import androidx.compose.animation.core.*
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun GameScreen(
    navController: NavHostController
) {
    // 主题颜色
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    // 游戏状态
    var gameStarted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var timeRemaining by remember { mutableStateOf(10) }
    var targetVisible by remember { mutableStateOf(false) }
    val targetSize = 64.dp

    // 动画
    val infiniteTransition = rememberInfiniteTransition()
    val targetRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // 游戏逻辑
    LaunchedEffect(gameStarted) {
        if (gameStarted) {
            timeRemaining = 10
            score = 0
            targetVisible = true
            while (timeRemaining > 0 && gameStarted) {
                delay(1000)
                timeRemaining -= 1
                if (timeRemaining == 0) {
                    gameStarted = false
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "小游戏", style = MaterialTheme.typography.headlineMedium) },
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
                    containerColor = primaryColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ConstraintLayout {
                val (gameArea, controls, scoreBoard, timeLeft, startButton) = createRefs()

                // 游戏区域
                Box(
                    modifier = Modifier
                        .constrainAs(gameArea) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            bottom.linkTo(controls.top, margin = 16.dp)
                        }
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (targetVisible) {
                        Box(
                            modifier = Modifier
                                .size(targetSize)
                                .background(
                                    color = randomColor(),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .graphicsLayer(rotationZ = targetRotation)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    if (gameStarted) {
                                        score += 1
                                        targetVisible = false
                                        delay(500)
                                        targetVisible = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_target),
                                contentDescription = "点击目标",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // 控制按钮区域
                Row(
                    modifier = Modifier
                        .constrainAs(controls) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // 开始/暂停按钮
                    Button(
                        onClick = { gameStarted = !gameStarted },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (gameStarted) Color.Red else primaryColor,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = if (gameStarted) "暂停" else "开始")
                    }

                    // 重置按钮
                    Button(
                        onClick = {
                            gameStarted = false
                            timeRemaining = 10
                            score = 0
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = "重置")
                    }
                }

                // 分数板
                Row(
                    modifier = Modifier
                        .constrainAs(scoreBoard) {
                            top.linkTo(gameArea.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Score,
                        contentDescription = "分数",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "分数：$score",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // 时间剩余
                Row(
                    modifier = Modifier
                        .constrainAs(timeLeft) {
                            top.linkTo(gameArea.bottom, margin = 8.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "时间",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "时间：$timeRemaining",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // 启动动画
                val startIcon: ImageVector
                val painter = when {
                    gameStarted -> {
                        startIcon = ImageVector.vectorResource(id = R.drawable.ic_pause)
                        rememberAnimatedVectorPainter(
                            animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.avd_pause),
                            atEnd = false
                        )
                    }
                    else -> {
                        startIcon = ImageVector.vectorResource(id = R.drawable.ic_play)
                        rememberAnimatedVectorPainter(
                            animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.avd_play),
                            atEnd = false
                        )
                    }
                }

                // 启动按钮
                Button(
                    onClick = { gameStarted = !gameStarted },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (gameStarted) Color.Red else primaryColor,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .constrainAs(startButton) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            end.linkTo(controls.end)
                        },
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = "启动/暂停",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

// 随机颜色生成
fun randomColor(): Color {
    return Color(
        random.nextInt(256),
        random.nextInt(256),
        random.nextInt(256),
        255
    )
}
