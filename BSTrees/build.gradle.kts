plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")

    testImplementation("org.mockito:mockito-core:3.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
