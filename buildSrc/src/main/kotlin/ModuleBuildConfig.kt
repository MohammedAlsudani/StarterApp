


/**
 *
 * @author Mohammed Al-Sudani
 * @since 5/1/20 7:10 PM
 */
object ModuleGradleConfig {

    const val applicationId = "com.materialuiux.starterapp"
    const val compileSDKVersion = 33
    const val minSdkVersion = 23
    const val targetSDKVersion = 33

    const val versionCode = 1
    const val versionName = "1.0"

    const val viewBindingEnabled = true
    const val composeEnabled = true
    const val buildConfigEnabled = true
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    const val kotlinJvmTarget = "17"

    const val flavorDimensions = "StarterApp"

    const val DATABASE_NAME = "starterapp_db.db"
    const val DATABASE_VERSION : Int = 1
    const val DEV_VERSION_CODE : Int = 2
    const val DEV_VERSION_NAME : String = "1.0.0.2"
    const val QA_VERSION_CODE : Int = 1
    const val QA_VERSION_NAME : String = "1.0.0.0"
    const val UAT_VERSION_CODE : Int = 1
    const val UAT_VERSION_NAME : String = "1.0.0.0"
    const val PROD_VERSION_CODE : Int = 6
    const val PROD_VERSION_NAME : String = "1.0.0.6"
}

object ProjectModules {
    const val COMMON = ":common"
}