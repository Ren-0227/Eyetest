package com.example.draweletterapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val primaryColor = colorResource(id = androidx.compose.material3.R.color.primary)
    val secondaryColor = colorResource(id = androidx.compose.material3.R.color.secondary)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "主页", fontSize = 20.sp, color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "眼科测试软件",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = primaryColor,
                    fontSize = 30.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "关注眼健康，享受清晰视界",
                style = MaterialTheme.typography.labelMedium.copy(color = secondaryColor)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // 功能按钮区域
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val buttonCount = 4
                val buttonHeight = (screenHeight / (buttonCount + 2)).value.dp

                val eChartButtonState = remember { mutableStateOf(false) }
                val gameButtonState = remember { mutableStateOf(false) }
                val infoButtonState = remember { mutableStateOf(false) }
                val settingsButtonState = remember { mutableStateOf(false) }

                val animatedAlpha by animateFloatAsState(
                    targetValue = if (eChartButtonState.value) 1f else 0.5f,
                    label = "E Chart Alpha"
                )

                val animatedColor by animateColorAsState(
                    targetValue = if (eChartButtonState.value) primaryColor else secondaryColor,
                    label = "E Chart Color"
                )

                ElevatedButton(
                    onClick = {
                        eChartButtonState.value = !eChartButtonState.value
                        navController.navigate("e_chart")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .alpha(animatedAlpha)
                        .background(color = animatedColor, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "E字视力表图标",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "E字视力表",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 类似地为其他按钮添加淡入淡出效果和渐变
                // ...
                // 小游戏按钮
                val gameButtonAlpha by animateFloatAsState(
                    targetValue = if (gameButtonState.value) 1f else 0.5f,
                    label = "Game Alpha"
                )

                val gameButtonColor by animateColorAsState(
                    targetValue = if (gameButtonState.value) primaryColor else secondaryColor,
                    label = "Game Color"
                )

                ElevatedButton(
                    onClick = {
                        gameButtonState.value = !gameButtonState.value
                        navController.navigate("game")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .alpha(gameButtonAlpha)
                        .background(color = gameButtonColor, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.SportsEsports,
                        contentDescription = "小游戏图标",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "小游戏",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 科普页面按钮
                val infoButtonAlpha by animateFloatAsState(
                    targetValue = if (infoButtonState.value) 1f else 0.5f,
                    label = "Info Alpha"
                )

                val infoButtonColor by animateColorAsState(
                    targetValue = if (infoButtonState.value) primaryColor else secondaryColor,
                    label = "Info Color"
                )

                ElevatedButton(
                    onClick = {
                        infoButtonState.value = !infoButtonState.value
                        navController.navigate("info")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .alpha(infoButtonAlpha)
                        .background(color = infoButtonColor, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "科普页面图标",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "科普页面",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 设置按钮
                val settingsButtonAlpha by animateFloatAsState(
                    targetValue = if (settingsButtonState.value) 1f else 0.5f,
                    label = "Settings Alpha"
                )

                val settingsButtonColor by animateColorAsState(
                    targetValue = if (settingsButtonState.value) primaryColor else secondaryColor,
                    label = "Settings Color"
                )

                ElevatedButton(
                    onClick = {
                        settingsButtonState.value = !settingsButtonState.value
                        navController.navigate("settings")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .alpha(settingsButtonAlpha)
                        .background(color = settingsButtonColor, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "设置图标",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "设置",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 信息提示区域（可自定义内容）
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                    .clickable(onClick = {
                        // 点击事件
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "信息提示图标",
                    tint = secondaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "点击空白处缩小",
                    style = MaterialTheme.typography.labelMedium.copy(color = secondaryColor)
                )
            }
        }
    }
}
