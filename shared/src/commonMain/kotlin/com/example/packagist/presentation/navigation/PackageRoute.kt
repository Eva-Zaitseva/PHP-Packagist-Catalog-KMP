package com.example.packagist.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface PackageRoute : NavKey {

    @Serializable
    data object Catalog : PackageRoute

    @Serializable
    data class Details(
        val packageName: String
    ) : PackageRoute
}