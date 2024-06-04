// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    id ("org.jetbrains.kotlin.kapt") version ("1.6.10") apply false
    id ("androidx.navigation.safeargs.kotlin") version ("2.7.7") apply false
}

buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7") //add here.
    }
}