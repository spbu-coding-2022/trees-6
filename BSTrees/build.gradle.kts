val jetbrainsExposedVersion:  String? by rootProject
val junit5Version: String? by rootProject
val gsonVersion: String? by rootProject
val kotlinxSerializationVersion: String? by rootProject
val neo4jDriverVersion: String? by project

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"

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
    implementation("org.xerial:sqlite-jdbc:3.34.0")

    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.6")
    implementation("org.slf4j", "slf4j-simple", "1.7.29")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
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
