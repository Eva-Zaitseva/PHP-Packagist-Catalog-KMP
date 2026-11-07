
package com.example.packagist

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.packagist.data.MockPackages
import com.example.packagist.model.PhpPackage
import com.example.packagist.presentation.catalog.CatalogScreen
import com.example.packagist.presentation.detail.DetailScreen

@Composable
@Preview
fun App() {
    var selectedPackage by remember {
        mutableStateOf<PhpPackage?>(null)
    }

    val availableDependencies = MockPackages.packages
        .map { it.name }
        .toSet()

    MaterialTheme {
        if (selectedPackage == null) {
            CatalogScreen(
                onPackageClick = { phpPackage ->
                    selectedPackage = phpPackage
                }
            )
        } else {
            DetailScreen(
                phpPackage = selectedPackage!!,
                availableDependencies = availableDependencies,
                onBackClick = {
                    selectedPackage = null
                },
                onDependencyClick = { dependencyName ->
                    selectedPackage = MockPackages.packages.find {
                        it.name == dependencyName
                    }
                }
            )
        }
    }
}
