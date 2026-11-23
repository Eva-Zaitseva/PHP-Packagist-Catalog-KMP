package com.example.packagist.domain.datasource

import com.example.packagist.model.PhpPackage

interface PackageDataSource {
    fun getPackages(): List<PhpPackage>
    fun getPackage(packageName: String): PhpPackage?
}