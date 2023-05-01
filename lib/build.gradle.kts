val jetbrainsExposedVersion:  String? by rootProject
val junit5Version: String? by rootProject
val gsonVersion: String? by rootProject
val kotlinxSerializationVersion: String? by rootProject
val neo4jDriverVersion: String? by project
val sqliteJdbcVersion: String? by project
val kotlinLoggingVersion: String? by project
val slf4jVersion: String? by project

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("org.jetbrains.compose") version "1.4.0"

    jacoco
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    implementation("com.google.code.gson:gson:$gsonVersion")

    implementation("org.neo4j.driver:neo4j-java-driver:$neo4jDriverVersion")

    implementation("org.jetbrains.exposed:exposed-core:$jetbrainsExposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$jetbrainsExposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$jetbrainsExposedVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteJdbcVersion")

    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")

    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-alpha-02")
    implementation("com.arkivanov.decompose:decompose:2.0.0-alpha-02")

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        csv.required.set(true)
        csv.outputLocation.set(layout.buildDirectory.file("jacoco/jacocoCsv"))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("bstrees/view/AppKt")
}
