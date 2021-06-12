import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    kotlin("kapt") version "1.5.10"
}

group = "com.airwallex"
version = "0.0.1-SNAPSHOT"

repositories {
    maven("https://artistry.airwallex.com/repository/lib-release/libs-release-local") {
        content {
            includeGroupByRegex("com\\.airwallex.*")
        }
    }
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.flywaydb:flyway-core")
    implementation("com.airwallex.common.jdbc:spring-data:1.1.4")
    testImplementation("com.airwallex.common.jdbc:spring-data-test:1.1.4")
    kapt("com.airwallex.common.jdbc:spring-data-processor:1.1.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
