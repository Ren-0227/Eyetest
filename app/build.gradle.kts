plugins {
    alias(libs.plugins.android.application) // Android 应用插件
    alias(libs.plugins.kotlin.android)     // Kotlin 插件
    alias(libs.plugins.kotlin.compose)     // Jetpack Compose 插件
}

android {
    namespace = "com.example.draweletterapp" // 应用包名
    compileSdk = 35                          // 升级至兼容版本

    defaultConfig {
        applicationId = "com.example.draweletterapp" // 应用唯一标识
        minSdk = 24                                 // 最低支持 Android 7.0
        targetSdk = 35                              // 升级至与 compileSdk 一致
        versionCode = 1
        versionName = "1.0"

        // 测试框架
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // 禁用 ProGuard 混淆（如需优化，可以启用）
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // 使用现代 Java 标准
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17" // 对应 Java 17，支持高性能 Compose 等特性
    }

    buildFeatures {
        compose = true // 启用 Jetpack Compose 支持
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // 必须与 Compose BOM 的版本保持一致
    }
}

dependencies {
    // Android 核心库
    implementation(libs.androidx.core.ktx)                 // 核心 Kotlin 扩展
    implementation(libs.androidx.lifecycle.runtime.ktx)   // 生命周期管理
    implementation(libs.androidx.activity.compose)        // Compose 兼容 Activity

    // Jetpack Compose BOM（版本由 BOM 统一管理）
    implementation(platform(libs.androidx.compose.bom))   // 基础 BOM
    implementation("androidx.compose.ui:ui")              // Compose UI
    implementation("androidx.compose.material3:material3") // Material Design 3
    implementation("androidx.compose.ui:ui-tooling-preview") // 预览工具

    // 导航组件
    implementation("androidx.navigation:navigation-compose:2.7.2") // 最新稳定版本

    // Jetpack Compose 调试支持
    debugImplementation("androidx.compose.ui:ui-tooling") // UI 调试工具
    debugImplementation("androidx.compose.ui:ui-test-manifest") // 测试版调试支持

    // CameraX（用于摄像头访问）
    implementation("androidx.camera:camera-core:1.3.0")         // Camera 核心功能
    implementation("androidx.camera:camera-lifecycle:1.3.0")    // Camera 生命周期管理
    implementation("androidx.camera:camera-view:1.3.0")         // Camera UI 组件

    // 测试依赖 (单元测试和 UI 测试)
    testImplementation(libs.junit)                             // JUnit 单元测试
    androidTestImplementation(libs.androidx.junit)             // AndroidX JUnit 封装
    androidTestImplementation(libs.androidx.ui.test.junit4)    // Compose 测试支持
    androidTestImplementation(platform(libs.androidx.compose.bom)) // 使用 Compose 测试 BOM
    androidTestImplementation(libs.androidx.espresso.core)     // Espresso UI 测试框架
}
