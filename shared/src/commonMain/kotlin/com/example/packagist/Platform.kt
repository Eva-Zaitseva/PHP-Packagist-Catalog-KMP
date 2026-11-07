package com.example.packagist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform