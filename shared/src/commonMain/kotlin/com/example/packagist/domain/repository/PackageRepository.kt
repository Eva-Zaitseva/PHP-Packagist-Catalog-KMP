package com.example.packagist.domain.repository

import com.example.packagist.model.PhpPackage

interface PackageRepository {
    fun getPackages(): List<PhpPackage>
    fun getPackage(packageName: String): PhpPackage?
}