plugins {
    java
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
}

group = "com.polarbookshop"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.1"
extra["testcontainersVersion"] = "1.21.0"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")  // needed by spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.testcontainers:postgresql")
    // since flywaydb v10, specific database drivers are no longer included in the
    // core library, so we need to add the database driver as a separate dependency
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// below will stop generating the xxx-plan.jar file
// * tasks.named("jar") is a generic way to refer to a task
// * tasks.jar can only be used for common-known tasks.
/*
tasks.jar {
	enabled = false
}
*/

tasks.bootRun {
    systemProperty("spring.profiles.active", "testdata")
}

tasks.bootBuildImage {
    imageName = project.name
    environment.set(mapOf("BP_JVM_VERSION" to "21.*"))

    // publish to registry, just demo, normally should be done in CI/CD
    // it needs to pass below properties to the build command
    // gw bootBuildImage --imageName ghcr.io/qlwpub/catalog-service --publishImage\
    // -PDOCKER_USERNAME=qlwpub -PDOCKER_PAT=ghp_mypatxxx -PDOCKER_URL=ghcr.io
    docker {
        publishRegistry {
            username = project.findProperty("DOCKER_USERNAME").toString()
            password = project.findProperty("DOCKER_PAT").toString()
            url = project.findProperty("DOCKER_URL").toString()
        }
    }
}