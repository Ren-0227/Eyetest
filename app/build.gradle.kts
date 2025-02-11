plugins {
    alias(libs.plugins.android.application) // Android 应用插件
    alias(libs.plugins.kotlin.android)     // Kotlin 插件
    alias(libs.plugins.kotlin.compose)     // Jetpack Compose 插件
}

android {
    namespace = "com.example.draweletterapp" // 应用包名
    compileSdk = 35                          // 使用最新的 Android API 版本

    defaultConfig {
        applicationId = "com.example.draweletterapp" // 应用唯一标识
        minSdk = 24                                 // 最低支持 Android 7.0
        targetSdk = 35                              // 与 compileSdk 保持一致
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // 测试 Runner
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Release 不使用代码混淆，方便调试（如需优化可改为 true）
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Java 17 支持
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // Kotlin 配置为 Java17，最大兼容性和性能优化
    }

    buildFeatures {
        compose = true // 启用 Jetpack Compose 特性
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // 与 BOM 版本一致
    }

    packaging {
        // 避免重复的依赖资源冲突
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 核心 Android 库
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))       // Compose BOM
    implementation("androidx.compose.ui:ui")                  // Compose UI 核心
    implementation("androidx.compose.ui:ui-tooling-preview")  // Compose 预览工具
    implementation("androidx.compose.material3:material3")    // Material Design 3

    // Material Icons
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation组件
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // CameraX（如果需要，E字表相关扩展）
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    // 测试相关依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // 调试工具
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
