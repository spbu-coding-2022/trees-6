plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
