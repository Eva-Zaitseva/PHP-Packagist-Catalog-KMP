package com.example.packagist

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.packagist.presentation.navigation.PackageRoute
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentName
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentParameters

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App(
            browserHistory = { backStack ->
                ChronologicalBrowserNavigation(
                    backStack = backStack,

                    saveKey = { route ->
                        when (route) {
                            PackageRoute.Catalog ->
                                buildBrowserHistoryFragment("catalog")

                            PackageRoute.Favorites ->
                                buildBrowserHistoryFragment("favorites")

                            is PackageRoute.Details ->
                                buildBrowserHistoryFragment(
                                    "details",
                                    mapOf(
                                        "packageName" to route.packageName
                                    )
                                )
                        }
                    },

                    restoreKey = { fragment ->
                        when (getBrowserHistoryFragmentName(fragment)) {
                            "catalog" ->
                                PackageRoute.Catalog

                            "favorites" ->
                                PackageRoute.Favorites

                            "details" -> {
                                val parameters =
                                    getBrowserHistoryFragmentParameters(fragment)

                                parameters["packageName"]?.let { packageName ->
                                    PackageRoute.Details(packageName)
                                }
                            }

                            else -> null
                        }
                    }
                )
            }
        )
    }
}