allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://repo.spring.io/milestone")
        maven("https://jitpack.io")
    }
    group = "com.afossey"
    version = "0.0.1-SNAPSHOT"
}

tasks.register("printVersion") {
    doLast {
        println(project.version)
    }
}