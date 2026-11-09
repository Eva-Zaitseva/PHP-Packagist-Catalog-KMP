
package com.example.packagist.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.packagist.presentation.components.PackageCard
import com.example.packagist.resources.Res
import com.example.packagist.resources.catalog_title
import com.example.packagist.resources.favorites
import com.example.packagist.resources.package_count
import com.example.packagist.resources.search_hint
import com.example.packagist.resources.search_packages
import org.jetbrains.compose.resources.stringResource

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onSearchQueryChange: (String) -> Unit,
    onPackageClick: (com.example.packagist.model.PhpPackage) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val filteredPackages = uiState.filteredPackages

    // Сохраняем позицию прокрутки списка.
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.catalog_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            TextButton(onClick = onFavoritesClick) {
                Text(stringResource(Res.string.favorites))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(Res.string.search_packages))
            },
            placeholder = {
                Text(stringResource(Res.string.search_hint))
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(
                Res.string.package_count,
                filteredPackages.size
            ),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = filteredPackages,
                key = { it.name }
            ) { phpPackage ->
                PackageCard(
                    phpPackage = phpPackage,
                    isFavorite = uiState.isFavorite(phpPackage.name),
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
