buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once #22797 is fixed
plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.kotlin) apply false
}
true // Needed to make the Suppress annotation work for the plugins block