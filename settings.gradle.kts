pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kuiklyMultiModuleDemo"
include(":androidApp")
include(":shared")
include(":libraryBase")
include(":moduleMine")
include(":moduleMessage")
