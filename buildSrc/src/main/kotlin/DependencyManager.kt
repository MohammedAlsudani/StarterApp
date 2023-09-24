object DependencyVersion {
    const val coreKTX = "1.8.0"
    const val kotlin = "1.8.22"
    const val kotlinLifecycleRuntime = "2.5.1"
    const val appCompat = "1.4.2"
    const val material = "1.8.0"
    const val constraint = "2.1.4"
    const val compose = "1.2.0"
    const val composeCompiler = "1.4.8"
    const val material3 = "1.0.1"
    const val room = "2.4.2"
    const val hilt = "2.44"
    const val hiltNavigationCompose = "1.0.0"
    const val navigation = "2.5.1"
    const val lifecycleVersion = "2.4.1"
    const val archVersion = "2.1.0"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.3"
    const val glide = "4.13.2"
    const val constraintLayoutCompose = "1.0.1"
    const val coroutines = "1.6.2"
    const val activity = "1.4.0"
    const val coil = "2.1.0"
    const val trueTime = "3.5"
    const val firebaseCrashlytics = "18.3.5"
    const val firebaseAnalytics = "21.2.0"
    const val junit = "4.13.2"
    const val junitParams = "1.1.1"
    const val mockitoKotlin = "4.0.0"
    const val androidJunit = "1.1.3"
    const val espresso = "3.4.0"
    const val appCenterSdkVersion = "5.0.0"
}

object AppDependencies {
    // Generics
    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib:${DependencyVersion.kotlin}"
    const val kotlinLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersion.kotlinLifecycleRuntime}"
    const val coreKtx = "androidx.core:core-ktx:${DependencyVersion.coreKTX}"
    const val appCompat = "androidx.appcompat:appcompat:${DependencyVersion.appCompat}"
    const val materialComponents = "com.google.android.material:material:${DependencyVersion.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersion.constraint}"
    const val activityKtx = "androidx.activity:activity-ktx:${DependencyVersion.activity}"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.3.0"
    const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"

    // Hilt
    const val hilt = "com.google.dagger:hilt-android:${DependencyVersion.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${DependencyVersion.hilt}"

    // Navigation Component
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${DependencyVersion.navigation}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${DependencyVersion.navigation}"
    const val navigationTest = "androidx.navigation:navigation-testing:${DependencyVersion.navigation}"

    // ViewModel & LiveData
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersion.lifecycleVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersion.lifecycleVersion}"
    const val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:${DependencyVersion.lifecycleVersion}"
    const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${DependencyVersion.lifecycleVersion}"
    const val viewModelTestHelpers = "androidx.arch.core:core-testing:${DependencyVersion.archVersion}"

    // true time library
    const val trueTime = "com.github.instacart.truetime-android:library-extension-rx:${DependencyVersion.trueTime}"

    // Kotlin Coroutines
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersion.coroutines}"

    const val coil = "io.coil-kt:coil:${DependencyVersion.coil}"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.11"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${DependencyVersion.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${DependencyVersion.glide}"
    const val glideOkHttpIntegration = "com.github.bumptech.glide:okhttp3-integration:${DependencyVersion.glide}"

    // Room
    const val room = "androidx.room:room-runtime:${DependencyVersion.room}"
    const val roomCompiler = "androidx.room:room-compiler:${DependencyVersion.room}"
    const val roomCoroutinesSupport = "androidx.room:room-ktx:${DependencyVersion.room}"
    const val roomTestHelpers = "androidx.room:room-testing:${DependencyVersion.room}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${DependencyVersion.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${DependencyVersion.retrofit}"

    // OkHttp
    const val okHttpBOM = "com.squareup.okhttp3:okhttp-bom:${DependencyVersion.okhttp}"
    const val okHttp = "com.squareup.okhttp3:okhttp"
    const val okHttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor"

    // firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:31.2.3"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:${DependencyVersion.firebaseCrashlytics}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:${DependencyVersion.firebaseAnalytics}"
    const val firebaseAuth = "com.google.firebase:firebase-auth:21.0.3"
    const val firebaseAuthKtx = "com.google.firebase:firebase-auth-ktx:21.0.3"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging:23.0.3"
    const val firebaseFirestore = "com.google.firebase:firebase-firestore:24.1.1"
    const val gms_play_services = "com.google.android.gms:play-services-auth:20.4.1"

    //firebaseStorage
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx:20.1.0"

    //konfetti
    const val konfetti = "nl.dionsegijn:konfetti-xml:2.0.3"

    // Navigation
    const val navigationFragmentktx = "androidx.navigation:navigation-fragment-ktx:2.5.3"
    const val navigationUiktx = "androidx.navigation:navigation-ui-ktx:2.5.3"
    const val navigationDynamic = "androidx.navigation:navigation-dynamic-features-fragment:2.5.3"
    const val navigationTesting = "androidx.navigation:navigation-testing:2.5.3"
    const val navigation_runtime = "androidx.navigation:navigation-runtime-ktx:2.5.3"

    // facebook
    const val facebook_login = "com.facebook.android:facebook-login:latest.release"

    // splash
    const val splash = "androidx.core:core-splashscreen:1.0.1"

    // circular image view lib
    const val circularimageview = "com.mikhaellopez:circularimageview:4.3.1"

    // gson
    const val gson = "com.google.code.gson:gson:2.10.1"

    // appcenter
    const val appcenterAnalytics = "com.microsoft.appcenter:appcenter-analytics:${DependencyVersion.appCenterSdkVersion}"
    const val appcenterCrashes = "com.microsoft.appcenter:appcenter-crashes:${DependencyVersion.appCenterSdkVersion}"
    const val appCenterSdkVersion = "com.microsoft.appcenter:appcenter-distribute:${DependencyVersion.appCenterSdkVersion}"

    //admob
    const val admobAds = "com.google.android.gms:play-services-ads:22.2.0"

    // Test Dependencies
    const val junit = "junit:junit:${DependencyVersion.junit}"
    const val junitParams = "pl.pragmatists:JUnitParams:${DependencyVersion.junitParams}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersion.coroutines}"
    const val okHttpMockWebserver = "com.squareup.okhttp3:mockwebserver:${DependencyVersion.okhttp}"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${DependencyVersion.mockitoKotlin}"
    const val androidJunitExtension = "androidx.test.ext:junit:${DependencyVersion.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${DependencyVersion.espresso}"
}
