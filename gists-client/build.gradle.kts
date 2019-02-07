import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("com.moowork.node") version "1.2.0"
}
node {
    download = true
    version = "10.15.1"
    yarnVersion = "1.13.0"
}

tasks.register<YarnTask>("yarnBuild") {
    args = listOf("build")
    inputs.dir("src")
    inputs.dir("public")
    outputs.dir("build")
    dependsOn("yarn_install")
}

