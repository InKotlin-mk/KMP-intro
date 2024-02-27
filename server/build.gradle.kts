plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    application
}

group = libs.versions.app.version.appId.get()
version = libs.versions.app.version.versionName.get()
application {
    mainClass.set("mk.inkotlin.kmp.intro.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.serialization.json)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.app.build.kotlinJVMTarget.get().toInt()))
    }
}