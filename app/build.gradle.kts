
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.kotlin)
    id("kotlin-kapt")
}

android {

    namespace = ModuleGradleConfig.applicationId
    compileSdk = ModuleGradleConfig.compileSDKVersion

    defaultConfig {
        applicationId = ModuleGradleConfig.applicationId
        minSdk = ModuleGradleConfig.minSdkVersion
        targetSdk = ModuleGradleConfig.targetSDKVersion
        versionCode = 1
        versionName = "1.1"
        testInstrumentationRunner = ModuleGradleConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = ModuleGradleConfig.viewBindingEnabled
        dataBinding = ModuleGradleConfig.viewBindingEnabled
        buildConfig = ModuleGradleConfig.buildConfigEnabled
        compose = ModuleGradleConfig.composeEnabled
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

        }
        debug {
            enableAndroidTestCoverage = false
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = ModuleGradleConfig.kotlinJvmTarget
        // This compiler argument is needed to use the @Opt-in annotation
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }


    composeOptions {
        kotlinCompilerExtensionVersion = DependencyVersion.composeCompiler
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Customize the file name of the packaged apk
    android.applicationVariants.forEach { variant ->
        variant.assembleProvider.get().doLast {
            variant.outputs.forEach { output ->
                val filename = "${variant.applicationId}_${variant.versionCode}(${variant.versionName})_${variant.buildType.name}.apk"
                val dir = output.outputFile.parent
                val outputFile = File(dir, filename)
                if (output.outputFile.name.endsWith(".apk")) {
                    output.outputFile.renameTo(outputFile)
                }
            }
        }
    }

    signingConfigs {
        create("starterAppDevConfig") {
            keyAlias = "starterApp"
            keyPassword = "123456"
            storeFile = rootProject.file("../app/jksFils/starterApp_dev.jks")
            storePassword = "123456"
        }
        create("starterAppProdConfig") {
            keyAlias = "starterApp"
            keyPassword = "starterApp12345"
            storeFile = rootProject.file("../app/jksFils/starterApp_release.jks")
            storePassword = "starterApp12345"
        }
    }

    flavorDimensions.add(ModuleGradleConfig.flavorDimensions)
    productFlavors {
        create("dev") {
            dimension = ModuleGradleConfig.flavorDimensions
            applicationId = "com.materialuiux.starterapp.dev"
            versionCode = ModuleGradleConfig.DEV_VERSION_CODE
            versionName = ModuleGradleConfig.DEV_VERSION_NAME
            signingConfig = signingConfigs.getByName("starterAppDevConfig")
        }
        create("prod") {
            dimension = ModuleGradleConfig.flavorDimensions
            applicationId = "com.materialuiux.starterapp"
            versionCode = ModuleGradleConfig.PROD_VERSION_CODE
            versionName = ModuleGradleConfig.PROD_VERSION_NAME
            signingConfig = signingConfigs.getByName("starterAppProdConfig")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}