package com.example.packagist.data.repository

import com.example.packagist.data.MockPackages
import com.example.packagist.domain.datasource.PackageDataSource
import com.example.packagist.model.PhpPackage

class MockPackageDataSource : PackageDataSource {

    override fun getPackages(): List<PhpPackage> {
        return MockPackages.packages
    }

    override fun getPackage(packageName: String): PhpPackage? {
        return MockPackages.packages.find {
            it.name == packageName
        }
    }
}