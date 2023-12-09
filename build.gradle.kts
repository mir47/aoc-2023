plugins {
    kotlin("jvm") version "1.9.20"
}

sourceSets {
    main {
        kotlin.srcDir("src/main")
    }
    test {
        kotlin.srcDir("src/test")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation("org.reflections", "reflections", "0.9.12")
    testImplementation(kotlin("test"))
}
