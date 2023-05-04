val composeVersion: String? by rootProject

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.compose") version "1.4.0"

    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))

    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$composeVersion")
    implementation("com.arkivanov.decompose:decompose:$composeVersion")

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}


application {
    // Define the main class for the application.
    mainClass.set("app/view/AppKt")
}
