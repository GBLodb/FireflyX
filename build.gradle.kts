import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.40"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

taboolib {
    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-database")
    install("module-kether")
    install("module-lang")
    install("module-ui")
    install("module-nms")
    install("platform-bukkit")
    classifier = null
    version = "6.0.9-25"

    description {
        contributors {
            name("Mical")
        }
        dependencies {
            name("Vault")
        }
    }
}

repositories {
    mavenCentral()

    maven {
        name = "JitPack"
        url = uri("https://jitpack.io/")
    }

    maven {
        name = "SpigotMC"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        name = "CodeMC NMS"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
}

dependencies {
    compileOnly ("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly ("ink.ptms:nms-all:1.0.0")
    compileOnly ("ink.ptms.core:v11900:11900-minimize:mapped")
    compileOnly ("ink.ptms.core:v11900:11900-minimize:universal")
    compileOnly ("com.github.MilkBowl:VaultAPI:1.7")
}

tasks.withType<ShadowJar> {
    manifest.attributes["Main-Class"] = "work.crash.micalhl.fireflyx.FireflyX"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}
