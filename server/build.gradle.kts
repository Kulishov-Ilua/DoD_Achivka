plugins {
    alias(libs.plugins.kotlinJvm)
    //alias(libs.plugins.ktor)
    id("io.ktor.plugin") version "3.0.0"
    kotlin("plugin.serialization") version libs.versions.kotlin
    application
}

group = "ru.kulishov.statistic"
version = "1.0.0"
application {
    mainClass.set("ru.kulishov.statistic.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    //testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    implementation("io.ktor:ktor-server-auth:${libs.versions.ktor}")
    implementation("io.ktor:ktor-server-cors:${libs.versions.ktor}")
    implementation("io.ktor:ktor-server-content-negotiation:${libs.versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${libs.versions.ktor}")
}