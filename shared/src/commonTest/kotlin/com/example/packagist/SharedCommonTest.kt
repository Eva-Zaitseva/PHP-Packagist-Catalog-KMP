package com.example.packagist

import com.example.packagist.data.MockPackages
import com.example.packagist.presentation.catalog.CatalogUiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SharedCommonTest {

    private val packages = MockPackages.packages

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun searchByNameFindsPackage() {
        val state = CatalogUiState(
            packages = packages,
            searchQuery = "laravel"
        )

        assertTrue(state.filteredPackages.isNotEmpty())
        assertTrue(state.filteredPackages.all {
            it.name.contains("laravel", ignoreCase = true) ||
                    it.description.contains("laravel", ignoreCase = true)
        })
    }

    @Test
    fun searchWithNoMatchesReturnsEmptyList() {
        val state = CatalogUiState(
            packages = packages,
            searchQuery = "zzz-not-a-real-package"
        )

        assertTrue(state.filteredPackages.isEmpty())
    }

    @Test
    fun clearingQueryRestoresFullList() {
        val state = CatalogUiState(
            packages = packages,
            searchQuery = "zzz-not-a-real-package"
        )
        assertTrue(state.filteredPackages.isEmpty())

        val cleared = state.copy(searchQuery = "")

        assertEquals(packages.size, cleared.filteredPackages.size)
    }
}
