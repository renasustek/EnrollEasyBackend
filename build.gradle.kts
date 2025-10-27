plugins {
    java
    jacoco
    `jvm-test-suite`
    `jacoco-report-aggregation`
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.0.1.6134"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "com.renasustek"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"
val springDependencies = "3.3.1"
val mysqlConnector = "8.0.33"
val hibernate = "8.0.1.Final"
val junit = "5.10.3"
val palantirJavaFormat = "2.47.0"

val assertJ = "3.26.0"

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springDependencies"))
    implementation("mysql:mysql-connector-java:$mysqlConnector")
    implementation("org.hibernate.validator:hibernate-validator:$hibernate")
    implementation("org.hibernate:hibernate-validator:$hibernate")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-mail")
}

testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                implementation("io.projectreactor:reactor-test")
                implementation("org.assertj:assertj-core:$assertJ")
                implementation("org.junit.jupiter:junit-jupiter:$junit")
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("com.h2database:h2")
            }
        }
    }
}

tasks.testCodeCoverageReport {
    dependsOn(tasks.test)
    executionData(fileTree(layout.buildDirectory).include("jacoco/*.exec"))
    reports {
        xml.required = true
        html.required = true
    }
    mustRunAfter(tasks.spotlessCheck)
}

tasks.check {
    dependsOn(tasks.spotlessCheck, tasks.testCodeCoverageReport)
}

sonar {
    properties {
        property("sonar.projectKey", "renasustek_EnrollEasyBackend")
        property("sonar.organization", "renasustek")
    }
}

spotless {
    java {
        googleJavaFormat()
        endWithNewline()
    }

    kotlinGradle {
        target("*.gradle.kts") // Target your .gradle.kts files
        ktlint() // Use the ktlint formatter
    }
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
