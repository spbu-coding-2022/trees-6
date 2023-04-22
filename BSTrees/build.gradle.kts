plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"

    kotlin("plugin.serialization") version "1.8.20"

    jacoco

    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.jetbrains.exposed:exposed-core:0.38.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.1")
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation("org.slf4j:slf4j-nop:1.7.25")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
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
