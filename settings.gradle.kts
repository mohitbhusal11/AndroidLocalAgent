pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LocalAIAgent"

include(
    ":app",
    ":core",
    ":core-ui",
    ":core-network",
    ":core-ai",
    ":core-database",
    ":core-agent",
    ":core-memory",
    ":core-voice",
    ":core-permissions",
    ":feature-chat",
    ":feature-voice",
    ":feature-memory",
    ":feature-notifications",
    ":feature-automation",
    ":feature-accessibility",
    ":feature-research",
    ":feature-settings",
    ":feature-shortcuts",
    ":feature-productivity",
    ":feature-doomscroll",
)
