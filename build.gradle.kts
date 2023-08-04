plugins {
//    kotlin("multiplatform") version "1.9.0"
    kotlin("multiplatform") version "1.9.20-dev-8763"
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/") }
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    jvm {
        compilations.all {
            kotlinOptions.apiVersion = "1.9"
            kotlinOptions.languageVersion = "2.0"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }.apply {
        compilations.all {
            kotlinOptions.apiVersion = "1.9"
            kotlinOptions.languageVersion = "2.0"
        }
    }

    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
