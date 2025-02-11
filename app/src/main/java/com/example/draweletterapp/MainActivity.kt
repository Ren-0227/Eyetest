package com.example.draweletterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntryPoint()
        }
    }
}

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController() // 页面导航控制器

    // 定义页面导航路径
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }         // 主页面
        composable("e_chart") { EChartScreen(navController) }   // E字视力表页面
        composable("game") { GameScreen(navController) }        // 小游戏页面
        composable("info") { InfoScreen(navController) }        // 科普页面
        composable("settings") { SettingsScreen(navController) } // 设置页面
    }
}
