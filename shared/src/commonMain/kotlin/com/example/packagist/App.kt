
package com.example.packagist

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.packagist.data.repository.DefaultPackageRepository
import com.example.packagist.data.repository.MockPackageDataSource
import com.example.packagist.presentation.catalog.CatalogScreen
import com.example.packagist.presentation.catalog.CatalogViewModel
import com.example.packagist.presentation.detail.DetailScreen
import com.example.packagist.presentation.navigation.PackageRoute

@Composable
@Preview
fun App(
    browserHistory: @Composable (SnapshotStateList<PackageRoute>) -> Unit = {}
) {
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

    val backStack = remember {
        mutableStateListOf<PackageRoute>(
            PackageRoute.Catalog
        )
    }

    // Подключаем браузерную историю, если она передана платформой.
    browserHistory(backStack)

    val availableDependencies = remember {
        packageRepository.getPackages()
            .map { it.name }
            .toSet()
    }

    MaterialTheme {
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                }
            },
            entryProvider = entryProvider {
                entry<PackageRoute.Catalog> {
                    CatalogScreen(
                        uiState = catalogViewModel.uiState,
                        onSearchQueryChange =
                            catalogViewModel::onSearchQueryChange,
                        onPackageClick = { phpPackage ->
                            backStack.add(
                                PackageRoute.Details(
                                    packageName = phpPackage.name
                                )
                            )
                        }
                    )
                }

                entry<PackageRoute.Details> { route ->
                    val phpPackage =
                        packageRepository.getPackage(route.packageName)

                    if (phpPackage != null) {
                        DetailScreen(
                            phpPackage = phpPackage,
                            availableDependencies = availableDependencies,
                            onBackClick = {
                                if (backStack.size > 1) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                            },
                            onDependencyClick = { dependencyName ->
                                backStack.add(
                                    PackageRoute.Details(
                                        packageName = dependencyName
                                    )
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}
