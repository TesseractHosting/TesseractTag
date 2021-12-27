import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "club.tesseract"
version = "0.0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo1.maven.org/maven2/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("org.reflections:reflections:0.9.12")

}
tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("TesseractTag.jar")
        relocate("org.reflections", "club.tesseract.tesseracttag.dependencies.reflections")
        exclude("DebugProbesKt.bin")
        exclude("META-INF/**")
    }
    processResources {
        filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
    }

}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}