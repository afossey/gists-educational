import com.epages.restdocs.apispec.gradle.OpenApi3Extension
import org.springframework.cloud.contract.verifier.config.TestMode
import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

buildscript {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("org.springframework.cloud:spring-cloud-contract-gradle-plugin:2.0.2.RELEASE")
        classpath("com.epages:restdocs-api-spec-gradle-plugin:0.8.0")
    }
}

// spring cloud contract plugin and extension configuration
apply(plugin = "spring-cloud-contract")
configure<ContractVerifierExtension> {
    testMode = TestMode.MOCKMVC
    baseClassForTests = "com.afossey.projects.educational.gists.ContractTestsBase"
    generatedTestSourcesDir = project.file("src/generatedContract")
    basePackageForTests = "com.afossey.projects.educational.gists"
}

// restdocs-api-spec plugin and extension configuration
apply(plugin = "com.epages.restdocs-api-spec")
configure<OpenApi3Extension> {
    setServer("https://localhost:8080")
    title = "Gist API"
    description = "Auto-generated OAS from RestDocs"
    version = project.version as String
    format = "yaml"
}

plugins {
    java
    id("org.asciidoctor.convert") version "1.5.9.2"
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

// copy ReactJS build to resources
tasks.register<Copy>("copyReactBuildDirectory") {
    from("../gists-client/build")
    into("$buildDir/resources/main/static")
    dependsOn(":gists-client:yarnBuild")
}

// Tasks configuration
tasks {
    val snippetsDir = file("build/generated-snippets")
    test {
        outputs.dir(snippetsDir)
    }
    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)
    }
    bootJar {
        // copy react app before assembling the jar
        dependsOn("copyReactBuildDirectory")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.github.everit-org.json-schema:org.everit.json.schema:1.11.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testCompile("org.springframework.restdocs:spring-restdocs-mockmvc")
    testCompile("com.epages:restdocs-api-spec-mockmvc:0.8.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    compile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    //testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    //testImplementation("org.springframework.security:spring-security-test")
}

val springCloudVersion = "Greenwich.RC2"
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}
