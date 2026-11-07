package com.example.packagist

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.compose.ui.tooling.preview.Preview
import com.example.packagist.data.repository.DefaultPackageRepository
import com.example.packagist.data.repository.MockPackageDataSource
import com.example.packagist.model.PhpPackage
import com.example.packagist.presentation.catalog.CatalogScreen
import com.example.packagist.presentation.catalog.CatalogViewModel
import com.example.packagist.presentation.detail.DetailScreen

@Composable
@Preview
fun App() {
    val packageRepository = remember {
        DefaultPackageRepository(
            MockPackageDataSource()
        )
    }

    val catalogViewModel: CatalogViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                CatalogViewModel(packageRepository)
            }
        }
    )

    var selectedPackageState = remember {
        androidx.compose.runtime.mutableStateOf<PhpPackage?>(null)
    }

    val selectedPackage = selectedPackageState.value

    val availableDependencies = remember {
        packageRepository.getPackages()
            .map { it.name }
            .toSet()
    }

    MaterialTheme {
        if (selectedPackage == null) {
            CatalogScreen(
                uiState = catalogViewModel.uiState,
                onSearchQueryChange = catalogViewModel::onSearchQueryChange,
                onPackageClick = { phpPackage ->
                    selectedPackageState.value = phpPackage
                }
            )
        } else {
            DetailScreen(
                phpPackage = selectedPackage,
                availableDependencies = availableDependencies,
                onBackClick = {
                    selectedPackageState.value = null
                },
                onDependencyClick = { dependencyName ->
                    selectedPackageState.value =
                        packageRepository.getPackage(dependencyName)
                }
            )
        }
    }
}