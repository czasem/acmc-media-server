
group = "pl.acmc.media"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/")}
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/")}
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/nms/")
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")

    flatDir {
        dirs("libs")
    }
}


dependencies {
    implementation("dev.rollczi:litecommands-bukkit:3.9.1")
    compileOnly("org.projectlombok:lombok:1.18.28")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly ("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.4")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.8.6")
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.5")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.5")
    implementation("com.zaxxer:HikariCP:4.0.3")
    compileOnly("dev.triumphteam:triumph-gui:3.1.10")
    implementation("de.tr7zw:item-nbt-api-plugin:2.13.2")


    annotationProcessor("org.projectlombok:lombok:1.18.28")
}


tasks {

    val shadowJar by getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        archiveBaseName.set("acmc-media")
        archiveClassifier.set("")
        archiveVersion.set("1.0")

        exclude("META-INF/**")
        exclude("javaassist/**")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    build {
        dependsOn(shadowJar)
    }
}