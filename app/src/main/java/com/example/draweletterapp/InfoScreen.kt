package com.example.draweletterapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "科普页面", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "返回主页"
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 标题
                Text(
                    text = "眼睛的奥秘",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                // 眼睛结构图
                Image(
                    painter = painterResource(id = R.drawable.eye_diagram),
                    contentDescription = "眼睛结构图",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Fit
                )

                // 科普内容
                Text(
                    text = "眼睛是人体最重要的感觉器官之一，它让我们能够看到五彩斑斓的世界。眼睛的结构非常复杂，主要由角膜、晶状体、视网膜等部分组成。角膜是眼睛的最外层，负责折射光线；晶状体可以调节焦距，使我们能够看清远近不同的物体；视网膜则将光线转化为神经信号，传递给大脑。",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Justify
                )

                // 互动元素：点击眼睛结构图显示详细信息
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, RoundedCornerShape(16.dp))
                        .clickable { /* TODO: 显示详细信息 */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "点击眼睛结构图查看详细信息",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // 护眼小贴士
                Text(
                    text = "护眼小贴士",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "1. 保持正确的用眼姿势，避免长时间近距离用眼。\n2. 经常进行户外活动，让眼睛得到充分的休息。\n3. 合理饮食，多吃富含维生素A和C的食物，如胡萝卜、橙子等。\n4. 定期检查视力，及时发现和纠正视力问题。",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Justify
                )

                // 互动元素：护眼小测验
                Text(
                    text = "护眼小测验",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "1. 以下哪种行为对眼睛有害？\n   a. 长时间玩手机\n   b. 经常眨眼\n   c. 吃胡萝卜\n\n2. 以下哪种食物对眼睛有益？\n   a. 巧克力\n   b. 西兰花\n   c. 汽水",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Justify
                )
                Button(
                    onClick = { /* TODO: 显示测验答案 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "查看答案",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }
    }
}
