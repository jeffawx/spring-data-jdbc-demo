import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.spring") version "1.4.30"
    kotlin("kapt") version "1.4.30"
}

group = "com.airwallex"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

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
    implementation("com.airwallex.common.jdbc:spring-data:1.0.0")
    testImplementation("com.airwallex.common.jdbc:spring-data-test:1.0.0")
    kapt("com.airwallex.common.jdbc:spring-data-processor:1.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
