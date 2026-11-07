
package com.example.packagist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.packagist.data.repository.DefaultPackageRepository
import com.example.packagist.data.repository.MockPackageDataSource
import com.example.packagist.environment.AppEnvironment
import com.example.packagist.environment.LocalAppLocale
import com.example.packagist.presentation.catalog.CatalogScreen
import com.example.packagist.presentation.catalog.CatalogViewModel
import com.example.packagist.presentation.detail.DetailScreen
import com.example.packagist.presentation.favorites.FavoritesScreen
import com.example.packagist.presentation.navigation.PackageRoute
import com.example.packagist.presentation.theme.AppTheme
import com.example.packagist.resources.Res
import com.example.packagist.resources.language_english
import com.example.packagist.resources.language_russian
import com.example.packagist.resources.language_setting
import com.example.packagist.resources.theme_dark
import com.example.packagist.resources.theme_light
import com.example.packagist.resources.theme_setting
import com.example.packagist.resources.theme_system
import org.jetbrains.compose.resources.stringResource

private enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

private enum class AppLanguage(val localeTag: String) {
    RUSSIAN("ru"),
    ENGLISH("en");

    companion object {
        fun fromSystemLocale(locale: String): AppLanguage {
            return if (locale.startsWith("ru", ignoreCase = true)) {
                RUSSIAN
            } else {
                ENGLISH
            }
        }
    }
}

@Composable
@Preview
fun App(
    browserHistory: @Composable (SnapshotStateList<PackageRoute>) -> Unit = {}
) {
    // Определяем системную локаль до применения локали самого приложения.
    val systemLocale = LocalAppLocale.current

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

    val uiState = catalogViewModel.uiState

    val backStack = remember {
        mutableStateListOf<PackageRoute>(
            PackageRoute.Catalog
        )
    }

    var themeMode by remember {
        mutableStateOf(ThemeMode.SYSTEM)
    }

    // При первом запуске используем язык системных настроек.
    // После этого пользователь может переключать его вручную.
    var appLanguage by remember {
        mutableStateOf(
            AppLanguage.fromSystemLocale(systemLocale)
        )
    }

    browserHistory(backStack)

    val availableDependencies = remember {
        packageRepository.getPackages()
            .map { it.name }
            .toSet()
    }

    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> null
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    AppEnvironment(
        locale = appLanguage.localeTag
    ) {
        AppTheme(
            darkTheme = darkTheme
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SettingsBar(
                    themeMode = themeMode,
                    appLanguage = appLanguage,
                    onThemeClick = {
                        themeMode = when (themeMode) {
                            ThemeMode.SYSTEM -> ThemeMode.LIGHT
                            ThemeMode.LIGHT -> ThemeMode.DARK
                            ThemeMode.DARK -> ThemeMode.SYSTEM
                        }
                    },
                    onLanguageClick = {
                        appLanguage = when (appLanguage) {
                            AppLanguage.RUSSIAN -> AppLanguage.ENGLISH
                            AppLanguage.ENGLISH -> AppLanguage.RUSSIAN
                        }
                    }
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
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
                                    uiState = uiState,
                                    onSearchQueryChange =
                                        catalogViewModel::onSearchQueryChange,
                                    onPackageClick = { phpPackage ->
                                        backStack.add(
                                            PackageRoute.Details(
                                                packageName = phpPackage.name
                                            )
                                        )
                                    },
                                    onFavoriteClick =
                                        catalogViewModel::onFavoriteClick,
                                    onFavoritesClick = {
                                        backStack.add(PackageRoute.Favorites)
                                    }
                                )
                            }

                            entry<PackageRoute.Favorites> {
                                FavoritesScreen(
                                    favoritePackages = uiState.favoritePackages,
                                    onBackClick = {
                                        if (backStack.size > 1) {
                                            backStack.removeAt(backStack.lastIndex)
                                        }
                                    },
                                    onPackageClick = { phpPackage ->
                                        backStack.add(
                                            PackageRoute.Details(
                                                packageName = phpPackage.name
                                            )
                                        )
                                    },
                                    onFavoriteClick =
                                        catalogViewModel::onFavoriteClick
                                )
                            }

                            entry<PackageRoute.Details> { route ->
                                val phpPackage =
                                    packageRepository.getPackage(route.packageName)

                                if (phpPackage != null) {
                                    DetailScreen(
                                        phpPackage = phpPackage,
                                        availableDependencies = availableDependencies,
                                        isFavorite = uiState.isFavorite(phpPackage.name),
                                        onBackClick = {
                                            if (backStack.size > 1) {
                                                backStack.removeAt(backStack.lastIndex)
                                            }
                                        },
                                        onFavoriteClick = {
                                            catalogViewModel.onFavoriteClick(
                                                phpPackage.name
                                            )
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
        }
    }
}

@Composable
private fun SettingsBar(
    themeMode: ThemeMode,
    appLanguage: AppLanguage,
    onThemeClick: () -> Unit,
    onLanguageClick: () -> Unit
) {
    val themeLabel = when (themeMode) {
        ThemeMode.SYSTEM -> Res.string.theme_system
        ThemeMode.LIGHT -> Res.string.theme_light
        ThemeMode.DARK -> Res.string.theme_dark
    }

    val languageLabel = when (appLanguage) {
        AppLanguage.RUSSIAN -> Res.string.language_russian
        AppLanguage.ENGLISH -> Res.string.language_english
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = onThemeClick) {
            Text(
                text = "${stringResource(Res.string.theme_setting)}: " +
                        stringResource(themeLabel),
                style = MaterialTheme.typography.labelLarge
            )
        }

        TextButton(onClick = onLanguageClick) {
            Text(
                text = "${stringResource(Res.string.language_setting)}: " +
                        stringResource(languageLabel),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
