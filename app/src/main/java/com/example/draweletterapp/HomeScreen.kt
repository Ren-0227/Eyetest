package com.example.draweletterapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "主页") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 第一功能："E字视力表"
            Button(
                onClick = { navController.navigate("e_chart") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "E字视力表")
            }

            // 第二功能："小游戏"
            Button(
                onClick = { navController.navigate("game") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "小游戏")
            }

            // 第三功能："科普页面"
            Button(
                onClick = { navController.navigate("info") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "科普页面")
            }

            // 第四功能："设置页面"
            Button(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "设置")
            }
        }
    }
}
