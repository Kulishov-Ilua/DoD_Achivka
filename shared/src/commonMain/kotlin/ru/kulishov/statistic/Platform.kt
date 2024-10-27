package ru.kulishov.statistic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform