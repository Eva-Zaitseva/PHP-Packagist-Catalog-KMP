package com.example.packagist.model

data class PhpPackage(
    val name: String,
    val description: String,
    val version: String,
    val downloads: String,
    val stars: Int,
    val require: Map<String, String> = emptyMap()
)