package com.example.packagist.presentation.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.packagist.domain.repository.PackageRepository

class CatalogViewModel(
    private val packageRepository: PackageRepository
) : ViewModel() {

    var uiState by mutableStateOf(
        CatalogUiState(
            packages = packageRepository.getPackages()
        )
    )
        private set

    fun onSearchQueryChange(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }
}