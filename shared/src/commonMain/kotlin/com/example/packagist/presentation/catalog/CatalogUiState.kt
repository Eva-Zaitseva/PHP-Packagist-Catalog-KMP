package com.example.packagist.presentation.catalog

import com.example.packagist.model.PhpPackage

data class CatalogUiState(
    val packages: List<PhpPackage> = emptyList(),
    val searchQuery: String = "",
    val favoritePackageNames: Set<String> = emptySet()
) {
    val filteredPackages: List<PhpPackage>
        get() = packages.filter { phpPackage ->
            phpPackage.name.contains(searchQuery, ignoreCase = true) ||
                    phpPackage.description.contains(searchQuery, ignoreCase = true)
        }

    val favoritePackages: List<PhpPackage>
        get() = packages.filter { phpPackage ->
            phpPackage.name in favoritePackageNames
        }

    fun isFavorite(packageName: String): Boolean {
        return packageName in favoritePackageNames
    }
}