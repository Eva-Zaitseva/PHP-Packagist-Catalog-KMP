package com.example.packagist.data.repository

import com.example.packagist.domain.datasource.PackageDataSource
import com.example.packagist.domain.repository.PackageRepository
import com.example.packagist.model.PhpPackage

class DefaultPackageRepository(
    private val dataSource: PackageDataSource
) : PackageRepository {

    override fun getPackages(): List<PhpPackage> {
        return dataSource.getPackages()
    }

    override fun getPackage(packageName: String): PhpPackage? {
        return dataSource.getPackage(packageName)
    }
}