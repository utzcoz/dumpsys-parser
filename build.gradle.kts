import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.utzcoz.parser.dumpsys"
version = "0.2.2"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
    }

    kotlinGradle {
        target("*.kts", "**/*.kts")
        ktlint()
    }

    format("misc") {
        // define the files to apply `misc` to
        target("*.gradle", "*.md", ".gitignore")

        // define the steps to apply to those files
        trimTrailingWhitespace()
        indentWithTabs() // or spaces. Takes an integer argument if you don't like 4
        endWithNewline()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to "com.utzcoz.parser.dumpsys.DumpsysParser",
        )
    }
}
