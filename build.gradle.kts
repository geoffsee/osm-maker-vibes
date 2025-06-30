plugins {
    kotlin("multiplatform") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://mvn.topobyte.de")
    }
    maven {
        url = uri("https://mvn.slimjars.com")
    }
}
