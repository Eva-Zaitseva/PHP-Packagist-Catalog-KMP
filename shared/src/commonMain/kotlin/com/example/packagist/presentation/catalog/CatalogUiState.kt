package com.example.packagist.presentation.catalog

import com.example.packagist.model.PhpPackage

data class CatalogUiState(
    val packages: List<PhpPackage> = emptyList(),
    val searchQuery: String = ""
) {
    val filteredPackages: List<PhpPackage>
        get() = packages.filter { phpPackage ->
            phpPackage.name.contains(searchQuery, ignoreCase = true) ||
                    phpPackage.description.contains(searchQuery, ignoreCase = true)
        }
}