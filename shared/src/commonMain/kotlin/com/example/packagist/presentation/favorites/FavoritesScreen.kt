package com.example.packagist.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.packagist.model.PhpPackage
import com.example.packagist.presentation.components.PackageCard
import com.example.packagist.resources.Res
import com.example.packagist.resources.back_to_catalog
import com.example.packagist.resources.empty_favorites
import com.example.packagist.resources.favorites
import org.jetbrains.compose.resources.stringResource


@Composable
fun FavoritesScreen(
    favoritePackages: List<PhpPackage>,
    onBackClick: () -> Unit,
    onPackageClick: (PhpPackage) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text(stringResource(Res.string.back_to_catalog))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.favorites),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (favoritePackages.isEmpty()) {
            Text(
                text = stringResource(Res.string.empty_favorites),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = favoritePackages,
                    key = { it.name }
                ) { phpPackage ->
                    PackageCard(
                        phpPackage = phpPackage,
                        isFavorite = true,
                        onPackageClick = {
                            onPackageClick(phpPackage)
                        },
                        onFavoriteClick = {
                            onFavoriteClick(phpPackage.name)
                        }
                    )
                }
            }
        }
    }
}