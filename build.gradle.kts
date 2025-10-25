plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
    id ("org.sonarqube") version "7.0.1.6134"

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
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.assertj:assertj-core:$assertJ")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
//    testImplementation("org.springframework.security:spring-security-test:5.7.3")

}

sonar {
    properties {
        property("sonar.projectKey", "renasustek_EnrollEasyBackend")
        property("sonar.organization", "renasustek")
    }
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}


tasks.withType<Test> {
	useJUnitPlatform()
}
