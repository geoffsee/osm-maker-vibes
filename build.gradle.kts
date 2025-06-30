plugins {
    kotlin("multiplatform") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            }
        }

        jvmMain {
            dependencies {
                // OSM2World dependencies temporarily removed for testing
                // implementation("org.osm2world:osm2world-core:0.3.0")
                // implementation("org.osm2world:osm2world-gltf:0.3.0")
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        wasmJsMain {
            dependencies {
                // WASM-specific dependencies if needed
            }
        }
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
